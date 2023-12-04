package me.jaffe2718.the_matrix.unit;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class SoundEventRegistry {

    public static final SoundEvent ROBOT_SENTINEL_DEATH = SoundEvent.of(new Identifier(MOD_ID, "robot_sentinel_death"));
    public static final SoundEvent ROBOT_SENTINEL_HURT = SoundEvent.of(new Identifier(MOD_ID, "robot_sentinel_hurt"));
    public static final SoundEvent ROBOT_SENTINEL_RADAR_DETECTION = SoundEvent.of(new Identifier(MOD_ID, "robot_sentinel_radar_detection"));

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "robot_sentinel_death"), ROBOT_SENTINEL_DEATH);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "robot_sentinel_hurt"), ROBOT_SENTINEL_HURT);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "robot_sentinel_radar_detection"), ROBOT_SENTINEL_RADAR_DETECTION);
    }
}
