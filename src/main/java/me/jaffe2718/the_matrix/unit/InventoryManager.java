package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.NotNull;

public class InventoryManager {
    private final PlayerInventory realWorldInventory;
    private final PlayerInventory vanillaInventory;
    private final PlayerInventory virtualEndInventory;
    private final PlayerInventory virtualWorldInventory;

    public InventoryManager(PlayerEntity player) {
        this.realWorldInventory = new PlayerInventory(player);
        this.vanillaInventory = new PlayerInventory(player);
        this.virtualEndInventory = new PlayerInventory(player);
        this.virtualWorldInventory = new PlayerInventory(player);
    }

    public void readNbt(@NotNull NbtCompound nbt) {
        this.realWorldInventory.readNbt(nbt.getList("RealWorldInventory",  NbtElement.COMPOUND_TYPE));
        this.vanillaInventory.readNbt(nbt.getList("VanillaInventory",  NbtElement.COMPOUND_TYPE));
        this.virtualEndInventory.readNbt(nbt.getList("VirtualEndInventory",  NbtElement.COMPOUND_TYPE));
        this.virtualWorldInventory.readNbt(nbt.getList("VirtualWorldInventory",  NbtElement.COMPOUND_TYPE));
    }

    public void writeNbt(@NotNull NbtCompound nbt) {
        nbt.put("RealWorldInventory", this.realWorldInventory.writeNbt(new NbtList()));
        nbt.put("VanillaInventory", this.vanillaInventory.writeNbt(new NbtList()));
        nbt.put("VirtualEndInventory", this.virtualEndInventory.writeNbt(new NbtList()));
        nbt.put("VirtualWorldInventory", this.virtualWorldInventory.writeNbt(new NbtList()));
    }

    public void recordInventory(@NotNull Dimensions.DimensionFlags originFlag, PlayerInventory inventory) {
        switch (originFlag) {
            case REAL_WORLD ->
                this.realWorldInventory.readNbt(inventory.writeNbt(new NbtList()));
            case VIRTUAL_END ->
                    this.virtualEndInventory.readNbt(inventory.writeNbt(new NbtList()));
            case VIRTUAL_WORLD ->
                    this.virtualWorldInventory.readNbt(inventory.writeNbt(new NbtList()));
            default ->  // vanilla
                vanillaInventory.readNbt(inventory.writeNbt(new NbtList()));
        }
        TheMatrix.LOGGER.info("recording player inventory: " + originFlag);
    }

    public void restoreInventory(@NotNull Dimensions.DimensionFlags dimensionFlag) {
        PlayerEntity player = vanillaInventory.player;
        switch (dimensionFlag) {
            case REAL_WORLD ->
                player.getInventory().readNbt(realWorldInventory.writeNbt(new NbtList()));
            case VIRTUAL_END ->
                player.getInventory().readNbt(virtualEndInventory.writeNbt(new NbtList()));
            case VIRTUAL_WORLD ->
                player.getInventory().readNbt(virtualWorldInventory.writeNbt(new NbtList()));
            default ->  // vanilla
                player.getInventory().readNbt(vanillaInventory.writeNbt(new NbtList()));
        }
        TheMatrix.LOGGER.info("restoring player inventory: " + dimensionFlag);
    }
}
