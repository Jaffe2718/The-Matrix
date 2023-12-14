package me.jaffe2718.the_matrix.element.entity.vehicle;

import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.network.packet.s2c.play.MachineGunEntitySpawnS2CPacket;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.KeyBindings;
import me.jaffe2718.the_matrix.unit.MathUnit;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
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

    public static final int MAX_BULLET_NUM = 192;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int bulletNum;
    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 10.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 128.0D);
    }

    public MachineGunEntity(EntityType<? extends MachineGunEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.bulletNum = nbt.getInt("BulletNum");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("BulletNum", this.bulletNum);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new MachineGunEntitySpawnS2CPacket(this);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        if (packet instanceof MachineGunEntitySpawnS2CPacket) {
            this.bulletNum = ((MachineGunEntitySpawnS2CPacket) packet).getBulletNum();
        } else {
            this.bulletNum = 0;
        }
    }

    @Override
    protected ActionResult interactMob(@NotNull PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).isOf(ItemRegistry.BULLET)) {
            if (this.bulletNum < MAX_BULLET_NUM) {
                this.bulletNum++;
                player.getStackInHand(hand).decrement(1);
                return ActionResult.success(this.getWorld().isClient);
            } else {
                return ActionResult.PASS;
            }
        } else if (!this.hasPassengers()) {
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
            if (this.getControllingPassenger() instanceof PlayerEntity passenger) {
                this.prevYaw = getYaw();
                this.prevPitch = getPitch();
                setYaw(passenger.getYaw());
                setPitch(passenger.getPitch() * 0.5f);
                setRotation(getYaw(), getPitch());
                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                super.travel(movementInput);
            }
        }
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

    @Override
    public void tick() {
        if (this.age % 4 == 0) {
            if (this.isShooting() && this.getControllingPassenger() instanceof PlayerEntity player) {
                this.bulletNum--;
                if (!this.getWorld().isClient()) {
                    BulletEntity.shoot(this,
                            player.getEyePos().add(MathUnit.getRotationVector(player.getPitch(), player.getHeadYaw())),
                            player.getRotationVector().multiply(10));
                    this.playSound(SoundEventRegistry.MACHINE_GUN_SHOOT, 1.0F, 1.0F);
                    this.playSound(SoundEventRegistry.BULLET_SHELL_HITTING_THE_GROUND, 1.0F, 1.0F);
                }
                if (this.getFirstPassenger() instanceof PlayerEntity user) {
                    user.setYaw(user.getYaw() + this.random.nextFloat() * 3 - 1.5F);
                    user.setPitch(user.getPitch() - this.random.nextFloat() * 3);
                }
            } else if (this.getFirstPassenger() instanceof PlayerEntity user) {
                if (!KeyBindings.FIRE_SAFETY_CATCH.isPressed()) {
                    user.sendMessage(Text
                            .translatable("message.the_matrix.press").append(" ")
                            .append(Text.translatable(KeyBindings.FIRE_SAFETY_CATCH.getBoundKeyTranslationKey())).append(" ")
                            .append(Text.translatable("message.the_matrix.to_open_the_safety_catch")), true);
                } else {
                    user.sendMessage(Text
                            .translatable("message.the_matrix.press").append(" ")
                            .append(Text.translatable(MinecraftClient.getInstance().options.attackKey.getBoundKeyTranslationKey())).append(" ")
                            .append(Text.translatable("message.the_matrix.to_shoot")), true);
                }
            }

        }
        this.fallDistance = 0;
        this.setAir(this.getMaxAir());
        super.tick();
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
        return this.bulletNum > 0 && this.getFirstPassenger() instanceof PlayerEntity &&
                MinecraftClient.getInstance().options.attackKey.isPressed()
                && KeyBindings.FIRE_SAFETY_CATCH.isPressed() || this.isAIShooting();
    }

    private boolean isAIShooting() {   // TODO: AI shooting
        return false;
    }

    public int getBulletNum() {
        return bulletNum;
    }
}
