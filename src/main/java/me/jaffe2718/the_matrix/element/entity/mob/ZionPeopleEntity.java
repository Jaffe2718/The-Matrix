package me.jaffe2718.the_matrix.element.entity.mob;

import me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel.SelectTargetGoal;
import me.jaffe2718.the_matrix.network.packet.s2c.play.ZionPeopleEntitySpawnS2CPacket;
import net.minecraft.entity.EntityType;
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
            if (tempJobID != 0) {
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
    protected void initGoals() {
        // universal goals for all jobs
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, RobotSentinelEntity.class, 16, 0.2, 0.35F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new WanderAroundGoal(this, 0.15D));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, ZionPeopleEntity.class, 8.0F));
        switch (this.jobId) {  // TODO: Add job-specific goals
            case 1 -> {    // APU Pilot
                // this.goalSelector.add(2);
            }
            case 2 -> {    // Carpenter
            }
            case 3 -> {    // Farm Keeper
            }
            case 4 -> {    // Farmer
            }
            case 5 -> {    // Grocer
            }
            case 6 -> {    // Infantry
                // TODO: Add other robot types
                this.targetSelector.add(1, new SelectTargetGoal<>(this, RobotSentinelEntity.class, true));
            }
            case 7 -> {    // Machinist
            }
            case 8 -> {    // Miner
            }
            case 9 -> {    // Riflemen
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
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public String getJobName() {
        return ZionPeopleEntitySpawnS2CPacket.jobIDMap.get(this.jobId);
    }

}
