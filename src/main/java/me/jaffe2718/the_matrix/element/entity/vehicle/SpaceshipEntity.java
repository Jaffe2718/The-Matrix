package me.jaffe2718.the_matrix.element.entity.vehicle;

import me.jaffe2718.the_matrix.client.model.entity.SpaceshipModel;
import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.KeyBindings;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

/**
 * The spaceship entity.
 * @see SpaceshipModel
 * @see me.jaffe2718.the_matrix.client.render.entity.SpaceshipRenderer
 * @see me.jaffe2718.the_matrix.unit.EntityRegistry#SPACESHIP
 * */
public class SpaceshipEntity
        extends PathAwareEntity
        implements GeoEntity {

    public static final int MAX_POWER = 24;

    /** Tracks the amount of power remaining in the spaceship, min = 0, max = 24, default = 0 */
    private static final TrackedData<Integer> REMAINING_POWER = DataTracker.registerData(SpaceshipEntity.class, TrackedDataHandlerRegistry.INTEGER);

    /** Tracks the amount of ticks the entity has been accelerated for, min = 0, max = 30 */
    private static final TrackedData<Integer> ACCELERATE_TICKS = DataTracker.registerData(SpaceshipEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /** Work on the server side, to apply the shoot cooldown, min=0, max=10, default=0 */
    private int shootCooldown;

    public static DefaultAttributeContainer createAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 50.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 5.0D)
                .build();
    }

    public SpaceshipEntity(EntityType<? extends SpaceshipEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACCELERATE_TICKS, 0);
        this.dataTracker.startTracking(REMAINING_POWER, 0);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(REMAINING_POWER, nbt.getInt("RemainingPower"));
        this.shootCooldown = nbt.getInt("ShootCooldown");
        this.dataTracker.set(ACCELERATE_TICKS, nbt.getInt("AccelerateTicks"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("RemainingPower", this.dataTracker.get(REMAINING_POWER));
        nbt.putInt("ShootCooldown", this.shootCooldown);
        nbt.putInt("AccelerateTicks", this.dataTracker.get(ACCELERATE_TICKS));
    }

    @Override
    protected ActionResult interactMob(@NotNull PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isOf(ItemRegistry.BATTERY)
                && !player.getItemCooldownManager().isCoolingDown(ItemRegistry.BATTERY)
                && this.dataTracker.get(REMAINING_POWER) < MAX_POWER) {
            this.charge();
            handStack.decrement(1);
            player.getItemCooldownManager().set(ItemRegistry.BATTERY, 40);
            return ActionResult.success(this.getWorld().isClient);
        } else if (handStack.isOf(ItemRegistry.SPANNER)
                && !player.getItemCooldownManager().isCoolingDown(ItemRegistry.SPANNER)
                && this.getHealth() < this.getMaxHealth()) {
            this.heal(20F);
            handStack.damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
            player.getItemCooldownManager().set(ItemRegistry.SPANNER, 20);
            return ActionResult.success(this.getWorld().isClient);
        } else if (!this.hasPassengers() && !player.hasVehicle()) {
            player.startRiding(this);
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.getFirstPassenger() instanceof PlayerEntity player) {
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
            this.setRotation(player.getYaw(),
                    player.getPitch() < 0 ? player.getPitch() / 1.75F : player.getPitch() / 1.25F);
            this.bodyYaw = this.getYaw();
            this.headYaw = this.bodyYaw;
            float x, y, z;
            float forwardSpeed = player.forwardSpeed > 0 ? player.forwardSpeed : player.forwardSpeed * 0.35F;
            x = player.sidewaysSpeed * 0.5F;
            y = -forwardSpeed * MathHelper.sin(player.getPitch() * 0.017453292F);
            z = forwardSpeed * MathHelper.cos(player.getPitch() * 0.017453292F);
            this.setMovementSpeed(this.isAccelerating() ? 3.0F : 1.0F);
            super.travel(new Vec3d(x, y, z));
            return;
        }
        super.travel(movementInput);
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof PlayerEntity player &&
                player.equals(this.getControllingPassenger())) {
            return true;    // The player can't hurt the spaceship while riding it.
        } else if (damageSource.getTypeRegistryEntry().getKey().isPresent()) {
            RegistryKey<DamageType> damageType = damageSource.getTypeRegistryEntry().getKey().get();
            return damageType == DamageTypes.FALL || damageType == DamageTypes.DROWN;
        }
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public void tick() {
        this.setOnGround(!this.hasPassengers() && super.isOnGround());
        this.setNoGravity(this.hasPassengers());
        this.fallDistance = 0;
        this.setAir(this.getMaxAir());
        if (this.isAccelerating()) {
            this.consumeAccelerateTick();
            this.hitEntities();
        }
        if (this.shootCooldown > 0) {
            this.shootCooldown--;
        }
        if (this.getControllingPassenger() instanceof PlayerEntity player) {
            if (this.getPower() > 0) {
                if (KeyBindings.FIRE_SAFETY_CATCH.isPressed() && this.hasPassenger(MinecraftClient.getInstance().player)) {
                    if (MinecraftClient.getInstance().options.attackKey.isPressed() && this.shootCooldown == 0) {
                        this.shoot();
                    } else if (MinecraftClient.getInstance().options.useKey.isPressed() && !this.isAccelerating()) {
                        this.startAccelerating();
                    } else if (!this.getWorld().isClient) {
                        if (this.age % 150 > 74) {
                            player.sendMessage(Text.translatable("message.the_matrix.press")
                                    .append(" ")
                                    .append(Text.translatable(MinecraftClient.getInstance().options.attackKey.getBoundKeyTranslationKey()))
                                    .append(" ")
                                    .append(Text.translatable("message.the_matrix.to_shoot")), true);
                        } else {
                            player.sendMessage(Text.translatable("message.the_matrix.press")
                                    .append(" ")
                                    .append(Text.translatable(MinecraftClient.getInstance().options.useKey.getBoundKeyTranslationKey()))
                                    .append(" ")
                                    .append(Text.translatable("message.the_matrix.to_accelerate")), true
                            );
                        }
                    }
                } else {
                    player.sendMessage(Text
                            .translatable("message.the_matrix.press").append(" ")
                            .append(Text.translatable(KeyBindings.FIRE_SAFETY_CATCH.getBoundKeyTranslationKey())).append(" ")
                            .append(Text.translatable("message.the_matrix.to_open_the_safety_catch")), true);
                }
            } else {
                player.sendMessage(Text.translatable("message.the_matrix.spaceship.should_charge"), true);
            }
        }
        super.tick();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected Vector3f getPassengerAttachmentPos(@NotNull Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        Vector3f rawOffset = new Vector3f(0, 0.9F, 0.75F);
        float dz = passenger.getPitch() > 0 ? passenger.getPitch() / 240F : 0;
        float pitch = passenger.getPitch() > 0 ?
                passenger.getPitch() * 0.017453292F * 0.8F : passenger.getPitch() * 0.017453292F;
        return rawOffset.add(0, 0, dz).rotateX(pitch);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return (passenger instanceof PlayerEntity) && !this.hasPassengers();
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        passenger.fallDistance = -3.0F;
    }

    /**
     * Only the player can control the spaceship.
     * */
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        if (passenger instanceof PlayerEntity player) {
            return player;
        } else {
            return null;
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public boolean isAccelerating() {
        return this.dataTracker.get(ACCELERATE_TICKS) > 0 && this.hasPassengers();
    }

    private void consumeAccelerateTick() {
        this.dataTracker.set(ACCELERATE_TICKS, Math.max(0, this.dataTracker.get(ACCELERATE_TICKS) - 1));
    }

    private void startAccelerating() {
        this.getWorld().playSoundFromEntity(this, SoundEventRegistry.SPACESHIP_ACCELERATING, this.getSoundCategory(), 1.0F, 1.0F);
        this.dataTracker.set(REMAINING_POWER, Math.max(0, this.dataTracker.get(REMAINING_POWER) - 1));
        this.dataTracker.set(ACCELERATE_TICKS, 30);
    }

    private void charge() {
        this.dataTracker.set(REMAINING_POWER, Math.min(24, this.dataTracker.get(REMAINING_POWER) + 6));
    }

    public int getPower() {
        return this.dataTracker.get(REMAINING_POWER);
    }

    private void shoot() {
        this.shootCooldown = 10;
        this.dataTracker.set(REMAINING_POWER, Math.max(0, this.dataTracker.get(REMAINING_POWER) - 1));
        if (this.getControllingPassenger() instanceof PlayerEntity player) {
            Vec3d gunPos = player.getEyePos().add(player.getRotationVector().multiply(2.5D));
            Vec3d bulletVelocity = player.getRotationVector().multiply(16D);
            ElectromagneticBulletEntity.shoot(this, gunPos, bulletVelocity);
            this.playSound(SoundEventRegistry.ELECTROMAGNETIC_GUN_SHOOT, 1.0F, 1.0F);
        }
    }

    private void hitEntities() {
        List<LivingEntity> entities = this.getWorld().getEntitiesByClass(
                LivingEntity.class,
                this.getBoundingBox().expand(1.0D),
                entity -> entity != this && entity != this.getControllingPassenger());
        for (LivingEntity entity : entities) {
            this.tryAttack(entity);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> state.setAndContinue(SpaceshipModel.COMMON)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
