package me.jaffe2718.the_matrix.network.packet.s2c.play;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

import java.util.HashMap;

/**
 * The spawn packet for {@link ZionPeopleEntity}.
 * @see ZionPeopleEntity#onSpawnPacket(EntitySpawnS2CPacket)
 * @see ZionPeopleEntity#createSpawnPacket()
 * */
public class ZionPeopleEntitySpawnS2CPacket extends EntitySpawnS2CPacket {

    public static final HashMap<Integer, String> jobIDMap = new HashMap<>() {{
        // 0 -> "random";
        put(1, "apu_pilot");
        put(2, "carpenter");
        put(3, "farm_keeper");
        put(4, "farmer");
        put(5, "grocer");
        put(6, "infantry");
        put(7, "machinist");
        put(8, "miner");
        put(9, "rifleman");
    }};

    private final int jobID;
    public ZionPeopleEntitySpawnS2CPacket(ZionPeopleEntity entity) {
        super(entity);
        this.jobID = entity.jobId;
    }

    public int getJobID() {
        return jobID;
    }
}
