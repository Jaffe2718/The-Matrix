package me.jaffe2718.the_matrix.client.gui.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;


/**
 * This slot is designed for buttons, not used for storing the items
 * It is reasonable to set the {@link #runnable} to {@code null} if you don't want to do anything when the button is clicked
 * */
public class ButtonSlot extends Slot {
    public Runnable runnable;

    public ButtonSlot(@Nullable Runnable runnable, int x, int y) {
        super(new SimpleInventory(1), 0, x, y);
        this.runnable = runnable == null ? () -> {} : runnable;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return false;
    }
}
