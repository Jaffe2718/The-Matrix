package me.jaffe2718.the_matrix.element.block.entity;

import me.jaffe2718.the_matrix.unit.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PlasmaEmitterBlockEntity extends BlockEntity {
    public PlasmaEmitterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.PLASMA_EMITTER_BLOCK_ENTITY, pos, state);
    }
}
