package me.jaffe2718.the_matrix.client.gui.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

/**
 * This slot is used to show the matrix, not used for storing the items
 * @see me.jaffe2718.the_matrix.client.gui.screen.Game2048ScreenHandler#updateInventories()
 * */
public class MatrixSlot extends Slot {
    public MatrixSlot(Inventory inventory, int index) {
        super(inventory, index, -64, -64);   // make it invisible
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public boolean canTakePartial(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }
}
