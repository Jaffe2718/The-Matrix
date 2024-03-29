package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public abstract class ParticleRegistry {
    public static final DefaultParticleType BULLET_SHELL = FabricParticleTypes.simple();
    public static final DefaultParticleType HEAL = FabricParticleTypes.simple();
    public static final DefaultParticleType MOBILE_PHONE_01 = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, TheMatrix.id("bullet_shell"), BULLET_SHELL);
        Registry.register(Registries.PARTICLE_TYPE, TheMatrix.id("heal"), HEAL);
        Registry.register(Registries.PARTICLE_TYPE, TheMatrix.id("mobile_phone_01"), MOBILE_PHONE_01);
    }
}
