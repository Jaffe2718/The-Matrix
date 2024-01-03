package me.jaffe2718.the_matrix.unit;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class EventHandler {

    public static HashMap<UUID, InventoryManager> playerInventoryMap = new HashMap<>();

    private static void onPlayerChangeWorld(ServerPlayerEntity player, @NotNull ServerWorld origin, @NotNull ServerWorld destination) {
        String originKey = origin.getRegistryKey().getValue().getPath();
        String destinationKey = destination.getRegistryKey().getValue().getPath();
        if (origin.getRegistryKey().getValue().getNamespace().equals(MOD_ID)
            || origin.getRegistryKey().getValue().getNamespace().equals("minecraft")) {
            playerInventoryMap.get(player.getUuid()).recordInventory(originKey, player.getInventory());
        }
        if (destination.getRegistryKey().getValue().getNamespace().equals(MOD_ID)
            || destination.getRegistryKey().getValue().getNamespace().equals("minecraft")) {
            playerInventoryMap.get(player.getUuid()).restoreInventory(destinationKey);
        }
    }

    public static void register() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(EventHandler::onPlayerChangeWorld);
    }
}
