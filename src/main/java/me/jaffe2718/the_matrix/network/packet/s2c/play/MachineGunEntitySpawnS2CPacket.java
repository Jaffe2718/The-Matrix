package me.jaffe2718.the_matrix.network.packet.s2c.play;

import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

public class MachineGunEntitySpawnS2CPacket extends EntitySpawnS2CPacket {
    private final int bulletNum;
    public MachineGunEntitySpawnS2CPacket(MachineGunEntity entity) {
        super(entity);
        bulletNum = entity.getBulletNum();
    }

    public int getBulletNum() {
        return bulletNum;
    }
}
