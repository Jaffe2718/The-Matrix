package me.jaffe2718.the_matrix.element.entity.mob;

import me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel.AttackGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel.IdleFlyGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel.SelectTargetGoal;
import me.jaffe2718.the_matrix.unit.MathUnit;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static me.jaffe2718.the_matrix.client.model.entity.RobotSentinelModel.ATTACK_0;
import static me.jaffe2718.the_matrix.client.model.entity.RobotSentinelModel.COMMON;

public class RobotSentinelEntity extends HostileEntity implements GeoEntity {

    private int attackCooldown = 0;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 180.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 40.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 2.5D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.2D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 128.0D);
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public RobotSentinelEntity(EntityType<? extends RobotSentinelEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    protected void initGoals() {
        // this.goalSelector.add(0, new AttackGoal(this));
        this.goalSelector.add(1, new AttackGoal(this));
        this.goalSelector.add(2, new IdleFlyGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new SelectTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new SelectTargetGoal<>(this, ZionPeopleEntity.class, true));
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
        if (!this.getWorld().isClient() &&
                this.getTarget() != null &&
                this.canContinueAttacking() &&
                MathUnit.isBetween(this.distanceTo(this.getTarget()), 7.0D, 11.0D) &&
                MathUnit.vec3dCos(this.getVelocity(),
                        MathUnit.relativePos(this.getPos(), this.getTarget().getPos())) > 0.5D) {
                this.swingHand(this.getActiveHand(), true);
        }

    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        super.onDamaged(damageSource);
        if (damageSource.getAttacker() != null) {
            if (damageSource.getAttacker() instanceof LivingEntity livingEntity) {
                this.setTarget(livingEntity);
            } else if (damageSource.getAttacker() instanceof ProjectileEntity projectileEntity) {
                if (projectileEntity.getOwner() instanceof LivingEntity livingEntity) {
                    this.setTarget(livingEntity);
                }
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "Robot Sentinel Controller", this::handleAnimation)//,
        );
    }

    private PlayState handleAnimation(AnimationState<RobotSentinelEntity> state) {
        if (this.handSwinging || (state.isCurrentAnimation(ATTACK_0) && !state.getController().hasAnimationFinished())) {
            return state.setAndContinue(ATTACK_0);
        } else {
            return state.setAndContinue(COMMON);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
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
        this.swingHand(this.getActiveHand(), true);
        return super.tryAttack(target);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEventRegistry.ROBOT_SENTINEL_RADAR_DETECTION;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEventRegistry.ROBOT_SENTINEL_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventRegistry.ROBOT_SENTINEL_DEATH;
    }

}
