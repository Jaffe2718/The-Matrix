package me.jaffe2718.the_matrix.unit;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class ParticleRegistry {
    public static final DefaultParticleType HEAL = FabricParticleTypes.simple();
    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "heal"), HEAL);
    }
}
