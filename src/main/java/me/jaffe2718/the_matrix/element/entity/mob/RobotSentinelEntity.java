package me.jaffe2718.the_matrix.element.entity.mob;

import me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel.IdleFlyGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel.RobotSentinelAttackGoal;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RobotSentinelEntity extends HostileEntity implements GeoEntity {

    static final RawAnimation COMMON = RawAnimation.begin().then("animation.robot_sentinel.common", Animation.LoopType.DEFAULT);

    public int attackCooldown = 0;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 180.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 50.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 256.0D);
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public RobotSentinelEntity(EntityType<? extends RobotSentinelEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    protected void initGoals() {
        // this.goalSelector.add(0, new RobotSentinelAttackGoal(this));
        this.goalSelector.add(1, new RobotSentinelAttackGoal(this));
        this.goalSelector.add(2, new IdleFlyGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, WardenEntity.class, true));
        // TODO: add target selector for zion citizens
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return distanceSquared > 65536.0D;
    }

    @Override
    public void tick() {
        super.tick();
        this.fallDistance = 0.0F;
        this.setAir(this.getMaxAir());
        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        // TODO: register controllers
        controllers.add(
                new AnimationController<>(this, "common", 0, this::predicate)
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState predicate(@NotNull AnimationState<RobotSentinelEntity> state) {
        state.setAnimation(COMMON);
        return PlayState.CONTINUE;
    }

    public boolean canContinueAttacking() {
        return this.attackCooldown == 0 &&
                this.getTarget() != null &&
                this.getTarget().isAlive() &&
                !this.getTarget().isInvisible();
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.attackCooldown = 100;   // reset attack cooldown
        return super.tryAttack(target);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEventRegistry.ROBOT_SENTINEL_AMBIENT;
    }
}
