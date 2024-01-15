package me.jaffe2718.the_matrix.unit.gui.screen.slot;

import me.jaffe2718.the_matrix.unit.gui.screen.Game2048ScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

public class RewardSlot extends Slot {
    Game2048ScreenHandler handler;
    public RewardSlot(@NotNull Game2048ScreenHandler handler, int x, int y) {
        super(handler.laptopBlockEntity.rewardInventory, 0, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakePartial(PlayerEntity player) {
        return false;
    }

    /**
     * The player must take all the items in the slot.
     * */
    @Override
    public ItemStack takeStack(int amount) {
        return this.inventory.removeStack(0, this.getStack().getCount());
    }
}
