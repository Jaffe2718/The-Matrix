package me.jaffe2718.the_matrix.element.block;

import me.jaffe2718.the_matrix.element.block.entity.PlasmaEmitterBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PlasmaEmitterBlock extends BlockWithEntity {
    public PlasmaEmitterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlasmaEmitterBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        return validateTicker(type, BlockRegistry.PLASMA_EMITTER_BLOCK_ENTITY, PlasmaEmitterBlockEntity::tick);
//    }
}
