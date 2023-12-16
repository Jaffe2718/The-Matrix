package me.jaffe2718.the_matrix.element.entity.mob;

import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.FleeRobotGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.SelectEnemyGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot.StartDrivingAPUGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot.SelectAPUGoal;
import me.jaffe2718.the_matrix.network.packet.s2c.play.ZionPeopleEntitySpawnS2CPacket;
import me.jaffe2718.the_matrix.unit.TradeOfferListFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Npc;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

import java.util.List;

import static me.jaffe2718.the_matrix.client.model.entity.ZionPeopleModel.*;
import static me.jaffe2718.the_matrix.unit.EntityRegistry.ROBOT_CLASSES;

public class ZionPeopleEntity
        extends PathAwareEntity
        implements Npc, Merchant, GeoEntity {

    private static final String JOB_ID_KEY = "JobID";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private PlayerEntity customer;
    @Nullable
    protected TradeOfferList offers;

    /**
     * The vehicle that this entity wants to get into, for apu pilots and rifleman
     * only armored personnel units and machine guns are valid.
     */
    @Nullable
    protected PathAwareEntity targetVehicle;

    public int jobId;   // 0 -> (random), 1 -> AUP Pilot, 2 -> Carpenter, ...

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 20.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D);
    }

    public ZionPeopleEntity(EntityType<? extends ZionPeopleEntity> entityType, World world) {
        super(entityType, world);
        if (this.jobId == 0) {
            this.jobId = this.getRandom().nextInt(9) + 1;
        }
    }

    @Override
    public void readCustomDataFromNbt(@NotNull NbtCompound nbt) {
        int jobId = nbt.getInt(JOB_ID_KEY);
        if (jobId != 0 && jobId <= 9) {
            this.jobId = jobId;
        } else {
            this.jobId = this.getRandom().nextInt(9) + 1;
        }
        super.readCustomDataFromNbt(nbt);
        System.out.println("Setting goals in readCustomDataFromNbt");
        this.setGoals();
    }

    @Override
    public void writeCustomDataToNbt(@NotNull NbtCompound nbt) {
        nbt.putInt(JOB_ID_KEY, this.jobId);
        super.writeCustomDataToNbt(nbt);
    }

    /**
     * Called when this entity is spawned from a packet. This is where we set the job ID.
     * @see ZionPeopleEntitySpawnS2CPacket
     * @param packet The packet that spawned this entity
     */
    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        if (packet instanceof ZionPeopleEntitySpawnS2CPacket zionPacket) {
            int tempJobID = zionPacket.getJobID();
            if (tempJobID != 0 && tempJobID <= 9) {
                this.jobId = tempJobID;
            } else {
                this.jobId = this.getRandom().nextInt(9) + 1;
            }
        } else {
            this.jobId = this.getRandom().nextInt(9) + 1;
        }
        super.onSpawnPacket(packet);
    }

    /**
     * Called when this entity is spawned from a packet. This is where we set the job ID.
     * @see ZionPeopleEntitySpawnS2CPacket
     */
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new ZionPeopleEntitySpawnS2CPacket(this);
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
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isAlive()) {
            if (!this.getWorld().isClient) {
                this.prepareOffers();
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
            this.targetVehicle = null;
        } else if (this.targetVehicle != null
                && this.targetVehicle.hasPassengers()
                && !this.targetVehicle.hasPassenger(this)) {
            this.targetVehicle = null;   // if the vehicle is full, don't get into it
        }
//        if (!this.getWorld().isClient && this.jobId==1 && this.targetVehicle != null && this.age % 20 == 0) {
//            // System.out.println(this.targetVehicle);   // TODO: Remove After Debug
//        }
    }

    /**
     * Set the goals for this entity, do not call {@link MobEntity#initGoals()}.
     * instead, call this method in the end of {@link ZionPeopleEntity#ZionPeopleEntity(EntityType, World)}
     */
    protected void setGoals() {
        // universal goals for all jobs
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, ZionPeopleEntity.class, 8.0F));
        switch (this.jobId) {  // TODO: Add job-specific goals
            case 1 -> {    // APU Pilot
                this.targetSelector.add(1, new SelectAPUGoal(this));
                this.targetSelector.add(1, new SelectEnemyGoal(this));
                this.goalSelector.add(1, new StartDrivingAPUGoal(this));
                this.goalSelector.add(2, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 2 -> {    // Carpenter
                this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 3 -> {    // Farm Breeder
                this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 4 -> {    // Farmer
                this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 5 -> {    // Grocer
                this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 6 -> {    // Infantry
                this.targetSelector.add(1, new SelectEnemyGoal(this));
            }
            case 7 -> {    // Machinist
                this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 8 -> {    // Miner
                this.goalSelector.add(1, new FleeRobotGoal<>(this, LivingEntity.class, 64, 1.2f, 1.5F,
                        livingEntity -> ROBOT_CLASSES.contains(livingEntity.getClass())));
            }
            case 9 -> {    // Rifleman
                this.targetSelector.add(1, new SelectEnemyGoal(this));
                // use the machine gun
            }
        }
    }

    @Override
    public int getArmor() {
        List<Integer> soldierID = List.of(1, 6, 9);
        if (soldierID.contains(this.jobId)) {
            return super.getArmor() * 2;
        } else {
            return super.getArmor();
        }
    }

    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        if (this.jobId == 1) {
            return super.computeFallDamage(0, damageMultiplier) - 5;
        }
        return super.computeFallDamage(fallDistance, damageMultiplier);
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
            this.offers = TradeOfferListFactory.createZionPeopleTradeOfferList(this.jobId);
        }
        return this.offers;
    }

    private void prepareOffers() {
        this.offers = TradeOfferListFactory.createZionPeopleTradeOfferList(this.jobId);
    }

    @Override
    public void setOffersFromServer(@NotNull TradeOfferList offers) {
        this.offers = offers;
    }

    @Override
    public void trade(TradeOffer offer) {
    }

    @Override
    public void onSellingItem(ItemStack stack) {
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
    public SoundEvent getYesSound() {
        return null;
    }

    @Override
    public boolean isClient() {
        return this.getWorld().isClient;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO: Register controllers
        switch (this.jobId) {
            case 1 -> {    // APU Pilot
                controllers.add(new AnimationController<>(this, "controller", 0, this::apuPilotPredicate));
            }
            case 2 -> {    // Carpenter
                controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            }
            case 3 -> {    // Farm Breeder
                controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            }
            case 4 -> {    // Farmer
                controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            }
            case 5 -> {    // Grocer
                controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
            }
            case 6 -> {    // Infantry
                controllers.add(new AnimationController<>(this, "controller", 0, this::infantryPredicate));
            }
            case 7 -> {    // Machinist
                controllers.add(new AnimationController<>(this, "controller", 0, this::machinistPredicate));
            }
            case 8 -> {    // Miner
                controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(MINER_COMMON)));
            }
            case 9 -> {    // Rifleman
                controllers.add(new AnimationController<>(this, "controller", 0, this::riflemanPredicate));
            }
        }
    }

    private PlayState machinistPredicate(@NotNull AnimationState<ZionPeopleEntity> state) {
        // TODO: Add machinist fix animation
        return state.setAndContinue(COMMON);
    }

    private PlayState apuPilotPredicate(AnimationState<ZionPeopleEntity> state) {
        if (this.hasVehicle()) {
            return state.setAndContinue(APU_PILOT_DRIVE);
        } else {
            return state.setAndContinue(COMMON);
        }
    }

    private PlayState infantryPredicate(AnimationState<ZionPeopleEntity> state) {
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive() && ROBOT_CLASSES.contains(target.getClass())) {
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
        return ZionPeopleEntitySpawnS2CPacket.jobIDMap.get(this.jobId);
    }

}
