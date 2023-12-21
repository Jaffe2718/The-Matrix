package me.jaffe2718.the_matrix.network.packet.s2c.play;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

/**
 * The spawn packet for {@link ArmoredPersonnelUnitEntity}.
 * @see ArmoredPersonnelUnitEntity#onSpawnPacket(EntitySpawnS2CPacket)
 * @see ArmoredPersonnelUnitEntity#createSpawnPacket()
 * */
public class APUEntitySpawnS2CPacket extends EntitySpawnS2CPacket implements FabricPacket {
    private final int bulletNum;
    public APUEntitySpawnS2CPacket(ArmoredPersonnelUnitEntity entity) {
        super(entity);
        bulletNum = entity.getBulletNum();
    }

    public APUEntitySpawnS2CPacket(PacketByteBuf buf) {
        super(buf);
        bulletNum = buf.readInt();
        System.out.println("Reading packet");
    }

    @Override
    public void write(PacketByteBuf buf) {
        System.out.println("Writing packet");
        super.write(buf);
        buf.writeInt(bulletNum);
    }

    public int getBulletNum() {
        return bulletNum;
    }

    @Override
    public PacketType<?> getType() {
        return null;
    }
}
