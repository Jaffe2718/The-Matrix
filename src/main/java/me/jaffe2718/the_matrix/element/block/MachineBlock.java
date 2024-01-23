package me.jaffe2718.the_matrix.element.block;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.unit.BlockRegistry;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineBlock extends Block {

    public MachineBlock(Settings settings) {
        super(settings);
    }

    /**
     * Check if the player is building the APU.<br>
     * @see ArmoredPersonnelUnitEntity
     * */
    @Override
    public void onPlaced(@NotNull World world, BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world instanceof ServerWorld serverWorld
                && serverWorld.getBlockState(pos.down()).isOf(BlockRegistry.PLASMA_LAMP)
                && placer instanceof PlayerEntity) {
            if (checkFaceX(serverWorld, pos) || checkFaceZ(serverWorld, pos)) {
                clear5x5x5(serverWorld, pos);
                ArmoredPersonnelUnitEntity apu = EntityRegistry.ARMORED_PERSONAL_UNIT.create(serverWorld);
                if (apu != null) {
                    apu.setPosition(pos.down(4).toCenterPos());
                    serverWorld.spawnEntity(apu);
                    apu.setYaw(placer.getYaw());
                    TheMatrix.LOGGER.info("APU built at " + pos.down(4));
                }
            }
        }
    }

    private static boolean checkFaceX(@NotNull ServerWorld world, @NotNull BlockPos headPos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y >= -4; y--) {
                for (int z = -2; z <= 2; z++) {
                    int type = apuIngredientType(world.getBlockState(headPos.add(x, y, z)));
                    if (type == -1) {
                        return false;
                    }
                    if (matrix5x5x5[z + 2][-y][x + 2] != type) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean checkFaceZ(@NotNull ServerWorld world, @NotNull BlockPos headPos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y >= -4; y--) {
                for (int z = -2; z <= 2; z++) {
                    int type = apuIngredientType(world.getBlockState(headPos.add(x, y, z)));
                    if (type == -1) {
                        return false;
                    }
                    if (matrix5x5x5[x + 2][-y][z + 2] != type) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void clear5x5x5(@NotNull ServerWorld world, @NotNull BlockPos headPos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y >= -4; y--) {
                for (int z = -2; z <= 2; z++) {
                    world.breakBlock(headPos.add(x, y, z), false);
                }
            }
        }
    }

    /**
     * Used to determine the type of the ingredient in the APU, on building the APU.<br>
     * @param state the block state
     * @return 0: air<br>
     *         1: machine block<br>
     *         2: plasma lamp<br>
     *         -1: invalid block<br>
     * */
    private static int apuIngredientType(@NotNull BlockState state) {
        if (state.isAir()) {
            return 0;
        } else if (state.isOf(BlockRegistry.MACHINE_BLOCK)) {
            return 1;
        } else if (state.isOf(BlockRegistry.PLASMA_LAMP)) {
            return 2;
        } else {
            return -1;
        }
    }

    /**
     * Used to determine the type of the Block to build the APU in 5^3 space.<br>
     * */
    private static final int[][][] matrix5x5x5 = {
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 1, 0, 0},
                    {1, 1, 2, 1, 1},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 0, 1, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
    };
}
