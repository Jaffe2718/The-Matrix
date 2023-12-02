package me.jaffe2718.the_matrix.unit;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class SoundEventRegistry {
    public static final SoundEvent ROBOT_SENTINEL_AMBIENT = SoundEvent.of(new Identifier(MOD_ID, "robot_sentinel_ambient"));

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "robot_sentinel_ambient"), ROBOT_SENTINEL_AMBIENT);
    }
}
