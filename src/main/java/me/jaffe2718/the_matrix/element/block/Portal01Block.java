package me.jaffe2718.the_matrix.element.block;

import me.jaffe2718.the_matrix.unit.Dimensions;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class Portal01Block extends Block {
    public Portal01Block(Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.getServer() != null && entity.canUsePortals() && entity instanceof PlayerEntity) {
            RegistryKey<World> currentWorld = world.getRegistryKey();
            if (Dimensions.getDimensionFlag(currentWorld) == Dimensions.DimensionFlags.REAL_WORLD) {
                ServerWorld virtualEnd = world.getServer().getWorld(Dimensions.VIRTUAL_END);
                if (virtualEnd != null && world instanceof ServerWorld realWorld) {
                    Vec3d teleportVec = Dimensions.getWorldTeleportPos(virtualEnd, realWorld, entity.getBlockPos());
                    BlockPos teleportPos = new BlockPos((int) Math.round(teleportVec.getX()), (int) teleportVec.getY(), (int) Math.round(teleportVec.getZ()));
                    for (int dx = -2; dx <= 2; dx++) {
                        for (int dz = -2; dz <= 2; dz++) {
                            virtualEnd.setBlockState(teleportPos.add(dx, -1, dz), Blocks.QUARTZ_BLOCK.getDefaultState());
                            virtualEnd.breakBlock(teleportPos.add(dx, 0, dz), false);
                            virtualEnd.breakBlock(teleportPos.add(dx, 1, dz), false);
                        }
                    }
                    FabricDimensions.teleport(entity, virtualEnd, new TeleportTarget(teleportPos.toCenterPos(), Vec3d.ZERO, 0, 0));
                }
            } else if (Dimensions.getDimensionFlag(currentWorld) == Dimensions.DimensionFlags.VIRTUAL_END) {
                // teleport to spawn point of minecraft:overworld
                ServerWorld overworld = world.getServer().getWorld(World.OVERWORLD);
                if (overworld != null) {
                    BlockPos spawnPos = overworld.getSpawnPos();
                    FabricDimensions.teleport(entity, overworld, new TeleportTarget(spawnPos.toCenterPos(), Vec3d.ZERO, 0, 0));
                }
            }
        }
    }
}
