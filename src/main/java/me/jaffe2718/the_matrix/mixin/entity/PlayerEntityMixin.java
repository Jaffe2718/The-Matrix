package me.jaffe2718.the_matrix.mixin.entity;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.SpaceshipEntity;
import me.jaffe2718.the_matrix.element.item.HackerBootsItem;
import me.jaffe2718.the_matrix.element.item.HackerCloakItem;
import me.jaffe2718.the_matrix.element.item.HackerPantsItem;
import me.jaffe2718.the_matrix.unit.EventHandler;
import me.jaffe2718.the_matrix.unit.InventoryManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    private void readCustomDataFromNbtMixin(@NotNull NbtCompound nbt, CallbackInfo ci) {
        InventoryManager inventoryManager = Objects.requireNonNullElse(
                EventHandler.playerInventoryMap.get(((PlayerEntity) (Object) this).getUuid()),
                new InventoryManager((PlayerEntity) (Object) this));
        inventoryManager.readNbt(nbt);
        EventHandler.playerInventoryMap.put(((PlayerEntity) (Object) this).getUuid(), inventoryManager);  // update player inventory info
        TheMatrix.LOGGER.info("loading player data");
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbtMixin(@NotNull NbtCompound nbt, CallbackInfo ci) {
        InventoryManager inventoryManager = Objects.requireNonNullElse(
                EventHandler.playerInventoryMap.get(((PlayerEntity) (Object) this).getUuid()),
                new InventoryManager((PlayerEntity) (Object) this));
        inventoryManager.writeNbt(nbt);
        EventHandler.playerInventoryMap.put(((PlayerEntity) (Object) this).getUuid(), inventoryManager);  // update player inventory info
        TheMatrix.LOGGER.info("saving player data");
    }

    @Shadow public abstract void incrementStat(Identifier stat);

    /**
     * Invulnerable to AgentEntity with HackerPantsItem
     * */
    @Inject(at = @At("RETURN"), method = "isInvulnerableTo", cancellable = true)
    private void isInvulnerableToMixin(@NotNull DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (source.getAttacker() instanceof AgentEntity &&
                ((PlayerEntity) (Object) this).getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof HackerPantsItem) {
            cir.setReturnValue(true);
        }
        if (((PlayerEntity) (Object) this).getVehicle() instanceof SpaceshipEntity
                && source.getTypeRegistryEntry().getKey().isPresent()) {   // invulnerable when in spaceship
            RegistryKey<DamageType> damageType = source.getTypeRegistryEntry().getKey().get();
            cir.setReturnValue(damageType == DamageTypes.EXPLOSION
                    || damageType == DamageTypes.FALL
                    || damageType == DamageTypes.FALLING_ANVIL
                    || damageType == DamageTypes.FALLING_BLOCK
                    || damageType == DamageTypes.FALLING_STALACTITE
                    || damageType == DamageTypes.FIREBALL
                    || damageType == DamageTypes.FIREWORKS
                    || damageType == DamageTypes.LAVA
                    || damageType == DamageTypes.HOT_FLOOR
                    || damageType == DamageTypes.MOB_ATTACK
                    || damageType == DamageTypes.MOB_PROJECTILE
                    || damageType == DamageTypes.MOB_ATTACK_NO_AGGRO
                    || damageType == DamageTypes.ON_FIRE
                    || damageType == DamageTypes.PLAYER_ATTACK
                    || damageType == DamageTypes.TRIDENT);
        }
    }

    /**
     * Can fly with HackerCloakItem
     * */
    @Inject(at = @At("TAIL"), method = "tickMovement")
    private void tickMovementMixin(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.isSpectator() && MinecraftClient.getInstance().options.jumpKey.isPressed() &&
                player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof HackerCloakItem &&
                !player.checkFallFlying() && !player.isSwimming() && !player.isClimbing() &&
                !player.isUsingRiptide()) {
            Vec3d currentVelocity = player.getVelocity();
            this.incrementStat(Stats.JUMP);
            player.setVelocity(currentVelocity.x, 0.3, currentVelocity.z);
            player.fallDistance = 0;
        }
    }

    /**
     * Fall damage absolutely does not exist with HackerBootsItem
     * */
    @Inject(at = @At("TAIL"), method = "tick")
    private void tickMixin(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof HackerBootsItem &&
                player.fallDistance > 0) {
            player.fallDistance = 0;
        }
    }
}
