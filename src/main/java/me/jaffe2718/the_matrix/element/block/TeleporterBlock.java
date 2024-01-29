package me.jaffe2718.the_matrix.element.block;

import com.mojang.serialization.MapCodec;
import me.jaffe2718.the_matrix.element.block.entity.TeleporterBlockEntity;
import me.jaffe2718.the_matrix.unit.Dimensions;
import me.jaffe2718.the_matrix.unit.States;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class TeleporterBlock extends BlockWithEntity {
    public static final MapCodec<TeleporterBlock> CODEC = createCodec(TeleporterBlock::new);

    public TeleporterBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<TeleporterBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TeleporterBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world instanceof ServerWorld serverWorld && player.canUsePortals()) {
            RegistryKey<World> currentDimension = serverWorld.getRegistryKey();
            if (currentDimension == Dimensions.ZION) {  // show teleport screen
                player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            } else if (currentDimension == Dimensions.ROBOT_WORLD) {  // transfer to zion
                ServerWorld zionWorld = serverWorld.getServer().getWorld(Dimensions.ZION);
                if (zionWorld != null) {
                    FabricDimensions.teleport(player, zionWorld, new TeleportTarget(Dimensions.getZionSpawnPos(zionWorld), player.getVelocity(), player.getYaw(), player.getPitch()));
                }
            }   // else do nothing
            player.incrementStat(States.INTERACT_WITH_TELEPORTER);
            return ActionResult.CONSUME;
        } else {
            return ActionResult.SUCCESS;
        }
    }
}
