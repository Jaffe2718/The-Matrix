package me.jaffe2718.the_matrix.element.entity.vehicle;

import me.jaffe2718.the_matrix.element.entity.ai.goal.machine_gun.ShootEnemyGoal;
import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.unit.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import static me.jaffe2718.the_matrix.client.model.entity.MachineGunModel.COMMON;

public class MachineGunEntity extends PathAwareEntity implements GeoEntity {

    public static final int MAX_BULLET_NUM = 128;
    private static final TrackedData<Integer> BULLET_NUM = DataTracker.registerData(MachineGunEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    /** Work on the server side, to apply the shoot cooldown, min=0, max=4, default=0 */
    public int shootCooldown = 0;

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 128.0D);
    }

    public MachineGunEntity(EntityType<? extends MachineGunEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BULLET_NUM, 0);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(BULLET_NUM, nbt.getInt("BulletNum"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("BulletNum", this.dataTracker.get(BULLET_NUM));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new ShootEnemyGoal(this));
    }

    @Override
    protected ActionResult interactMob(@NotNull PlayerEntity player, Hand hand) {
        if (!player.getItemCooldownManager().isCoolingDown(player.getStackInHand(hand).getItem())) {
            int currentBulletNum = this.dataTracker.get(BULLET_NUM);
            if (player.getStackInHand(hand).isOf(ItemRegistry.BULLET) && currentBulletNum < MAX_BULLET_NUM) {
                this.dataTracker.set(BULLET_NUM, currentBulletNum + 1);
                player.getStackInHand(hand).decrement(1);
                player.getItemCooldownManager().set(ItemRegistry.BULLET, 25);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else if (player.getStackInHand(hand).isOf(ItemRegistry.BOXED_BULLETS) && currentBulletNum <= MAX_BULLET_NUM - 10) {
                this.dataTracker.set(BULLET_NUM, currentBulletNum + 10);
                player.getStackInHand(hand).decrement(1);
                player.getItemCooldownManager().set(ItemRegistry.BOXED_BULLETS, 25);
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else if (player.getStackInHand(hand).isOf(ItemRegistry.BULLET_FILLING_BOX) && currentBulletNum <= MAX_BULLET_NUM - 10) {
                this.dataTracker.set(BULLET_NUM, currentBulletNum + 10);
                player.getItemCooldownManager().set(ItemRegistry.BULLET_FILLING_BOX, 10);
                player.getStackInHand(hand).damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
                this.playSound(SoundEventRegistry.LOADING_BULLET, 1.0F, 1.0F);
                return ActionResult.success(this.getWorld().isClient);
            } else if (player.getStackInHand(hand).isOf(ItemRegistry.SPANNER)) {
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(10F);
                    player.getItemCooldownManager().set(player.getStackInHand(hand).getItem(), 10);
                    player.getStackInHand(hand).damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
                    this.playSound(SoundEventRegistry.SPANNER_TWIST, 1.0F, 1.0F);
                    return ActionResult.success(this.getWorld().isClient);
                } else {
                    return ActionResult.PASS;
                }
            }
        } else {
            return ActionResult.PASS;
        }
        if (!this.hasPassengers()) {
            player.startRiding(this);
            return ActionResult.success(this.getWorld().isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        Entity attacker = damageSource.getAttacker();
        if (this.hasPassenger(attacker)) {
            return true;
        } else {
            return super.isInvulnerableTo(damageSource);
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return passenger instanceof PlayerEntity || passenger instanceof ZionPeopleEntity;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            this.prevYaw = getYaw();
            this.prevPitch = getPitch();
            if (this.getControllingPassenger() instanceof PlayerEntity passenger) {
                this.setRotation(passenger.getYaw(), passenger.getPitch());
                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
            } else if (this.getFirstPassenger() instanceof ZionPeopleEntity
                    && this.getTarget() != null && this.getTarget().isAlive()) {
                this.lookAtEntity(this.getTarget(), 30, 360);
            }
        }
        super.travel(movementInput);
    }

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

    /**
     * Similar to {@link me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity#tick()}
     * @see MachineGunEntity#shoot()
     * @see MachineGunEntity#travel(Vec3d) 
     * @see me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity#tick()
     * @see me.jaffe2718.the_matrix.mixin.server.network.ServerPlayNetworkHandlerMixin
     */
    @Override
    public void tick() {
        if (this.shootCooldown > 0) {
            this.shootCooldown--;
        }
        if (this.getControllingPassenger() instanceof ClientPlayerEntity clientPlayer) {
            if (this.getBulletNum() == 0) {
                clientPlayer.sendMessage(Text.translatable("message.the_matrix.machine_gun.should_charge"), true);
            } else if (!KeyBindings.FIRE_SAFETY_CATCH.isPressed()) {
                clientPlayer.sendMessage(Text.translatable("message.the_matrix.press")
                        .append(" ")
                        .append(KeyBindings.FIRE_SAFETY_CATCH.getBoundKeyLocalizedText())
                        .append(" ")
                        .append(Text.translatable("message.the_matrix.to_open_the_safety_catch")), true);
            } else if (!MinecraftClient.getInstance().options.attackKey.isPressed()) {
                clientPlayer.sendMessage(Text.translatable("message.the_matrix.press")
                        .append(" ")
                        .append(MinecraftClient.getInstance().options.attackKey.getBoundKeyLocalizedText())
                        .append(" ")
                        .append(Text.translatable("message.the_matrix.to_shoot")), true);
            }
            clientPlayer.networkHandler.sendPacket(new ButtonClickC2SPacket(
                    clientPlayer.currentScreenHandler.syncId,
                    (KeyBindings.FIRE_SAFETY_CATCH.isPressed() ? KeyBindings.BUTTON_EVENT_IDS[0] : KeyBindings.BUTTON_EVENT_IDS[3])));
            clientPlayer.networkHandler.sendPacket(new ButtonClickC2SPacket(
                    clientPlayer.currentScreenHandler.syncId,
                    (MinecraftClient.getInstance().options.attackKey.isPressed() ? KeyBindings.BUTTON_EVENT_IDS[1] : KeyBindings.BUTTON_EVENT_IDS[4])));
        }
        if (this.age % 4 == 0) {
            if (this.getFirstPassenger() instanceof ZionPeopleEntity zionPeople) {
                this.setTarget(zionPeople.getTarget());
                this.updatePassengerPosition(zionPeople);
            } else {
                this.setTarget(null);
            }
            this.setAir(this.getMaxAir());
            this.fallDistance = 0;
        }
        super.tick();
    }

    public void shoot() {
        this.shootCooldown = 4;
        if (this.getBulletNum() > 0
                && this.getControllingPassenger() instanceof ServerPlayerEntity serverPlayer) {
            // shoot
            BulletEntity.shoot(this,
                    serverPlayer.getEyePos().add(MathUnit.getRotationVector(serverPlayer.getPitch(), serverPlayer.getHeadYaw())),
                    serverPlayer.getRotationVector().multiply(10));
            // play sound
            this.playSound(SoundEventRegistry.MACHINE_GUN_SHOOT, 1.0F, 1.0F);
            this.playSound(SoundEventRegistry.BULLET_SHELL_HITTING_THE_GROUND, 1.0F, 1.0F);
            // spawn particles
            Vec3d muzzlePos = this.getPos().add(0, this.getHeight(), 0).add(this.getRotationVector().multiply(2.0));
            serverPlayer.getServerWorld().spawnParticles(ParticleRegistry.BULLET_SHELL, muzzlePos.getX(), muzzlePos.getY(), muzzlePos.getZ(), 1, 0, 0, 0, 0.3);
            // apply recoil
            serverPlayer.setYaw(serverPlayer.getYaw() + this.random.nextFloat() * 3 - 1.5F);
            serverPlayer.setPitch(serverPlayer.getPitch() - this.random.nextFloat() * 3);
            // consume bullet
            this.consumeBullet();
        }
    }

    private void consumeBullet() {
        this.dataTracker.set(BULLET_NUM, Math.max(0, this.dataTracker.get(BULLET_NUM) - 1));
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> state.setAndContinue(COMMON)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean isShooting() {
        return this.dataTracker.get(BULLET_NUM) > 0 && this.getFirstPassenger() instanceof PlayerEntity &&
                MinecraftClient.getInstance().options.attackKey.isPressed()
                && KeyBindings.FIRE_SAFETY_CATCH.isPressed() || this.isAttacking();
    }

    public int getBulletNum() {
        return this.dataTracker.get(BULLET_NUM);
    }
}
