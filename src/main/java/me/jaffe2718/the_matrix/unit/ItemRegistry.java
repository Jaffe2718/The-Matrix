package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.element.item.*;
import me.jaffe2718.the_matrix.element.item.armor_material.MechanicalMaterial;
import me.jaffe2718.the_matrix.element.item.armor_material.VirtualArmorMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class ItemRegistry {

    public static final ArmorMaterial MECHANICAL_ARMOR_MATERIAL = new MechanicalMaterial();
    public static final ArmorMaterial VIRTUAL_ARMOR_MATERIAL = new VirtualArmorMaterial();
    public static final Item V_MASK = new VMaskItem(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item HACKER_CLOAK = new HackerCloakItem(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item HACKER_PANTS = new HackerPantsItem(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item HACKER_BOOTS = new HackerBootsItem(new FabricItemSettings().rarity(Rarity.RARE));

    public static final Item MECHANICAL_HELMET = new MechanicalArmorItem(ArmorItem.Type.HELMET, new FabricItemSettings().fireproof());
    public static final Item MECHANICAL_CHESTPLATE = new MechanicalArmorItem(ArmorItem.Type.CHESTPLATE, new FabricItemSettings().fireproof());
    public static final Item MECHANICAL_LEGGINGS = new MechanicalArmorItem(ArmorItem.Type.LEGGINGS, new FabricItemSettings().fireproof());
    public static final Item MECHANICAL_BOOTS = new MechanicalArmorItem(ArmorItem.Type.BOOTS, new FabricItemSettings().fireproof());

    public static final Item BATTERY = new Item(new FabricItemSettings());
    public static final Item BULLET = new Item(new FabricItemSettings());
    public static final Item COIN = new Item(new FabricItemSettings());    // use it in virtual world
    public static final Item CPU = new Item(new FabricItemSettings());
    public static final Item ELECTROMAGNETIC_GUN = new ElectromagneticGunItem(
            new FabricItemSettings()
                    .maxCount(1)
                    .maxDamage(100));
    public static final Item MACHINE_GUN = new MachineGunItem(new FabricItemSettings());
    public static final Item MACHINE_PART = new Item(new FabricItemSettings());
    public static final Item MOBILE_PHONE = new Item(new FabricItemSettings());

    public static final Item AGENT_SPAWN_EGG = new SpawnEggItem(EntityRegistry.AGENT,
            0x404040, 0xE5E15A,
            new FabricItemSettings());
    public static final Item ROBOT_SENTINEL_SPAWN_EGG = new SpawnEggItem(EntityRegistry.ROBOT_SENTINEL,
            0x303030, 0xFF0000,
            new FabricItemSettings());
    public static final Item ZION_PEOPLE_SPAWN_EGG = new SpawnEggItem(EntityRegistry.ZION_PEOPLE,
            0x9F7A71, 0x60D6FF,
            new FabricItemSettings());


    public static final ItemGroup THE_MATRIX_Item_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(V_MASK))
            .displayName(Text.translatable("itemGroup." + MOD_ID))
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "item_group"), THE_MATRIX_Item_GROUP);

        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "v_mask"), V_MASK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "hacker_cloak"), HACKER_CLOAK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "hacker_pants"), HACKER_PANTS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "hacker_boots"), HACKER_BOOTS);

        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "mechanical_helmet"), MECHANICAL_HELMET);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "mechanical_chestplate"), MECHANICAL_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "mechanical_leggings"), MECHANICAL_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "mechanical_boots"), MECHANICAL_BOOTS);

        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "battery"), BATTERY);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "bullet"), BULLET);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "coin"), COIN);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "cpu"), CPU);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "electromagnetic_gun"), ELECTROMAGNETIC_GUN);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "machine_gun"), MACHINE_GUN);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "machine_part"), MACHINE_PART);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "mobile_phone"), MOBILE_PHONE);

        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "agent_spawn_egg"), AGENT_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "robot_sentinel_spawn_egg"), ROBOT_SENTINEL_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "zion_people_spawn_egg"), ZION_PEOPLE_SPAWN_EGG);

        // add items to item group
        RegistryKey<ItemGroup> groupRegistryKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(MOD_ID, "item_group"));
        ItemGroupEvents.modifyEntriesEvent(groupRegistryKey).register(groupEntries -> {
            groupEntries.add(V_MASK);
            groupEntries.add(HACKER_CLOAK);
            groupEntries.add(HACKER_PANTS);
            groupEntries.add(HACKER_BOOTS);
            groupEntries.add(MECHANICAL_HELMET);
            groupEntries.add(MECHANICAL_CHESTPLATE);
            groupEntries.add(MECHANICAL_LEGGINGS);
            groupEntries.add(MECHANICAL_BOOTS);

            groupEntries.add(BATTERY);
            groupEntries.add(BULLET);
            groupEntries.add(COIN);
            groupEntries.add(CPU);
            groupEntries.add(ELECTROMAGNETIC_GUN);
            groupEntries.add(MACHINE_GUN);
            groupEntries.add(MACHINE_PART);
            groupEntries.add(MOBILE_PHONE);

            groupEntries.add(AGENT_SPAWN_EGG);
            groupEntries.add(ROBOT_SENTINEL_SPAWN_EGG);
            groupEntries.add(ZION_PEOPLE_SPAWN_EGG);
        });

    }
}
