package me.jaffe2718.the_matrix.unit;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class Dimensions {
    public static final RegistryKey<World> ROBOT_WORLD = RegistryKey.of(RegistryKeys.WORLD, new Identifier(MOD_ID, "robot_world"));
    public static final RegistryKey<World> VIRTUAL_WORLD = RegistryKey.of(RegistryKeys.WORLD, new Identifier(MOD_ID, "virtual_world"));
    public static final RegistryKey<World> ZION = RegistryKey.of(RegistryKeys.WORLD, new Identifier(MOD_ID, "zion"));
}
