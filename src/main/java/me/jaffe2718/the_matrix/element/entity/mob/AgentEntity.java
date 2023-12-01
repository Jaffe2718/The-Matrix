package me.jaffe2718.the_matrix.element.entity.mob;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AgentEntity extends HostileEntity implements GeoEntity {

    // public static final TrackedData<Integer> ATTACKING_TICKS = DataTracker.registerData(AgentEntity.class, TrackedDataHandlerRegistry.INTEGER);
    // public static final TrackedData<Boolean> FIST_OR_KICK = DataTracker.registerData(AgentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static DefaultAttributeContainer.Builder createAgentAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D);
    }

    static final RawAnimation IDLE = RawAnimation.begin().then("animation.agent.idle", Animation.LoopType.DEFAULT);
    static final RawAnimation KICK = RawAnimation.begin().then("animation.agent.kick", Animation.LoopType.DEFAULT);
    static final RawAnimation WALK = RawAnimation.begin().then("animation.agent.walk", Animation.LoopType.DEFAULT);
    static final RawAnimation WAVE_FIST = RawAnimation.begin().then("animation.agent.wave_fist", Animation.LoopType.DEFAULT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean fistOrKick = true;  // true = fist, false = kick
    private int attackingTicks = 0;

    public AgentEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 32.0F));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.handSwinging = true;
        return super.tryAttack(target);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    /**
     * Register your {@link AnimationController AnimationControllers} and their respective animations and conditions.
     * Override this method in your animatable object and add your controllers via {@link AnimatableManager.ControllerRegistrar#add ControllerRegistrar.add}.
     * You may add as many controllers as wanted.
     * <br><br>
     * Each controller can only play <u>one</u> animation at a time, and so animations that you intend to play concurrently should be handled in independent controllers.
     * Note having multiple animations playing via multiple controllers can override parts of one animation with another if both animations use the same bones or child bones.
     *
     * @param controllers The object to register your controller instances to
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // controllers.add(new AnimationController<>(this, "controller", 20, this::predicate));
        controllers.add(
                new AnimationController<>(this, "Attack", 5, this::handleAttack),
                new AnimationController<>(this, "Walk", this::handleWalk),
                new AnimationController<>(this, "Idle", 16, this::handleIdle)
        );
    }

    /**
     * Each instance of a {@code GeoAnimatable} must return an instance of an {@link AnimatableInstanceCache}, which handles instance-specific animation info.
     * Generally speaking, you should create your cache using {@code GeckoLibUtil#createCache} and store it in your animatable instance, returning that cached instance when called.
     *
     * @return A cached instance of an {@code AnimatableInstanceCache}
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState handleAttack(AnimationState<AgentEntity> state) {
        if (this.handSwinging) {
            this.attackingTicks = 50;
        } else if (this.attackingTicks > 0) {
            this.attackingTicks--;
        } else {   // attackingTicks == 0 -> reset fistOrKick
            this.fistOrKick = this.random.nextBoolean();
        }
        if (this.attackingTicks == 50) {
            if (this.fistOrKick) {
                this.fistOrKick = this.random.nextBoolean();
                return state.setAndContinue(KICK);
            } else {
                this.fistOrKick = this.random.nextBoolean();
                return state.setAndContinue(WAVE_FIST);
            }
        } else {
            return PlayState.CONTINUE;
        }
    }

    private PlayState handleWalk(@NotNull AnimationState<AgentEntity> state) {
        if (state.isMoving() && this.attackingTicks == 0) {
            return state.setAndContinue(WALK);
        } else {
            return PlayState.STOP;
        }
    }

    private PlayState handleIdle(@NotNull AnimationState<AgentEntity> state) {
        if (!state.isMoving() && this.attackingTicks == 0) {
            return state.setAndContinue(IDLE);
        } else {
            return PlayState.STOP;
        }
    }

}
