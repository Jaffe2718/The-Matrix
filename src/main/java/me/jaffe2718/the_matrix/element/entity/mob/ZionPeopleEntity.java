package me.jaffe2718.the_matrix.element.entity.mob;

import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.SelectEnemyGoal;
import me.jaffe2718.the_matrix.network.packet.s2c.play.ZionPeopleEntitySpawnS2CPacket;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
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
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ZionPeopleEntity
        extends PathAwareEntity
        implements Npc, Merchant, GeoEntity {

    private static final String JOB_ID_KEY = "JobID";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private PlayerEntity customer;
    @Nullable
    protected TradeOfferList offers;
    public int jobId;   // 0 -> (random), 1 -> AUP Pilot, 2 -> Carpenter, ...

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D);
    }

    public ZionPeopleEntity(EntityType<? extends ZionPeopleEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readCustomDataFromNbt(@NotNull NbtCompound nbt) {
        this.jobId = nbt.getInt(JOB_ID_KEY);
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public void writeCustomDataToNbt(@NotNull NbtCompound nbt) {
        nbt.putInt(JOB_ID_KEY, this.jobId);
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        if (packet instanceof ZionPeopleEntitySpawnS2CPacket zionPacket) {
            int tempJobID = zionPacket.getJobID();
            if (tempJobID != 0 && tempJobID <= 9) {
                this.jobId = tempJobID;
            } else {
                this.jobId = this.getRandom().nextInt(8) + 1;
            }
        } else {
            this.jobId = this.getRandom().nextInt(8) + 1;
        }
        super.onSpawnPacket(packet);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new ZionPeopleEntitySpawnS2CPacket(this);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected void initGoals() {
        // universal goals for all jobs
        this.goalSelector.add(2, new SwimGoal(this));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, LivingEntity.class, 32, 0.25, 0.35F,
                livingEntity -> EntityRegistry.ROBOT_CLASSES.contains(livingEntity.getClass())));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 0.35D));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, ZionPeopleEntity.class, 8.0F));
        switch (this.jobId) {  // TODO: Add job-specific goals
            case 1 -> {    // APU Pilot
                this.targetSelector.add(1, new SelectEnemyGoal(this, true));
            }
            case 2 -> {    // Carpenter
            }
            case 3 -> {    // Farm Breeder
            }
            case 4 -> {    // Farmer
            }
            case 5 -> {    // Grocer
            }
            case 6 -> {    // Infantry
                this.targetSelector.add(1, new SelectEnemyGoal(this, true));
            }
            case 7 -> {    // Machinist
            }
            case 8 -> {    // Miner
            }
            case 9 -> {    // Rifleman
                this.targetSelector.add(1, new SelectEnemyGoal(this, true));
                // use the machine gun
            }
        }
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
            this.offers = new TradeOfferList();
            this.fillRecipes();
        }
        return this.offers;
    }

    protected void fillRecipes() {    // TODO: Fill this in
    }

    @Override
    public void setOffersFromServer(TradeOfferList offers) {
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
        return 0;
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
            }
            case 2 -> {    // Carpenter
            }
            case 3 -> {    // Farm Breeder
            }
            case 4 -> {    // Farmer
            }
            case 5 -> {    // Grocer
            }
            case 6 -> {    // Infantry
            }
            case 7 -> {    // Machinist
            }
            case 8 -> {    // Miner
            }
            case 9 -> {    // Rifleman
            }
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
