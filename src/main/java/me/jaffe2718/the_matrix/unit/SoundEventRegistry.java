package me.jaffe2718.the_matrix.unit;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class SoundEventRegistry {

    public static final SoundEvent ARMORED_PERSONNEL_UNIT_HURT = SoundEvent.of(new Identifier(MOD_ID, "armored_personnel_unit_hurt"));
    public static final SoundEvent ARMORED_PERSONNEL_UNIT_LAND = SoundEvent.of(new Identifier(MOD_ID, "armored_personnel_unit_land"));
    public static final SoundEvent ARMORED_PERSONNEL_UNIT_SHOOT = SoundEvent.of(new Identifier(MOD_ID, "armored_personnel_unit_shoot"));
    public static final SoundEvent ARMORED_PERSONNEL_UNIT_STEP = SoundEvent.of(new Identifier(MOD_ID, "armored_personnel_unit_step"));
    public static final SoundEvent BULLET_HITTING_BLOCK = SoundEvent.of(new Identifier(MOD_ID, "bullet_hitting_block"));
    public static final SoundEvent BULLET_HITTING_ENTITY = SoundEvent.of(new Identifier(MOD_ID, "bullet_hitting_entity"));
    public static final SoundEvent BULLET_SHELL_HITTING_THE_GROUND = SoundEvent.of(new Identifier(MOD_ID, "bullet_shell_hitting_the_ground"));
    public static final SoundEvent ELECTROMAGNETIC_EXPLOSION = SoundEvent.of(new Identifier(MOD_ID, "electromagnetic_explosion"));
    public static final SoundEvent ELECTROMAGNETIC_GUN_CHARGING = SoundEvent.of(new Identifier(MOD_ID, "electromagnetic_gun_charging"));
    public static final SoundEvent ELECTROMAGNETIC_GUN_SHOOT = SoundEvent.of(new Identifier(MOD_ID, "electromagnetic_gun_shoot"));
    public static final SoundEvent ROBOT_SENTINEL_DEATH = SoundEvent.of(new Identifier(MOD_ID, "robot_sentinel_death"));
    public static final SoundEvent ROBOT_SENTINEL_HURT = SoundEvent.of(new Identifier(MOD_ID, "robot_sentinel_hurt"));
    public static final SoundEvent ROBOT_SENTINEL_RADAR_DETECTION = SoundEvent.of(new Identifier(MOD_ID, "robot_sentinel_radar_detection"));

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "armored_personnel_unit_hurt"), ARMORED_PERSONNEL_UNIT_HURT);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "armored_personnel_unit_land"), ARMORED_PERSONNEL_UNIT_LAND);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "armored_personnel_unit_shoot"), ARMORED_PERSONNEL_UNIT_SHOOT);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "armored_personnel_unit_step"), ARMORED_PERSONNEL_UNIT_STEP);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "bullet_hitting_block"), BULLET_HITTING_BLOCK);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "bullet_hitting_entity"), BULLET_HITTING_ENTITY);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "bullet_shell_hitting_the_ground"), BULLET_SHELL_HITTING_THE_GROUND);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "electromagnetic_explosion"), ELECTROMAGNETIC_EXPLOSION);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "electromagnetic_gun_charging"), ELECTROMAGNETIC_GUN_CHARGING);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "electromagnetic_gun_shoot"), ELECTROMAGNETIC_GUN_SHOOT);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "robot_sentinel_death"), ROBOT_SENTINEL_DEATH);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "robot_sentinel_hurt"), ROBOT_SENTINEL_HURT);
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "robot_sentinel_radar_detection"), ROBOT_SENTINEL_RADAR_DETECTION);
    }
}
