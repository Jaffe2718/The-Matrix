package me.jaffe2718.the_matrix.element.particle;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class BulletShellParticle extends AnimatedParticle {

    protected BulletShellParticle(ClientWorld clientWorld, double x, double y, double z, double vX, double vY, double vZ, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, spriteProvider, 1F);
        this.maxAge = 60;
        this.velocityX = vX;
        this.velocityY = vY;
        this.velocityZ = vZ;
        this.alpha = 1.0F;
        this.scale = 0.2F;
        this.collidesWithWorld = true;
        this.setSpriteForAge(spriteProvider); //Required
    }

    public void tick() {
        if(!this.onGround) { //If the particle isn't on the ground
            this.angle = this.prevAngle + 0.4f;       //Slowly turns the particle
        } else {
            this.velocityY *= -0.6D;            //Bounces the particle
            this.velocityX *= 0.6D;
            this.velocityZ *= 0.6D;
        }
        super.tick();
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public BulletShellParticle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double vX, double vY, double vZ) {
            return new BulletShellParticle(clientWorld, x, y, z, vX, vY, vZ, spriteProvider);
        }
    }
}
