package me.jaffe2718.the_matrix.unit;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

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

    /**
     * Called at the end of each world tick<br>
     * Tasks:<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;1. Spawn Digger Robot (boss) and Robot Sentinel (minions) at Zion on Friday, 6:00 PM<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date is the same as <code>time query day</code> in vanilla<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Time is the same as <code>time query daytime</code> in vanilla<br>
     * */
    private static void onEndWorldTick(@NotNull ServerWorld serverWorld) {
        if (serverWorld.getRegistryKey() == Dimensions.ZION && !serverWorld.getPlayers().isEmpty()) {
            // long time = serverWorld.getTime();
            long timeOfDay = serverWorld.getTimeOfDay();
            int dayTime = (int) (timeOfDay % 24000L);
            int date = (int) (timeOfDay / 24000L % 2147483647L);
            if (date % 7 == 5 && dayTime == 18000) {   // Friday, 6:00 PM
                // check if Digger Robot (boss) exists
                AtomicBoolean diggerRobotExist = new AtomicBoolean(false);
                serverWorld.getEntitiesByType(EntityRegistry.DIGGER_ROBOT, entity -> true).forEach(entity -> diggerRobotExist.set(entity.isAlive()));
                if (!diggerRobotExist.get()) {    // Digger Robot does not exist
                    @Nullable BlockPos zionPos = serverWorld.locateStructure(StructureTags.ZION_STRUCTURES, BlockPos.ORIGIN, 256, false);
                    if (zionPos != null) {
                        int diffId = serverWorld.getDifficulty().getId();
                        if (diffId > 0) {     // not peaceful
                            EntityRegistry.DIGGER_ROBOT.spawn(serverWorld, zionPos.withY(64), SpawnReason.EVENT);
                            for (int i = 0; i < diffId + 2; i++) {  // easy: 3, normal: 4, hard: 5
                                EntityRegistry.ROBOT_SENTINEL.spawn(serverWorld, zionPos.withY(64), SpawnReason.EVENT);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void register() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(EventHandler::onPlayerChangeWorld);
        ServerTickEvents.END_WORLD_TICK.register(EventHandler::onEndWorldTick);
    }


}
