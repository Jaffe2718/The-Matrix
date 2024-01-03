package me.jaffe2718.the_matrix.element.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class MobilePhone01Particle extends AnimatedParticle {
    protected MobilePhone01Particle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, -0.4F);
        this.maxAge = 20;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);   // Required
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public MobilePhone01Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new MobilePhone01Particle(world, x, y, z, this.spriteProvider);
        }
    }
}
