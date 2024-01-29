package me.jaffe2718.the_matrix.element.block;

import com.mojang.serialization.MapCodec;
import me.jaffe2718.the_matrix.element.block.entity.EngineCoreBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class EngineCoreBlock extends BlockWithEntity {
    public static final MapCodec<LaptopBlock> CODEC = createCodec(LaptopBlock::new);

    public EngineCoreBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EngineCoreBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
