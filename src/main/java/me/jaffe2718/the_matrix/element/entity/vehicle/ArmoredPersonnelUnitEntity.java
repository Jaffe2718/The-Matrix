package me.jaffe2718.the_matrix.element.entity.vehicle;

import me.jaffe2718.the_matrix.element.entity.ai.goal.apu.APURevengeGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.apu.ShootRobotGoal;
import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.unit.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

import static me.jaffe2718.the_matrix.client.model.entity.ArmoredPersonnelUnitModel.*;

public class ArmoredPersonnelUnitEntity extends PathAwareEntity implements GeoEntity {

    public static final int MAX_BULLET_NUM = 512;
    private static final TrackedData<Integer> BULLET_NUM = DataTracker.registerData(ArmoredPersonnelUnitEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IS_WALKING = DataTracker.registerData(ArmoredPersonnelUnitEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final String BULLET_NUM_KEY = "BulletNum";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2.0D)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 3.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D);
    }

    public ArmoredPersonnelUnitEntity(EntityType<? extends ArmoredPersonnelUnitEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BULLET_NUM, 0);
        this.dataTracker.startTracking(IS_WALKING, false);
    }

    @Override
    public void readCustomDataFromNbt(@NotNull NbtCompound nbt) {
        // this.bulletNum = nbt.getInt(BULLET_NUM_KEY);
        this.dataTracker.set(BULLET_NUM, nbt.getInt(BULLET_NUM_KEY));
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public void writeCustomDataToNbt(@NotNull NbtCompound nbt) {
        // nbt.putInt(BULLET_NUM_KEY, this.bulletNum);
        nbt.putInt(BULLET_NUM_KEY, this.dataTracker.get(BULLET_NUM));
        super.writeCustomDataToNbt(nbt);
    }

    public int getBulletNum() {
        return this.dataTracker.get(BULLET_NUM);
    }

    private void setBulletNum(int bulletNum) {
        this.dataTracker.set(BULLET_NUM, bulletNum);
    }

    private void addBulletNum(int bulletNum) {
        this.setBulletNum(Math.min(this.getBulletNum() + bulletNum, MAX_BULLET_NUM));
    }

    private void consumeBullet() {
        this.setBulletNum(Math.max(this.getBulletNum() - 1, 0));
    }

    public boolean isWalking() {
        return this.dataTracker.get(IS_WALKING);
    }

    public void setWalking(boolean isWalking) {
        this.dataTracker.set(IS_WALKING, isWalking);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return (passenger instanceof PlayerEntity || passenger instanceof ZionPeopleEntity) && !this.hasPassengers();
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        passenger.fallDistance = -3.0F;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new ShootRobotGoal(this));
        this.targetSelector.add(0, new APURevengeGoal(this));
    }

    @Override
    protected ActionResult interactMob(@NotNull PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if (this.hasPassenger(player)) {   // the player can only reload the APU when they're riding it
            if (player.getItemCooldownManager().isCoolingDown(handStack.getItem())) {
                return ActionResult.PASS;
            } else if (handStack.isOf(ItemRegistry.BULLET) && this.getBulletNum() < MAX_BULLET_NUM) {
                this.addBulletNum(1);
                handStack.decrement(1);
                player.getItemCooldownManager().set(ItemRegistry.BULLET, 25);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else if (handStack.isOf(ItemRegistry.BOXED_BULLETS)
                    && this.getBulletNum() <= MAX_BULLET_NUM - 10) {
                this.addBulletNum(10);
                handStack.decrement(1);
                player.getItemCooldownManager().set(ItemRegistry.BOXED_BULLETS, 25);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else if (handStack.isOf(ItemRegistry.BULLET_FILLING_BOX)
                    && this.getBulletNum() <= MAX_BULLET_NUM - 10) {
                this.addBulletNum(10);
                handStack.damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
                player.getItemCooldownManager().set(ItemRegistry.BULLET_FILLING_BOX, 10);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else {
                return ActionResult.PASS;
            }
        } else if (handStack.isOf(ItemRegistry.SPANNER)
                && this.getHealth() < this.getMaxHealth()
                && !player.getItemCooldownManager().isCoolingDown(ItemRegistry.SPANNER)) {
            this.setHealth(this.getHealth() + 10);
            handStack.damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
            player.getItemCooldownManager().set(ItemRegistry.SPANNER, 10);
            player.playSound(SoundEventRegistry.SPANNER_TWIST, 1.0F, 1.0F);
            return ActionResult.success(this.getWorld().isClient);
        } else if (this.hasPassengers()) {
            return ActionResult.PASS;
        } else {
            player.startRiding(this);
            return ActionResult.success(this.getWorld().isClient);
        }
    }

    /**
     * Only the player or the Zion people can control the APU.
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
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof PlayerEntity player &&
            player.equals(this.getControllingPassenger())) {
            return true;    // The player can't hurt the APUs they're riding.
        } else if (damageSource.getTypeRegistryEntry().getKey().isPresent() &&
                DamageTypes.FALL == damageSource.getTypeRegistryEntry().getKey().get()) {
            return true;    // The APUs are invulnerable to fall damage.
        }
        return super.isInvulnerableTo(damageSource) || this.equals(damageSource.getAttacker());
    }

    @Override
    public FallSounds getFallSounds() {
        return new FallSounds(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_LAND, SoundEventRegistry.ARMORED_PERSONNEL_UNIT_STEP);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void travel(Vec3d relativeVelocity) {
        if (this.getFirstPassenger() instanceof PlayerEntity player) {
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
            this.setRotation(player.getYaw(), player.getPitch());
            this.bodyYaw = this.getYaw();
            this.headYaw = this.bodyYaw;
            float x = player.sidewaysSpeed * 0.5F;
            float z = player.forwardSpeed;
            if (z <= 0)
                z *= 0.25f;
            this.setMovementSpeed(0.2F);
            if (MinecraftClient.getInstance().options.jumpKey.isPressed() && this.isOnGround()) {
                this.jump();
            }
            super.travel(new Vec3d(x, relativeVelocity.y, z));
            return;
        } else if (this.getTarget() != null) {
            // this.lookAtEntity(this.getTarget(), 30.0F, 90.0F);
            float pitch = MathUnit.getPitchDeg(MathUnit.relativePos(this.getPos(), this.getTarget().getPos()));
            this.prevPitch = this.getPitch();
            this.setPitch(pitch);
            this.setMovementSpeed(0.2F);
        }
        super.travel(relativeVelocity);
    }

    @Override
    public void tick() {
        if (this.getControllingPassenger() instanceof PlayerEntity player && this.age % 4 == 0) {
            this.setWalking((Math.abs(player.sidewaysSpeed) + Math.abs(player.forwardSpeed)) > 5e-2);
            if (this.isOnGround() &&
                    this.isWalking() &&
                    this.age % 20 == 0) {
                this.playSound(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_STEP, 1.0F, 1.0F);
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, serverWorld.getBlockState(this.getBlockPos().down())),
                            this.getX(), this.getY(), this.getZ(),
                            32, 1, 1, 1, 0.0);
                }
            }
            if (KeyBindings.FIRE_SAFETY_CATCH.isPressed()) {
                if (MinecraftClient.getInstance().options.attackKey.isPressed() &&
                        this.hasPassenger(MinecraftClient.getInstance().player)) {
                    if (this.getBulletNum() > 0) {
                        // step 1: calculate the velocity of the bullet and the position of the gun
                        Vec3d velocity = player.getRotationVector().multiply(12);
                        Vec3d gunPos = player.getEyePos().add(player.getRotationVector().multiply(2.4));
                        BulletEntity.shoot(this, gunPos, velocity);
                        // step 2: apply recoil (affect the player's head yaw and body pitch)
                        player.setYaw(player.getYaw() + this.random.nextFloat() * 3 - 1.5F);
                        player.setPitch(player.getPitch() - this.random.nextFloat() * 3);
                        // step 3: play sound & spawn particles & consume a bullet
                        if (this.age % 8 == 0) {
                            this.playSound(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_SHOOT, 1.0F, 1.0F);
                            this.playSound(SoundEventRegistry.BULLET_SHELL_HITTING_THE_GROUND, 1.0F, 1.0F);
                        }
                        Vec3d view = this.getRotationVector();
                        Vec3d leftGunPos = this.getPos().add(0, this.getHeight(), 0).add(view.multiply(4)).add(new Vec3d(-view.z, 0, view.x).normalize().multiply(2.5));
                        Vec3d rightGunPos = this.getPos().add(0, this.getHeight(), 0).add(view.multiply(4)).add(new Vec3d(view.z, 0, -view.x).normalize().multiply(2.5));
                        // spawn particles
                        if (this.getWorld() instanceof ServerWorld serverWorld) {
                            serverWorld.spawnParticles(ParticleRegistry.BULLET_SHELL, leftGunPos.getX(), leftGunPos.getY(), leftGunPos.getZ(),
                                    1, 0, 0, 0, 0.3);
                            serverWorld.spawnParticles(ParticleRegistry.BULLET_SHELL, rightGunPos.getX(), rightGunPos.getY(), rightGunPos.getZ(),
                                    1, 0, 0, 0, 0.3);
                        }
                        this.consumeBullet();
                    } else {
                        player.sendMessage(Text.translatable("message.the_matrix.apu_no_bullet"), true);
                    }
                } else {
                    player.sendMessage(Text
                            .translatable("message.the_matrix.press").append(" ")
                            .append(Text.translatable(MinecraftClient.getInstance().options.attackKey.getBoundKeyTranslationKey())).append(" ")
                            .append(Text.translatable("message.the_matrix.to_shoot")), true);
                }
            } else if (player.isMainPlayer()) {
                player.sendMessage(Text
                        .translatable("message.the_matrix.press").append(" ")
                        .append(Text.translatable(KeyBindings.FIRE_SAFETY_CATCH.getBoundKeyTranslationKey())).append(" ")
                        .append(Text.translatable("message.the_matrix.to_open_the_safety_catch")), true);
            }
        } else if (this.getFirstPassenger() instanceof ZionPeopleEntity zionPeople) {
            this.setTarget(zionPeople.getTarget());
            this.updatePassengerPosition(zionPeople);
        } else {
            this.setTarget(null);     // forget the target if no one is controlling the APU
            this.setWalking(false);
        }
        if (this.fallDistance > 0.5F) {
            List<LivingEntity> steppedEntities = this.getSteppedEntities();
            for (LivingEntity steppedEntity : steppedEntities) {
                this.tryAttack(steppedEntity);
            }
        }
        if (this.age % 10 == 0) {
            this.setAir(this.getMaxAir());
        }
        super.tick();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, @NotNull EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0F, dimensions.height - 1.6F, 0F);
    }

    @Override
    protected float getJumpVelocity() {
        return 0.8F;
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
        if (MinecraftClient.getInstance().options.attackKey.isPressed() &&
                KeyBindings.FIRE_SAFETY_CATCH.isPressed() && this.getBulletNum() > 0 &&
                this.hasPassenger(MinecraftClient.getInstance().player) || this.isAttacking()) {
            return state.setAndContinue(SHOOT);
        } else if (state.isMoving() && this.getFirstPassenger() != null) {
            return state.setAndContinue(WALK);
        } else {
            return state.setAndContinue(STATIC);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected List<LivingEntity> getSteppedEntities() {
        if (this.getPassengerList().isEmpty()) {
            return List.of();
        } else {
            return this.getWorld().getEntitiesByClass(
                    LivingEntity.class,
                    this.getBoundingBox(),
                    entity -> {
                        List<Entity> passengers = this.getPassengerList();
                        if (passengers.contains(entity)) {                     // cannot step on the passenger
                            return false;
                        } else if (entity instanceof PlayerEntity player) {    // cannot step on the creative or spectator player
                            return !player.isSpectator() && !player.isCreative();
                        } else {             // cannot step on the entity that is too high
                            return entity.getHeight() <= this.getHeight() * 0.5 && entity.getY() <= this.getY() + 1;
                        }
                    });
        }
    }
}
