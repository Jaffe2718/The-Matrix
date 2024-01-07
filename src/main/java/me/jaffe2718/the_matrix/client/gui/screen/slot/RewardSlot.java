package me.jaffe2718.the_matrix.client.gui.screen.slot;

import me.jaffe2718.the_matrix.client.gui.screen.Game2048ScreenHandler;
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
        return true;  // TODO: set to false after testing
    }

    @Override
    public boolean canTakePartial(PlayerEntity player) {
        return false;
    }

    @Override
    public ItemStack takeStack(int amount) {
        return this.inventory.removeStack(0, this.getStack().getCount());
    }
}
