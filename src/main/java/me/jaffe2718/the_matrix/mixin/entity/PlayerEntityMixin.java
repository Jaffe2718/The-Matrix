package me.jaffe2718.the_matrix.mixin.entity;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import me.jaffe2718.the_matrix.element.item.HackerBootsItem;
import me.jaffe2718.the_matrix.element.item.HackerCloakItem;
import me.jaffe2718.the_matrix.element.item.HackerPantsItem;
import me.jaffe2718.the_matrix.unit.EventHandler;
import me.jaffe2718.the_matrix.unit.InventoryManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
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
        // TODO: unfinished, add new dimension for agent smith boss
        InventoryManager inventoryManager = Objects.requireNonNullElse(
                EventHandler.playerInventoryMap.get(((PlayerEntity) (Object) this).getUuid()),
                new InventoryManager((PlayerEntity) (Object) this));
        inventoryManager.robotWorldInventory.readNbt(nbt.getList("RobotWorldInventory",  NbtElement.COMPOUND_TYPE));
        inventoryManager.vanillaInventory.readNbt(nbt.getList("VanillaInventory",  NbtElement.COMPOUND_TYPE));
        inventoryManager.virtualWorldInventory.readNbt(nbt.getList("VirtualWorldInventory",  NbtElement.COMPOUND_TYPE));
        inventoryManager.zionInventory.readNbt(nbt.getList("ZionInventory",  NbtElement.COMPOUND_TYPE));
        EventHandler.playerInventoryMap.put(((PlayerEntity) (Object) this).getUuid(), inventoryManager);  // update player inventory info
        TheMatrix.LOGGER.info("loading player data");
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbtMixin(@NotNull NbtCompound nbt, CallbackInfo ci) {
        // TODO: unfinished, add new dimension for agent smith boss
        InventoryManager inventoryManager = Objects.requireNonNullElse(
                EventHandler.playerInventoryMap.get(((PlayerEntity) (Object) this).getUuid()),
                new InventoryManager((PlayerEntity) (Object) this));
        nbt.put("RobotWorldInventory", inventoryManager.robotWorldInventory.writeNbt(new NbtList()));
        nbt.put("VanillaInventory", inventoryManager.vanillaInventory.writeNbt(new NbtList()));
        nbt.put("VirtualWorldInventory", inventoryManager.virtualWorldInventory.writeNbt(new NbtList()));
        nbt.put("ZionInventory", inventoryManager.zionInventory.writeNbt(new NbtList()));
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
