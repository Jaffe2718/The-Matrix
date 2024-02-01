package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public abstract class SoundEventRegistry {

    public static final SoundEvent ARMORED_PERSONNEL_UNIT_DEATH = SoundEvent.of(TheMatrix.id("armored_personnel_unit_death"));
    public static final SoundEvent ARMORED_PERSONNEL_UNIT_HURT = SoundEvent.of(TheMatrix.id("armored_personnel_unit_hurt"));
    public static final SoundEvent ARMORED_PERSONNEL_UNIT_LAND = SoundEvent.of(TheMatrix.id("armored_personnel_unit_land"));
    public static final SoundEvent ARMORED_PERSONNEL_UNIT_SHOOT = SoundEvent.of(TheMatrix.id("armored_personnel_unit_shoot"), 32F);
    public static final SoundEvent ARMORED_PERSONNEL_UNIT_STEP = SoundEvent.of(TheMatrix.id("armored_personnel_unit_step"));
    public static final SoundEvent BULLET_HITTING_BLOCK = SoundEvent.of(TheMatrix.id("bullet_hitting_block"));
    public static final SoundEvent BULLET_HITTING_ENTITY = SoundEvent.of(TheMatrix.id("bullet_hitting_entity"));
    public static final SoundEvent BULLET_SHELL_HITTING_THE_GROUND = SoundEvent.of(TheMatrix.id("bullet_shell_hitting_the_ground"));
    public static final SoundEvent DIALING = SoundEvent.of(TheMatrix.id("dialing"));
    public static final SoundEvent ELECTROMAGNETIC_EXPLOSION = SoundEvent.of(TheMatrix.id("electromagnetic_explosion"));
    public static final SoundEvent ELECTROMAGNETIC_GUN_CHARGING = SoundEvent.of(TheMatrix.id("electromagnetic_gun_charging"));
    public static final SoundEvent ELECTROMAGNETIC_GUN_SHOOT = SoundEvent.of(TheMatrix.id("electromagnetic_gun_shoot"));
    public static final SoundEvent GAME2048_BUTTON_CLICKED = SoundEvent.of(TheMatrix.id("game2048_button_clicked"));
    public static final SoundEvent GAME2048_GAIN_COINS = SoundEvent.of(TheMatrix.id("game2048_gain_coins"));
    public static final SoundEvent LOADING_BULLET = SoundEvent.of(TheMatrix.id("loading_bullet"));
    public static final SoundEvent MACHINE_GUN_SHOOT = SoundEvent.of(TheMatrix.id("machine_gun_shoot"), 32F);
    public static final SoundEvent MECHANICAL_ARMOR_EQUIPS = SoundEvent.of(TheMatrix.id("mechanical_armor_equips"));
    public static final SoundEvent ROBOT_SENTINEL_DEATH = SoundEvent.of(TheMatrix.id("robot_sentinel_death"));
    public static final SoundEvent ROBOT_SENTINEL_HURT = SoundEvent.of(TheMatrix.id("robot_sentinel_hurt"));
    public static final SoundEvent ROBOT_SENTINEL_RADAR_DETECTION = SoundEvent.of(TheMatrix.id("robot_sentinel_radar_detection"));
    public static final SoundEvent SPACESHIP_ACCELERATING = SoundEvent.of(TheMatrix.id("spaceship_accelerating"));
    public static final SoundEvent SPANNER_TWIST = SoundEvent.of(TheMatrix.id("spanner_twist"));
    public static final SoundEvent VENDING_MACHINE_GOODS_POPPING = SoundEvent.of(TheMatrix.id("vending_machine_goods_popping"));
    public static final SoundEvent VENDING_MACHINE_SWITCHING_OPTIONS = SoundEvent.of(TheMatrix.id("vending_machine_switching_options"));
    public static final SoundEvent ZION_PEOPLE_CARPENTER_PROMOTE = SoundEvent.of(TheMatrix.id("zion_people_carpenter_promote"));
    public static final SoundEvent ZION_PEOPLE_FARM_BREEDER_PROMOTE = SoundEvent.of(TheMatrix.id("zion_people_farm_breeder_promote"));
    public static final SoundEvent ZION_PEOPLE_FARMER_PROMOTE = SoundEvent.of(TheMatrix.id("zion_people_farmer_promote"));
    public static final SoundEvent ZION_PEOPLE_FEMALE_DEATH = SoundEvent.of(TheMatrix.id("zion_people_female_death"));
    public static final SoundEvent ZION_PEOPLE_FEMALE_GREET = SoundEvent.of(TheMatrix.id("zion_people_female_greet"));
    public static final SoundEvent ZION_PEOPLE_FEMALE_HURT = SoundEvent.of(TheMatrix.id("zion_people_female_hurt"));
    public static final SoundEvent ZION_PEOPLE_FEMALE_TRADE = SoundEvent.of(TheMatrix.id("zion_people_female_trade"));
    public static final SoundEvent ZION_PEOPLE_GROCER_PROMOTE = SoundEvent.of(TheMatrix.id("zion_people_grocer_promote"));
    public static final SoundEvent ZION_PEOPLE_MACHINIST_PROMOTE = SoundEvent.of(TheMatrix.id("zion_people_machinist_promote"));
    public static final SoundEvent ZION_PEOPLE_MALE_DEATH = SoundEvent.of(TheMatrix.id("zion_people_male_death"));
    public static final SoundEvent ZION_PEOPLE_MALE_GREET = SoundEvent.of(TheMatrix.id("zion_people_male_greet"));
    public static final SoundEvent ZION_PEOPLE_MALE_HURT = SoundEvent.of(TheMatrix.id("zion_people_male_hurt"));
    public static final SoundEvent ZION_PEOPLE_MALE_TRADE = SoundEvent.of(TheMatrix.id("zion_people_male_trade"));
    public static final SoundEvent ZION_PEOPLE_MINER_PROMOTE = SoundEvent.of(TheMatrix.id("zion_people_miner_promote"));
    public static final SoundEvent ZION_PEOPLE_SOLDIER_PROMOTE = SoundEvent.of(TheMatrix.id("zion_people_soldier_promote"));


    public static void register() {
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("armored_personnel_unit_death"), ARMORED_PERSONNEL_UNIT_DEATH);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("armored_personnel_unit_hurt"), ARMORED_PERSONNEL_UNIT_HURT);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("armored_personnel_unit_land"), ARMORED_PERSONNEL_UNIT_LAND);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("armored_personnel_unit_shoot"), ARMORED_PERSONNEL_UNIT_SHOOT);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("armored_personnel_unit_step"), ARMORED_PERSONNEL_UNIT_STEP);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("bullet_hitting_block"), BULLET_HITTING_BLOCK);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("bullet_hitting_entity"), BULLET_HITTING_ENTITY);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("bullet_shell_hitting_the_ground"), BULLET_SHELL_HITTING_THE_GROUND);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("dialing"), DIALING);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("electromagnetic_explosion"), ELECTROMAGNETIC_EXPLOSION);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("electromagnetic_gun_charging"), ELECTROMAGNETIC_GUN_CHARGING);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("electromagnetic_gun_shoot"), ELECTROMAGNETIC_GUN_SHOOT);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("game2048_button_clicked"), GAME2048_BUTTON_CLICKED);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("game2048_gain_coins"), GAME2048_GAIN_COINS);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("loading_bullet"), LOADING_BULLET);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("machine_gun_shoot"), MACHINE_GUN_SHOOT);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("mechanical_armor_equips"), MECHANICAL_ARMOR_EQUIPS);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("robot_sentinel_death"), ROBOT_SENTINEL_DEATH);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("robot_sentinel_hurt"), ROBOT_SENTINEL_HURT);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("robot_sentinel_radar_detection"), ROBOT_SENTINEL_RADAR_DETECTION);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("spaceship_accelerating"), SPACESHIP_ACCELERATING);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("spanner_twist"), SPANNER_TWIST);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("vending_machine_goods_popping"), VENDING_MACHINE_GOODS_POPPING);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("vending_machine_switching_options"), VENDING_MACHINE_SWITCHING_OPTIONS);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_carpenter_promote"), ZION_PEOPLE_CARPENTER_PROMOTE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_farm_breeder_promote"), ZION_PEOPLE_FARM_BREEDER_PROMOTE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_farmer_promote"), ZION_PEOPLE_FARMER_PROMOTE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_female_death"), ZION_PEOPLE_FEMALE_DEATH);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_female_greet"), ZION_PEOPLE_FEMALE_GREET);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_female_hurt"), ZION_PEOPLE_FEMALE_HURT);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_female_trade"), ZION_PEOPLE_FEMALE_TRADE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_grocer_promote"), ZION_PEOPLE_GROCER_PROMOTE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_machinist_promote"), ZION_PEOPLE_MACHINIST_PROMOTE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_male_death"), ZION_PEOPLE_MALE_DEATH);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_male_greet"), ZION_PEOPLE_MALE_GREET);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_male_hurt"), ZION_PEOPLE_MALE_HURT);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_male_trade"), ZION_PEOPLE_MALE_TRADE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_miner_promote"), ZION_PEOPLE_MINER_PROMOTE);
        Registry.register(Registries.SOUND_EVENT, TheMatrix.id("zion_people_soldier_promote"), ZION_PEOPLE_SOLDIER_PROMOTE);
    }
}
