package me.jaffe2718.the_matrix.element.entity.boss;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static me.jaffe2718.the_matrix.client.model.entity.DiggerRobotModel.COMMON;

public class DiggerRobotEntity extends HostileEntity implements GeoEntity {
    /**
     * Summon robot sentinel group number, total 5 groups.<br>
     * | difficulty | easy | normal | hard |<br>
     * | ---------- | ---- | ------ | ---- |<br>
     * | sentinel   | 3    | 4      | 5    |<br>
     * */
    private static final TrackedData<Integer> SUMMON_ROBOT_SENTINEL_GROUPS = DataTracker.registerData(DiggerRobotEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossBar bossBar;

    public DiggerRobotEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = new ServerBossBar(this.getDisplayName(), ServerBossBar.Color.PURPLE, ServerBossBar.Style.PROGRESS);
        this.dataTracker.set(SUMMON_ROBOT_SENTINEL_GROUPS, MathHelper.clamp((int) Math.floor(this.getHealth() / 250), 0, 5));
    }

    public static DefaultAttributeContainer createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1500.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 5.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 16.0)
                .build();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SUMMON_ROBOT_SENTINEL_GROUPS, 5);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        if (damageSource.getTypeRegistryEntry().getKey().isPresent() &&
                DamageTypes.FALL == damageSource.getTypeRegistryEntry().getKey().get()) {
            return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void tick() {
        if (this.getHealth() < 250 * this.dataTracker.get(SUMMON_ROBOT_SENTINEL_GROUPS)) {
            System.out.println("summon robot sentinel");
            // get difficulty
            int difficulty = this.getWorld().getDifficulty().getId();
            if (difficulty > 0) {
                this.summonRobotSentinel(difficulty + 2);
            }
            this.dataTracker.set(SUMMON_ROBOT_SENTINEL_GROUPS, this.dataTracker.get(SUMMON_ROBOT_SENTINEL_GROUPS) - 1);
        }
        if (this.age % 500 == 0) {
            this.jump();
        }
        if (this.fallDistance > 0.5F) {
            List<LivingEntity> steppedEntities = this.getSteppedEntities();
            for (LivingEntity steppedEntity : steppedEntities) {
                this.tryAttack(steppedEntity);
            }
        }
        this.removeStatusEffect(StatusEffects.LEVITATION);
        super.tick();
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected float getJumpVelocity() {
        return 1.5F;
    }

    private void summonRobotSentinel(int count) {
        for (int i = 0; i < count; i++) {
            Entity entity = new RobotSentinelEntity(EntityRegistry.ROBOT_SENTINEL, this.getWorld());
            entity.setPosition(this.getX(), this.getY(), this.getZ());
            this.getWorld().spawnEntity(entity);
        }
    }

    protected List<LivingEntity> getSteppedEntities() {
        return this.getWorld().getEntitiesByClass(
                LivingEntity.class,
                this.getBoundingBox().offset(0, -1, 0),
                entity ->
                        entity.getHeight() <= this.getHeight() * 0.5 && entity.getY() <= this.getY() + 1
                                && !EntityRegistry.ROBOT_CLASSES.contains(entity.getClass())
                );
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> state.setAndContinue(COMMON)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
