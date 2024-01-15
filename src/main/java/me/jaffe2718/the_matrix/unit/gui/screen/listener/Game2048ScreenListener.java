package me.jaffe2718.the_matrix.unit.gui.screen.listener;

import me.jaffe2718.the_matrix.unit.gui.screen.Game2048ScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import org.jetbrains.annotations.NotNull;

public class Game2048ScreenListener implements ScreenHandlerListener {
    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, @NotNull ItemStack stack) {
        if (slotId == 36 && stack.isEmpty() && handler instanceof Game2048ScreenHandler game2048ScreenHandler) {
            game2048ScreenHandler.laptopBlockEntity.getMatrix2048().reset();
            game2048ScreenHandler.updateInventories();
            game2048ScreenHandler.laptopBlockEntity.markDirty();
        }
        // sync server to client
        // handler.updateToClient();
    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
    }
}
