package me.jaffe2718.the_matrix.element.entity.vehicle;

import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu.SelectRobotGoal;
import me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu.ZionAPUDrivingGoal;
import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.network.packet.s2c.play.APUEntitySpawnS2CPacket;
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
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
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
    private static final String BULLET_NUM_KEY = "BulletNum";
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected int bulletNum;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 3.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D);
    }

    public ArmoredPersonnelUnitEntity(EntityType<? extends ArmoredPersonnelUnitEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readCustomDataFromNbt(@NotNull NbtCompound nbt) {
        this.bulletNum = nbt.getInt(BULLET_NUM_KEY);
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public void writeCustomDataToNbt(@NotNull NbtCompound nbt) {
        nbt.putInt(BULLET_NUM_KEY, this.bulletNum);
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        if (packet instanceof APUEntitySpawnS2CPacket apuPacket) {
            this.bulletNum = apuPacket.getBulletNum();
        }
        super.onSpawnPacket(packet);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new APUEntitySpawnS2CPacket(this);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new SelectRobotGoal(this));
        this.goalSelector.add(1, new ZionAPUDrivingGoal(this));

    }

    public int getBulletNum() {
        return this.bulletNum;
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
    protected ActionResult interactMob(@NotNull PlayerEntity player, Hand hand) {
        if (this.hasPassenger(player)) {   // the player can only reload the APU when they're riding it
            if (player.getItemCooldownManager().isCoolingDown(player.getStackInHand(hand).getItem())) {
                return ActionResult.PASS;
            } else if (player.getStackInHand(hand).isOf(ItemRegistry.BULLET) && this.bulletNum < MAX_BULLET_NUM) {
                this.bulletNum++;
                player.getStackInHand(hand).decrement(1);
                player.getItemCooldownManager().set(ItemRegistry.BULLET, 25);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else if (player.getStackInHand(hand).isOf(ItemRegistry.BOXED_BULLETS)
                    && this.bulletNum <= MAX_BULLET_NUM - 10) {
                this.bulletNum += 10;
                player.getStackInHand(hand).decrement(1);
                player.getItemCooldownManager().set(ItemRegistry.BOXED_BULLETS, 25);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else if (player.getStackInHand(hand).isOf(ItemRegistry.BULLET_FILLING_BOX)
                    && this.bulletNum <= MAX_BULLET_NUM - 10) {
                this.bulletNum += 10;
                player.getStackInHand(hand).damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
                player.getItemCooldownManager().set(ItemRegistry.BULLET_FILLING_BOX, 10);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
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

    /**
     * Only the player can control the APUs.
     * If the driver is Zion people, the APU will be controlled by the AI.
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
    public boolean isLogicalSideForUpdatingMovement() {
        return true;
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
                this.getVelocity().length() > 0.05 &&
                this.getControllingPassenger() != null &&
            this.age % 20 == 0) {
            this.playSound(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_STEP, 1.0F, 1.0F);
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, serverWorld.getBlockState(this.getBlockPos().down())),
                        this.getX(), this.getY(), this.getZ(),
                        32, 1, 1, 1, 0.0);
            }
        }
        if (this.getControllingPassenger() instanceof PlayerEntity player && this.age % 4 == 0) {
            if (KeyBindings.FIRE_SAFETY_CATCH.isPressed()) {
                if (MinecraftClient.getInstance().options.attackKey.isPressed()) {
                    if (this.bulletNum > 0) {
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
                        // TODO: spawn particles
                        this.bulletNum--;
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
        }
        if (this.getVelocity().y < -0.1) {
            List<LivingEntity> steppedEntities = this.getSteppedEntities();
            for (LivingEntity steppedEntity : steppedEntities) {
                this.tryAttack(steppedEntity);
            }
        }
        if (this.age % 10 == 0) {
            this.setAir(this.getMaxAir());
            this.setAiDisabled(!(this.getFirstPassenger() instanceof ZionPeopleEntity));
        }
//        if (this.getFirstPassenger() instanceof ZionPeopleEntity) {
//            System.out.println("APU is controlled by AI -> isAiDisabled: " + this.isAiDisabled());
//        }
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
                KeyBindings.FIRE_SAFETY_CATCH.isPressed() && this.bulletNum > 0 &&
                this.hasPassenger(MinecraftClient.getInstance().player)) {
            return state.setAndContinue(SHOOT);
        } else if (state.isMoving() && this.getControllingPassenger() != null) {
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
