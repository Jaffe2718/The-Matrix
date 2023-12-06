package me.jaffe2718.the_matrix.network.packet.s2c.play;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

/**
 * The spawn packet for {@link ArmoredPersonnelUnitEntity}.
 * @see ArmoredPersonnelUnitEntity#onSpawnPacket(EntitySpawnS2CPacket)
 * @see ArmoredPersonnelUnitEntity#createSpawnPacket()
 * */
public class APUEntitySpawnS2CPacket extends EntitySpawnS2CPacket {
    private final int bulletNum;
    public APUEntitySpawnS2CPacket(ArmoredPersonnelUnitEntity entity) {
        super(entity);
        bulletNum = entity.getBulletNum();
    }

    public int getBulletNum() {
        return bulletNum;
    }
}
