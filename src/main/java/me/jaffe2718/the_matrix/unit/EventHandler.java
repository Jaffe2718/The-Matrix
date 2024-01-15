package me.jaffe2718.the_matrix.unit;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public abstract class EventHandler {

    public static HashMap<UUID, InventoryManager> playerInventoryMap = new HashMap<>();

    /**
     * Called when the player changes world<br>
     * */
    private static void onPlayerChangeWorld(@NotNull ServerPlayerEntity player, @NotNull ServerWorld origin, @NotNull ServerWorld destination) {
        Dimensions.DimensionFlags originFlag = Dimensions.getDimensionFlag(origin.getRegistryKey());
        playerInventoryMap.get(player.getUuid()).recordInventory(originFlag, player.getInventory());
        Dimensions.DimensionFlags destinationFlag = Dimensions.getDimensionFlag(destination.getRegistryKey());
        playerInventoryMap.get(player.getUuid()).restoreInventory(destinationFlag);
    }

    public static void register() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(EventHandler::onPlayerChangeWorld);
    }
}
