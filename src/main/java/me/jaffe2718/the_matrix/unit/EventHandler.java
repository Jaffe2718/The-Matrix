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

    /**
     * Called when the player changes world<br>
     * TODO adjust inventory management:
     * TODO 1. add new dimension for agent smith boss (virtual_end)
     * TODO 2. vanilla -> zion: record inventory, remove all items except coins, restore in zion
     * TODO 3. virtual -> zion: record inventory, remove all items except coins, restore in zion
     * TODO 4. virtual_end -> zion: record inventory, remove all items except coins, restore in zion
     * TODO 5. zion -> virtual: record inventory, remove all items except coins, restore in virtual
     * TODO 6. zion -> virtual_end: record inventory, remove all items except coins, restore in virtual_end
     * */
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
