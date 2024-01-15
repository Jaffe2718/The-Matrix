package me.jaffe2718.the_matrix.unit.gui.screen;

import me.jaffe2718.the_matrix.unit.Dimensions;
import me.jaffe2718.the_matrix.unit.ScreenRegistry;
import me.jaffe2718.the_matrix.unit.gui.screen.slot.ButtonSlot;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;
import org.jetbrains.annotations.NotNull;

public class TeleporterScreenHandler extends ScreenHandler {
    private final PlayerEntity player;

    public TeleporterScreenHandler(int syncId, @NotNull PlayerInventory playerInventory) {
        super(ScreenRegistry.TELEPORTER_SCREEN_HANDLER, syncId);
        this.player = playerInventory.player;
        if (player instanceof ServerPlayerEntity serverPlayerEntity) {    // server side
            this.addSlot(new ButtonSlot(() -> {
                ServerWorld zion = serverPlayerEntity.getServerWorld();
                ServerWorld robotWorld = zion.getServer().getWorld(Dimensions.ROBOT_WORLD);
                if (robotWorld != null) {
                    FabricDimensions.teleport(
                            serverPlayerEntity,
                            robotWorld,
                            new TeleportTarget(
                                    Dimensions.getWorldTeleportPos(robotWorld, zion, serverPlayerEntity.getBlockPos()),
                                    serverPlayerEntity.getVelocity(),
                                    serverPlayerEntity.getYaw(),
                                    serverPlayerEntity.getPitch())
                    );
                }
            }, 131, 45));
            this.addSlot(new ButtonSlot(() -> {
                ServerWorld zion = serverPlayerEntity.getServerWorld();
                ServerWorld virtualWorld = zion.getServer().getWorld(Dimensions.VIRTUAL_WORLD);
                if (virtualWorld != null) {
                    FabricDimensions.teleport(
                            serverPlayerEntity,
                            virtualWorld,
                            new TeleportTarget(
                                    Dimensions.getWorldTeleportPos(virtualWorld, zion, serverPlayerEntity.getBlockPos()),
                                    serverPlayerEntity.getVelocity(),
                                    serverPlayerEntity.getYaw(),
                                    serverPlayerEntity.getPitch())
                    );
                }
            }, 131, 115));
        } else {                                                          // client side
            this.addSlot(new ButtonSlot(null, 131, 45));
            this.addSlot(new ButtonSlot(null, 131, 115));
        }
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        Slot slot = this.slots.get(slotIndex);
        if (slot instanceof ButtonSlot buttonSlot) {
            buttonSlot.runnable.run();
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
