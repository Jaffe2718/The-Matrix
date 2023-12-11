package me.jaffe2718.the_matrix.network.packet.s2c.play;

import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

public class ElectromagneticBulletEntitySpawnS2CPacket extends EntitySpawnS2CPacket {

    private final int explosionDuration;   // max = 5, min = 0, destroy when 0
    private final boolean isExploding;
    public ElectromagneticBulletEntitySpawnS2CPacket(ElectromagneticBulletEntity entity) {
        super(entity);
        this.explosionDuration = entity.getExplosionDuration();
        this.isExploding = entity.isExploding();
    }

    public int getExplosionDuration() {
        return this.explosionDuration;
    }

    public boolean isExploding() {
        return this.isExploding;
    }

}
