package me.jaffe2718.the_matrix.element.entity.vehicle;

import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ArmoredPersonnelUnitEntity extends PathAwareEntity implements GeoEntity {
    static final RawAnimation WALK = RawAnimation.begin().then("animation.armored_personnel_unit.walk", Animation.LoopType.DEFAULT);
    public static final int MAX_BULLET_NUM = 256;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected int bulletNum = 0;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D);
    }

    public ArmoredPersonnelUnitEntity(EntityType<? extends ArmoredPersonnelUnitEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.bulletNum = nbt.getInt("BulletNum");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {  // TODO: test if the nbt is saved in the archive
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("BulletNum", this.bulletNum);
    }

    @Override
    public boolean shouldSave() {
        return super.shouldSave();
    }

    public int getBulletNum() {
        return this.bulletNum;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return passenger instanceof PlayerEntity && !this.hasPassengers();
    }

    @Override
    protected ActionResult interactMob(@NotNull PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).getItem() == ItemRegistry.BULLET) {
            if (this.bulletNum < MAX_BULLET_NUM) {
                this.bulletNum++;
                player.getStackInHand(hand).decrement(1);
                return ActionResult.success(this.getWorld().isClient);
            } else {
                return ActionResult.PASS;
            }
        } else if (this.hasPassengers()) {
            return ActionResult.PASS;
        } else {
            player.startRiding(this);
            return ActionResult.success(this.getWorld().isClient);
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        if (passenger instanceof PlayerEntity player) {
            return player;
        } else {
            return super.getControllingPassenger();
        }
    }

    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof PlayerEntity player &&
            player.equals(this.getControllingPassenger())) {
            return true;    // The player can't hurt the APUs they're riding.
        }
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void travel(Vec3d pos) {
        if (this.isAlive()) {
            if (this.getControllingPassenger() instanceof PlayerEntity passenger) {
                this.prevYaw = getYaw();
                this.prevPitch = getPitch();

                setYaw(passenger.getYaw());
                setPitch(passenger.getPitch() * 0.5f);
                setRotation(getYaw(), getPitch());

                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                float x = passenger.sidewaysSpeed * 0.5F;
                float z = passenger.forwardSpeed;
                if (z <= 0)
                    z *= 0.25f;
                this.setMovementSpeed(0.2F);
                if (passenger instanceof PlayerEntity) {
                    if (MinecraftClient.getInstance().options.jumpKey.isPressed() && this.isOnGround()) {
                        this.jump();
                    }
                }
                super.travel(new Vec3d(x, pos.y, z));
            } else {
                super.travel(pos);
            }
        }
    }

    @Override
    public void tick() {
        if (this.isOnGround() &&
                this.getVelocity().length() > 0.1 &&
                this.getControllingPassenger() != null &&
        this.age % 20 == 0) {
            this.playSound(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_STEP, 1.0F, 0.5F);
        }
        this.fallDistance = 0;
        super.tick();
    }

    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, @NotNull EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0F, dimensions.height - 1.6F, 0F);
    }

    @Override
    protected float getJumpVelocity() {
        return 1F;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEventRegistry.ARMORED_PERSONNEL_UNIT_HURT;
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "Walk", this::handleWalk)
        );
    }

    private PlayState handleWalk(@NotNull AnimationState<ArmoredPersonnelUnitEntity> state) {
        if (state.isMoving() && this.getControllingPassenger() != null) {
            state.setAnimation(WALK);
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
