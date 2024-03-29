package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public interface Dimensions {
    RegistryKey<World> ROBOT_WORLD = RegistryKey.of(RegistryKeys.WORLD, TheMatrix.id("robot_world"));
    RegistryKey<World> VIRTUAL_WORLD = RegistryKey.of(RegistryKeys.WORLD, TheMatrix.id("virtual_world"));
    RegistryKey<World> VIRTUAL_END = RegistryKey.of(RegistryKeys.WORLD, TheMatrix.id("virtual_end"));
    RegistryKey<World> ZION = RegistryKey.of(RegistryKeys.WORLD, TheMatrix.id("zion"));

    /**
     * @param targetWorld the world to teleport to, can be {@link #ROBOT_WORLD}, {@link #VIRTUAL_WORLD}
     * @param currentWorld the zion world, {@link #ZION}
     * @param zionPos the position to teleport from
     * @return the position to teleport to
     */
    @Contract("_, _, _ -> new")
    static @NotNull Vec3d getWorldTeleportPos(@NotNull ServerWorld targetWorld, @NotNull ServerWorld currentWorld, @NotNull BlockPos zionPos) {
        // get the coordinate scale between the two worlds
        double coordinateScale = targetWorld.getDimension().coordinateScale() / currentWorld.getDimension().coordinateScale();
        // get the scaled position of the zion spawn position
        BlockPos scaledPos = new BlockPos((int) Math.round(zionPos.getX() * coordinateScale), zionPos.getY(), (int) Math.round(zionPos.getZ() * coordinateScale));
        int dy = -2;
        while (dy != 0 && MathUnit.isBetween(scaledPos.getX(), targetWorld.getTopY() + 1, targetWorld.getTopY())) {
            dy = checkSpawnPos(targetWorld, scaledPos);
            scaledPos = scaledPos.add(0, dy, 0);
        }
        return new Vec3d(scaledPos.getX(), scaledPos.getY(), scaledPos.getZ());
    }

    @Contract("_ -> new")
    static @NotNull Vec3d getZionSpawnPos(ServerWorld zionWorld) {
        BlockPos pos = BlockPos.ORIGIN;
        int dy = -2;
        while (dy != 0) {
            dy = checkSpawnPos(zionWorld, pos);
            pos = pos.add(0, dy, 0);
        }
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    private static int checkSpawnPos(@NotNull ServerWorld serverWorld, BlockPos pos) {
        if (!serverWorld.getBlockState(pos).isAir()) {              // if standing inside a block, move up 2 blocks
            return +2;
        } else if (!serverWorld.getBlockState(pos.up()).isAir()) {  // if head is inside a block, move down 1 block
            return -1;
        } else if (serverWorld.getBlockState(pos.down()).isAir()) { // if feet is in the air, move down 1 block
            return -1;
        } else{                                                   // fine
            return 0;
        }
    }

    static DimensionFlags getDimensionFlag(@NotNull RegistryKey<World> registryKey) {
        switch (registryKey.getValue().toString()) {
            case MOD_ID + ":robot_world", MOD_ID + ":zion" -> {
                return DimensionFlags.REAL_WORLD;
            }
            case MOD_ID + ":virtual_end" -> {
                return DimensionFlags.VIRTUAL_END;
            }
            case MOD_ID + ":virtual_world" -> {
                return DimensionFlags.VIRTUAL_WORLD;
            }
            default -> {
                return DimensionFlags.VANILLA;
            }
        }
    }

    enum DimensionFlags {
        REAL_WORLD,        // zion + robot_world
        VIRTUAL_WORLD,     // virtual_world
        VIRTUAL_END,       // virtual_end
        VANILLA            // overworld + the_nether + the_end + <other mods' dimensions>
    }
}
