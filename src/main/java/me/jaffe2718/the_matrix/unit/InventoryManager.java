package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.NotNull;

public class InventoryManager {
    public PlayerInventory robotWorldInventory;
    public PlayerInventory vanillaInventory;
    public PlayerInventory virtualWorldInventory;
    public PlayerInventory zionInventory;

    public InventoryManager(PlayerEntity player) {
        robotWorldInventory = new PlayerInventory(player);
        vanillaInventory = new PlayerInventory(player);
        virtualWorldInventory = new PlayerInventory(player);
        zionInventory = new PlayerInventory(player);
    }

    public void recordInventory(@NotNull String originKey, PlayerInventory inventory) {
        switch (originKey) {
            case "robot_world" ->
                robotWorldInventory.readNbt(inventory.writeNbt(new NbtList()));
            case "virtual_world" ->
                virtualWorldInventory.readNbt(inventory.writeNbt(new NbtList()));
            case "zion" ->
                zionInventory.readNbt(inventory.writeNbt(new NbtList()));
            case "overworld", "the_nether", "the_end" ->
                vanillaInventory.readNbt(inventory.writeNbt(new NbtList()));
        }
        TheMatrix.LOGGER.info("recording player inventory: " + originKey);
    }

    public void restoreInventory(@NotNull String destinationKey) {
        switch (destinationKey) {
            case "robot_world" ->
                robotWorldInventory.player.getInventory().readNbt(robotWorldInventory.writeNbt(new NbtList()));
            case "virtual_world" ->
                virtualWorldInventory.player.getInventory().readNbt(virtualWorldInventory.writeNbt(new NbtList()));
            case "zion" ->
                zionInventory.player.getInventory().readNbt(zionInventory.writeNbt(new NbtList()));
            case "overworld", "the_end", "the_nether" ->
                vanillaInventory.player.getInventory().readNbt(vanillaInventory.writeNbt(new NbtList()));
        }
        TheMatrix.LOGGER.info("restoring player inventory: " + destinationKey);
    }
}
