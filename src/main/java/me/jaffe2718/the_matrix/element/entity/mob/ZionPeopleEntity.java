package me.jaffe2718.the_matrix.element.entity.mob;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.*;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot.DriveAPUGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot.SelectAPUGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.infantry.ShootGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.machinist.FixMachineGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.machinist.SelectMachineGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.rifleman.SelectMachineGunGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.rifleman.UseMachineGunGoal;
import me.jaffe2718.the_matrix.unit.MathUnit;
import me.jaffe2718.the_matrix.unit.ParticleRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import me.jaffe2718.the_matrix.unit.TradeOfferListFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Npc;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.HashMap;

import static me.jaffe2718.the_matrix.client.model.entity.ZionPeopleModel.*;
import static me.jaffe2718.the_matrix.unit.EntityRegistry.ROBOT_CLASSES;

public class ZionPeopleEntity
        extends PathAwareEntity
        implements Npc, Merchant, GeoEntity {

    private static final String JOB_ID_KEY = "JobID";
    public static final HashMap<Integer, String> jobIDMap = new HashMap<>() {{
        // 0 -> "random";
        put(1, "apu_pilot");
        put(2, "carpenter");
        put(3, "farm_breeder");
        put(4, "farmer");
        put(5, "grocer");
        put(6, "infantry");
        put(7, "machinist");
        put(8, "miner");
        put(9, "rifleman");
    }};
    private static final TrackedData<Integer> JOB_ID = DataTracker.registerData(ZionPeopleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    /**
     * For machinist -> if this entity is fixing a machine
     * */
    private static final TrackedData<Boolean> IS_FIXING = DataTracker.registerData(ZionPeopleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final String HAS_HOME_KEY = "HasHome";        // nbt boolean
    private static final String HOME_POS_KEY = "HomePos";        // nbt long
    @Environment(EnvType.SERVER)
    public boolean hasHome = false;
    public BlockPos homePos = BlockPos.ORIGIN;
    @Nullable
    private PlayerEntity customer;
    @Nullable
    protected TradeOfferList offers;

    /**
     * The vehicle that this entity wants to get into, for apu pilots and rifleman.<br>
     * Or the vehicle that this entity want to fix, for machinist.<br>
     * Only armored personnel units and machine guns are valid.
     */
    @Nullable
    protected PathAwareEntity targetVehicle;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 20.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 96.0D);
    }

    public ZionPeopleEntity(EntityType<? extends ZionPeopleEntity> entityType, World world) {
        super(entityType, world);
        if (!MathUnit.isBetween(this.getJobId(), 1, 9)) {
            this.setJobId(this.getRandom().nextInt(9) + 1);
        }
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(JOB_ID, 0);
        this.dataTracker.startTracking(IS_FIXING, false);
    }

    @Override
    public void readCustomDataFromNbt(@NotNull NbtCompound nbt) {
        this.setJobId(nbt.getInt(JOB_ID_KEY));
        if (!MathUnit.isBetween(this.getJobId(), 1, 9)) {   // set to random [1, 9] if invalid
            this.setJobId(this.getRandom().nextInt(9) + 1);
        }
        this.hasHome = nbt.getBoolean(HAS_HOME_KEY);
        this.homePos = BlockPos.fromLong(nbt.getLong(HOME_POS_KEY));
        super.readCustomDataFromNbt(nbt);
        this.setGoals();
    }

    @Override
    public void writeCustomDataToNbt(@NotNull NbtCompound nbt) {
        nbt.putInt(JOB_ID_KEY, this.getJobId());
        nbt.putBoolean(HAS_HOME_KEY, this.hasHome);
        nbt.putLong(HOME_POS_KEY, this.homePos.asLong());
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("job.the_matrix.zion_people." + this.getJobName());
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (this.getJobId() == 1 && damageSource.isOf(DamageTypes.FALL) && this.fallDistance < 6) {
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    public int getJobId() {
        return this.dataTracker.get(JOB_ID);
    }

    public void setJobId(int jobId) {
        this.dataTracker.set(JOB_ID, jobId);
    }

    /**
     * Get gender of this Zion people entity.<br>
     * Male -> true; Female -> false
     * */
    private boolean getGender() {
        int jobId = this.getJobId();
        return jobId != 3 && jobId != 5;
    }

    /**
     * If this entity is a soldier
     * apu pilot, infantry, rifleman -> true
     * [else] -> false
     * */
    private boolean isSoldier() {
        int jobId = this.getJobId();
        return jobId == 1 || jobId == 6 || jobId == 9;
    }

    /**
     * For machinist -> if this entity is fixing a machine
     * */
    public boolean isFixing() {
        return this.dataTracker.get(IS_FIXING) && this.getJobId() == 7;
    }

    /**
     * For machinist -> if this entity is fixing a machine
     * */
    public void setFixing(boolean fixing) {
        this.dataTracker.set(IS_FIXING, fixing && this.getJobId() == 7);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        this.playSound(this.getGender() ? SoundEventRegistry.ZION_PEOPLE_MALE_GREET : SoundEventRegistry.ZION_PEOPLE_FEMALE_GREET, 1, 1);
        if (this.isAlive()) {
            if (!this.getWorld().isClient) {
                this.getOffers();
                this.setCustomer(player);
                this.sendOffers(player, this.getDisplayName(), 1);
                return ActionResult.success(this.getWorld().isClient);
            }
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.targetVehicle != null && !this.targetVehicle.isAlive()) {
            this.targetVehicle.remove(RemovalReason.KILLED);
            this.targetVehicle = null;    // if the vehicle is dead, don't get into it
        } else if (this.targetVehicle != null
                && this.targetVehicle.hasPassengers()
                && !this.targetVehicle.hasPassenger(this)) {
            this.targetVehicle = null;   // if the vehicle is full, don't get into it
        }
        if (this.age % 200 == 0
                && this.isAlive()
                && !this.hasVehicle()
                && this.getHealth() < this.getMaxHealth()
                && (this.getTarget() == null
                    || !this.getTarget().isAlive()
                    || !ROBOT_CLASSES.contains(this.getTarget().getClass()))
                && this.getWorld() instanceof ServerWorld serverWorld) {  // self-heal if no enemy
            this.setHealth(this.getHealth() + 1);
            serverWorld.spawnParticles(ParticleRegistry.HEAL, this.getX(), this.getY() + 1.0, this.getZ(), 8, 0.4, 0.4, 0.4, 0.0);
        }
    }

    /**
     * Set the goals for this entity, do not call {@link MobEntity#initGoals()}.
     * instead, call this method in the end of {@link ZionPeopleEntity#ZionPeopleEntity(EntityType, World)}
     */
    protected void setGoals() {
        // universal goals for all jobs
        this.targetSelector.add(2, new SelectHomeGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new LongDoorInteractGoal(this, true));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(3, new FollowCustomerGoal(this));
        this.goalSelector.add(4, new GoHomeGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAtEntityGoal(this, ZionPeopleEntity.class, 8.0F));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAroundGoal(this));
        switch (this.getJobId()) {
            case 1 -> {    // APU Pilot
                this.targetSelector.add(1, new SelectAPUGoal(this));
                this.targetSelector.add(1, new SelectEnemyGoal(this));
                this.goalSelector.add(1, new DriveAPUGoal(this));
                this.goalSelector.add(2, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 2 -> // Carpenter
                    this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                            livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            case 3 -> // Farm Breeder
                    this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                            livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            case 4 -> // Farmer
                    this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                            livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            case 5 -> // Grocer
                    this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                            livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            case 6 -> { // Infantry
                this.targetSelector.add(1, new SelectEnemyGoal(this));
                this.goalSelector.add(1, new ShootGoal(this));
            }
            case 7 -> {    // Machinist
                this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
                this.targetSelector.add(2, new SelectEnemyGoal(this));
                this.targetSelector.add(1, new SelectMachineGoal(this));
                this.goalSelector.add(1, new FixMachineGoal(this));
            }
            case 8 -> // Miner
                    this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                            livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            case 9 -> {    // Rifleman
                this.targetSelector.add(1, new SelectMachineGunGoal(this));
                this.targetSelector.add(1, new SelectEnemyGoal(this));
                this.goalSelector.add(1, new UseMachineGunGoal(this));
                this.goalSelector.add(2, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
                // use the machine gun
            }
        }
    }

    @Override
    public int getArmor() {
        return this.isSoldier() ? super.getArmor() * 2 : super.getArmor();
    }

    /**
     * Set the vehicle that this entity wants to get into, for apu pilots and rifleman
     * @param targetVehicle The vehicle that this entity wants to get into
     */
    public void setTargetVehicle(@Nullable PathAwareEntity targetVehicle) {
        this.targetVehicle = targetVehicle;
    }

    /**
     * Get the vehicle that this entity wants to get into, for apu pilots and rifleman
     * @return The vehicle that this entity wants to get into
     */
    public @Nullable PathAwareEntity getTargetVehicle() {
        return this.targetVehicle;
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity customer) {
        this.customer = customer;
        if (this.getCustomer() != null && customer == null) {
            this.customer = null;
        }
    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return this.customer;
    }

    @Override
    public TradeOfferList getOffers() {
        if (this.offers == null) {
            this.offers = TradeOfferListFactory.createZionPeopleTradeOfferList(this.getJobId());
        }
        return this.offers;
    }

    @Override
    public void setOffersFromServer(@NotNull TradeOfferList offers) {
        this.offers = offers;
    }

    @Override
    public void trade(TradeOffer offer) {
        if (random.nextInt() % 3 == 0) {
            this.playSound(this.getYesSound(), 1, 1);
        }
    }

    @Override
    public void onSellingItem(ItemStack stack) {
        if (random.nextInt() % 3 == 0 && ! this.isClient()) {
            this.playSound(this.getSellingSound(), 1, 1);
        }
    }

    @Override
    public int getExperience() {
        return 1;
    }

    @Override
    public void setExperienceFromServer(int experience) {
    }

    @Override
    public boolean isLeveledMerchant() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {   // TODO: add sounds for different jobs
        switch (this.getJobId()) {
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            case 6 -> {}
            case 7 -> {}
            case 8 -> {}
            case 9 -> {}
        }
        return super.getAmbientSound();
    }

    @Override
    public SoundEvent getYesSound() {
        return this.getGender() ? SoundEventRegistry.ZION_PEOPLE_MALE_TRADE : SoundEventRegistry.ZION_PEOPLE_FEMALE_TRADE;
    }

    protected SoundEvent getSellingSound() {    // TODO: add sounds for different jobs
        switch (this.getJobId()) {
            case 1, 6, 9 -> {   // [Soldier] APU Pilot, Infantry, Rifleman
                return SoundEventRegistry.ZION_PEOPLE_SOLDIER_PROMOTE;
            }
            case 2 -> {         // Carpenter

            }
            case 3 -> {         // Farm Breeder

            }
            case 4 -> {}
            case 5 -> {}
            case 7 -> {}
            case 8 -> {}
        }
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.getGender() ? SoundEventRegistry.ZION_PEOPLE_MALE_HURT : SoundEventRegistry.ZION_PEOPLE_FEMALE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.getGender() ? SoundEventRegistry.ZION_PEOPLE_MALE_DEATH : SoundEventRegistry.ZION_PEOPLE_FEMALE_DEATH;
    }

    @Override
    public boolean isClient() {
        return this.getWorld().isClient;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        switch (this.getJobId()) {
            case 1 -> // APU Pilot
                    controllers.add(new AnimationController<>(this, "controller", 0, this::apuPilotPredicate));
            case 2 -> // Carpenter
                    controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            case 3 -> // Farm Breeder
                    controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            case 4 -> // Farmer
                    controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            case 5 -> // Grocer
                    controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            case 6 -> // Infantry
                    controllers.add(new AnimationController<>(this, "controller", 0, this::infantryPredicate));
            case 7 -> // Machinist
                    controllers.add(new AnimationController<>(this, "controller", 0, this::machinistPredicate));
            case 8 -> // Miner
                    controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(MINER_COMMON)));
            case 9 -> // Rifleman
                    controllers.add(new AnimationController<>(this, "controller", 0, this::riflemanPredicate));
            default -> TheMatrix.LOGGER.error("Invalid job ID: " + this.getJobId());
        }
    }

    private PlayState machinistPredicate(@NotNull AnimationState<ZionPeopleEntity> state) {
        if (this.isFixing()) {
            return state.setAndContinue(MACHINIST_FIX);
        } else {
            return state.setAndContinue(COMMON);
        }
    }

    private PlayState apuPilotPredicate(AnimationState<ZionPeopleEntity> state) {
        if (this.hasVehicle()) {
            return state.setAndContinue(APU_PILOT_DRIVE);
        } else {
            return state.setAndContinue(COMMON);
        }
    }

    private PlayState infantryPredicate(AnimationState<ZionPeopleEntity> state) {
        if (this.isAttacking()) {
            return state.setAndContinue(INFANTRY_COMBAT);
        } else {
            return state.setAndContinue(INFANTRY_IDLE);
        }
    }

    private PlayState riflemanPredicate(AnimationState<ZionPeopleEntity>state) {
        if (this.hasVehicle()) {
            return state.setAndContinue(RIFLEMAN_SHOOT);
        } else {
            return state.setAndContinue(COMMON);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public String getJobName() {
        return ZionPeopleEntity.jobIDMap.get(this.getJobId());
    }

}
