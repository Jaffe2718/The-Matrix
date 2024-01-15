package me.jaffe2718.the_matrix.element.block.entity;

import me.jaffe2718.the_matrix.unit.BlockRegistry;
import me.jaffe2718.the_matrix.unit.gui.screen.TeleporterScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TeleporterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public TeleporterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.TELEPORTER_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return this.getCachedState().getBlock().getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TeleporterScreenHandler(syncId, playerInventory);
    }
}
