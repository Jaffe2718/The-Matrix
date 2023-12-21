package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.network.packet.s2c.play.APUEntitySpawnS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class PacketRegistry {
    public static final PacketType<APUEntitySpawnS2CPacket> APU_ENTITY_SPAWN_S2C_PACKET = PacketType.create(new Identifier(MOD_ID, "apu_entity_spawn_s2c_packet"), APUEntitySpawnS2CPacket::new);

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(APU_ENTITY_SPAWN_S2C_PACKET, ((packet, player, responseSender) -> {
        }));
    }

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(APU_ENTITY_SPAWN_S2C_PACKET, ((packet, player, responseSender) -> {
        }));
    }
}
