package me.jaffe2718.the_matrix.element.block;

import com.mojang.serialization.MapCodec;
import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.block.entity.EngineCoreBlockEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.SpaceshipEntity;
import me.jaffe2718.the_matrix.unit.BlockRegistry;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

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

    /**
     * Check if the player is building the APU or the Spaceship.<br>
     * @see ArmoredPersonnelUnitEntity
     * */
    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient || !player.getStackInHand(hand).isOf(ItemRegistry.SPANNER)) {
            return super.onUse(state, world, pos, player, hand, hit);
        } else if (world instanceof ServerWorld serverWorld){
            int facing = checkSpaceship(serverWorld, pos);
            if (checkApuFaceX(serverWorld, pos) || checkApuFaceZ(serverWorld, pos)) {   // try to summon APU
                clearApu5x5x5(serverWorld, pos);
                ArmoredPersonnelUnitEntity apu = EntityRegistry.ARMORED_PERSONAL_UNIT.create(serverWorld);
                if (apu != null) {
                    apu.setPosition(pos.down(3).toCenterPos());
                    serverWorld.spawnEntity(apu);
                    apu.setYaw(player.getYaw());
                    TheMatrix.LOGGER.info("APU built at " + pos.down(4));
                }
                player.getStackInHand(hand).damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
                player.getItemCooldownManager().set(ItemRegistry.SPANNER, 10);
                return ActionResult.SUCCESS;
            } else if (facing != -1) {   // try to summon spaceship
                clearSpaceship5x3x5(serverWorld, pos);
                SpaceshipEntity spaceship = EntityRegistry.SPACESHIP.create(serverWorld);
                if (spaceship != null) {
                    spaceship.setPosition(pos.down(1).toCenterPos());
                    spaceship.setYaw(player.getYaw() + 90 * facing);
                    serverWorld.spawnEntity(spaceship);
                    TheMatrix.LOGGER.info("Spaceship built at " + pos.down(1));
                }
                player.getStackInHand(hand).damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
                player.getItemCooldownManager().set(ItemRegistry.SPANNER, 10);
                player.startRiding(spaceship);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    private static boolean checkApuFaceX(@NotNull ServerWorld world, @NotNull BlockPos corePos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 1; y >= -3; y--) {
                for (int z = -2; z <= 2; z++) {
                    int type = blockIngredientType(world.getBlockState(corePos.add(x, y, z)));
                    if (type == -1) {
                        return false;
                    }
                    if (matrixApu5x5x5[z + 2][1 - y][x + 2] != type) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean checkApuFaceZ(@NotNull ServerWorld world, @NotNull BlockPos headPos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 1; y >= -3; y--) {
                for (int z = -2; z <= 2; z++) {
                    int type = blockIngredientType(world.getBlockState(headPos.add(x, y, z)));
                    if (type == -1) {
                        return false;
                    }
                    if (matrixApu5x5x5[x + 2][1 - y][z + 2] != type) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void clearApu5x5x5(@NotNull ServerWorld world, @NotNull BlockPos corePos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 1; y >= -3; y--) {
                for (int z = -2; z <= 2; z++) {
                    world.breakBlock(corePos.add(x, y, z), false);
                }
            }
        }
    }

    /**
     * Check if the player is building the Spaceship.<br>
     * @see SpaceshipEntity
     * */
    private static int checkSpaceship(@NotNull ServerWorld world, @NotNull BlockPos corePos) {
        boolean n = true, s = true, e = true, w = true;    // n s e w may not same as the direction in minecraft
        for (int y = -1; y <= 1; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    int type = blockIngredientType(world.getBlockState(corePos.add(x, y, z)));
                    if (type != matrixSpaceship5x3x5[y + 1][z + 2][x + 2]) {
                        n = false;
                    }
                    if (type != matrixSpaceship5x3x5[y + 1][2 - z][x + 2]) {
                        s = false;
                    }
                    if (type != matrixSpaceship5x3x5[y + 1][x + 2][z + 2]) {
                        e = false;
                    }
                    if (type != matrixSpaceship5x3x5[y + 1][2 - x][2 - z]) {
                        w = false;
                    }
                }
            }
        }
        if (n) {
            return 0;   // rotate 0 * 90 degrees
        } else if (e) {
            return 1;   // rotate 1 * 90 degrees
        } else if (s) {
            return 2;   // rotate 2 * 90 degrees
        } else if (w) {
            return 3;   // rotate 3 * 90 degrees
        }
        return -1;      // invalid
    }

    private static void clearSpaceship5x3x5(@NotNull ServerWorld world, @NotNull BlockPos corePos) {
        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    world.breakBlock(corePos.add(x, y, z), false);
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
    private static int blockIngredientType(@NotNull BlockState state) {
        if (state.isAir()) {
            return 0;
        } else if (state.isOf(BlockRegistry.MACHINE_BLOCK)) {
            return 1;
        } else if (state.isOf(BlockRegistry.ENGINE_CORE)) {
            return 2;
        } else if (state.isOf(BlockRegistry.PLASMA_LAMP)) {
            return 3;
        } else {
            return -1;
        }
    }

    /**
     * Used to determine the type of the Block to build the APU in 5^3 space.<br>
     * int[x][y][z] or int[z][y][x] is the type of the block at (x, y, z) in the 5^3 space.<br>
     * */
    private static final int[][][] matrixApu5x5x5 = {
            {       // X(Z) = -2
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {       // X(Z) = -1
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {       // X(Z) = 0
                    {0, 0, 1, 0, 0},
                    {1, 1, 2, 1, 1},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 1, 0, 1, 0}
            },
            {       // X(Z) = 1
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {       // X(Z) = 2
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            }
    };

    private static final int[][][] matrixSpaceship5x3x5 = {
            {       // Y = -1
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            },
            {       // Y = 0
                    {0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 0},
                    {1, 3, 2, 3, 1},
                    {1, 0, 1, 0, 1},
                    {0, 0, 1, 0, 0}
            },
            {       // Y = +1
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 1, 0, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0}
            },
    };
}
