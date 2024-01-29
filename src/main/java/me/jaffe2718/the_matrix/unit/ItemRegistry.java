package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.*;
import me.jaffe2718.the_matrix.element.item.armor_material.MechanicalMaterial;
import me.jaffe2718.the_matrix.element.item.armor_material.VirtualArmorMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    public static final Item BATTERY = new Item(new FabricItemSettings()) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
            tooltip.add(Text.translatable("item.the_matrix.battery.tooltip"));
        }
    };
    public static final Item BOXED_BULLETS = new Item(new FabricItemSettings()) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
            tooltip.add(Text.translatable("item.the_matrix.boxed_bullets.tooltip"));
        }
    };
    public static final Item BULLET = new Item(new FabricItemSettings()) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
            tooltip.add(Text.translatable("item.the_matrix.bullet.tooltip"));
        }
    };
    public static final Item BULLET_FILLING_BOX = new Item(new FabricItemSettings().maxCount(1).maxDamage(10)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
            tooltip.add(Text.translatable("item.the_matrix.bullet_filling_box.tooltip"));
        }
    };
    public static final Item COIN = new Item(new FabricItemSettings());    // use it in virtual world
    public static final Item CPU = new Item(new FabricItemSettings());
    public static final Item DEEPSLATE_PROMETHIUM_ORE = new BlockItem(BlockRegistry.DEEPSLATE_PROMETHIUM_ORE, new FabricItemSettings());
    public static final Item ELECTROMAGNETIC_GUN = new ElectromagneticGunItem(new FabricItemSettings().maxCount(1).maxDamage(100));
    public static final Item ENGINE_CORE = new EngineCoreItem(BlockRegistry.ENGINE_CORE, new FabricItemSettings());
    public static final Item LAPTOP = new LaptopItem(BlockRegistry.LAPTOP, new FabricItemSettings());
    public static final Item MACHINE_BLOCK = new BlockItem(BlockRegistry.MACHINE_BLOCK, new FabricItemSettings());
    public static final Item MACHINE_GUN = new MachineGunItem(new FabricItemSettings());
    public static final Item MACHINE_PART = new Item(new FabricItemSettings());
    public static final Item MINING_DRILL = new MiningDrillItem(new FabricItemSettings());
    public static final Item MOBILE_PHONE = new MobilePhoneItem(new FabricItemSettings());
    public static final Item PROMETHIUM_BLOCK = new BlockItem(BlockRegistry.PROMETHIUM_BLOCK, new FabricItemSettings());
    public static final Item PROMETHIUM_INGOT = new Item(new FabricItemSettings());
    public static final Item PROMETHIUM_ORE = new BlockItem(BlockRegistry.PROMETHIUM_ORE, new FabricItemSettings());
    public static final Item RAW_PROMETHIUM = new Item(new FabricItemSettings());
    public static final Item RAW_PROMETHIUM_BLOCK = new BlockItem(BlockRegistry.RAW_PROMETHIUM_BLOCK, new FabricItemSettings());
    public static final Item TELEPORTER = new BlockItem(BlockRegistry.TELEPORTER, new FabricItemSettings());
    public static final Item PLASMA_LAMP = new BlockItem(BlockRegistry.PLASMA_LAMP, new FabricItemSettings());
    public static final Item SPANNER = new Item(new FabricItemSettings().maxCount(1).maxDamage(20)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
            tooltip.add(Text.translatable("item.the_matrix.spanner.tooltip"));
        }
    };
    public static final Item VENDING_MACHINE = new BlockItem(BlockRegistry.VENDING_MACHINE, new FabricItemSettings());

    public static final Item AGENT_SPAWN_EGG = new SpawnEggItem(EntityRegistry.AGENT,
            0x404040, 0xE5E15A,
            new FabricItemSettings());
    public static final Item ROBOT_SENTINEL_SPAWN_EGG = new SpawnEggItem(EntityRegistry.ROBOT_SENTINEL,
            0x303030, 0xFF0000,
            new FabricItemSettings());
    public static final Item ZION_PEOPLE_SPAWN_EGG = new SpawnEggItem(EntityRegistry.ZION_PEOPLE,
            0xF0CCAF, 0x60D6FF,
            new FabricItemSettings());


    public static final ItemGroup THE_MATRIX_Item_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(V_MASK))
            .displayName(Text.translatable("itemGroup." + MOD_ID))
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, TheMatrix.id("item_group"), THE_MATRIX_Item_GROUP);

        Registry.register(Registries.ITEM, TheMatrix.id("v_mask"), V_MASK);
        Registry.register(Registries.ITEM, TheMatrix.id("hacker_cloak"), HACKER_CLOAK);
        Registry.register(Registries.ITEM, TheMatrix.id("hacker_pants"), HACKER_PANTS);
        Registry.register(Registries.ITEM, TheMatrix.id("hacker_boots"), HACKER_BOOTS);

        Registry.register(Registries.ITEM, TheMatrix.id("mechanical_helmet"), MECHANICAL_HELMET);
        Registry.register(Registries.ITEM, TheMatrix.id("mechanical_chestplate"), MECHANICAL_CHESTPLATE);
        Registry.register(Registries.ITEM, TheMatrix.id("mechanical_leggings"), MECHANICAL_LEGGINGS);
        Registry.register(Registries.ITEM, TheMatrix.id("mechanical_boots"), MECHANICAL_BOOTS);

        Registry.register(Registries.ITEM, TheMatrix.id("battery"), BATTERY);
        Registry.register(Registries.ITEM, TheMatrix.id("boxed_bullets"), BOXED_BULLETS);
        Registry.register(Registries.ITEM, TheMatrix.id("bullet"), BULLET);
        Registry.register(Registries.ITEM, TheMatrix.id("bullet_filling_box"), BULLET_FILLING_BOX);
        Registry.register(Registries.ITEM, TheMatrix.id("coin"), COIN);
        Registry.register(Registries.ITEM, TheMatrix.id("cpu"), CPU);
        Registry.register(Registries.ITEM, TheMatrix.id("deepslate_promethium_ore"), DEEPSLATE_PROMETHIUM_ORE);
        Registry.register(Registries.ITEM, TheMatrix.id("electromagnetic_gun"), ELECTROMAGNETIC_GUN);
        Registry.register(Registries.ITEM, TheMatrix.id("engine_core"), ENGINE_CORE);
        Registry.register(Registries.ITEM, TheMatrix.id("laptop"), LAPTOP);
        Registry.register(Registries.ITEM, TheMatrix.id("machine_block"), MACHINE_BLOCK);
        Registry.register(Registries.ITEM, TheMatrix.id("machine_gun"), MACHINE_GUN);
        Registry.register(Registries.ITEM, TheMatrix.id("machine_part"), MACHINE_PART);
        Registry.register(Registries.ITEM, TheMatrix.id("mining_drill"), MINING_DRILL);
        Registry.register(Registries.ITEM, TheMatrix.id("mobile_phone"), MOBILE_PHONE);
        Registry.register(Registries.ITEM, TheMatrix.id("plasma_lamp"), PLASMA_LAMP);
        Registry.register(Registries.ITEM, TheMatrix.id("promethium_block"), PROMETHIUM_BLOCK);
        Registry.register(Registries.ITEM, TheMatrix.id("promethium_ingot"), PROMETHIUM_INGOT);
        Registry.register(Registries.ITEM, TheMatrix.id("promethium_ore"), PROMETHIUM_ORE);
        Registry.register(Registries.ITEM, TheMatrix.id("raw_promethium"), RAW_PROMETHIUM);
        Registry.register(Registries.ITEM, TheMatrix.id("raw_promethium_block"), RAW_PROMETHIUM_BLOCK);
        Registry.register(Registries.ITEM, TheMatrix.id("spanner"), SPANNER);
        Registry.register(Registries.ITEM, TheMatrix.id("teleporter"), TELEPORTER);
        Registry.register(Registries.ITEM, TheMatrix.id("vending_machine"), VENDING_MACHINE);

        Registry.register(Registries.ITEM, TheMatrix.id("agent_spawn_egg"), AGENT_SPAWN_EGG);
        Registry.register(Registries.ITEM, TheMatrix.id("robot_sentinel_spawn_egg"), ROBOT_SENTINEL_SPAWN_EGG);
        Registry.register(Registries.ITEM, TheMatrix.id("zion_people_spawn_egg"), ZION_PEOPLE_SPAWN_EGG);

        // add items to item group
        RegistryKey<ItemGroup> groupRegistryKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), TheMatrix.id("item_group"));
        ItemGroupEvents.modifyEntriesEvent(groupRegistryKey).register(group -> {
            group.add(V_MASK);
            group.add(HACKER_CLOAK);
            group.add(HACKER_PANTS);
            group.add(HACKER_BOOTS);
            group.add(MECHANICAL_HELMET);
            group.add(MECHANICAL_CHESTPLATE);
            group.add(MECHANICAL_LEGGINGS);
            group.add(MECHANICAL_BOOTS);

            group.add(BATTERY);
            group.add(BOXED_BULLETS);
            group.add(BULLET);
            group.add(BULLET_FILLING_BOX);
            group.add(COIN);
            group.add(CPU);
            group.add(DEEPSLATE_PROMETHIUM_ORE);
            group.add(ELECTROMAGNETIC_GUN);
            group.add(ENGINE_CORE);
            group.add(LAPTOP);
            group.add(MACHINE_BLOCK);
            group.add(MACHINE_GUN);
            group.add(MACHINE_PART);
            group.add(MINING_DRILL);
            group.add(MOBILE_PHONE);
            group.add(PLASMA_LAMP);
            group.add(PROMETHIUM_BLOCK);
            group.add(PROMETHIUM_INGOT);
            group.add(PROMETHIUM_ORE);
            group.add(RAW_PROMETHIUM);
            group.add(RAW_PROMETHIUM_BLOCK);
            group.add(SPANNER);
            group.add(TELEPORTER);
            group.add(VENDING_MACHINE);

            group.add(AGENT_SPAWN_EGG);
            group.add(ROBOT_SENTINEL_SPAWN_EGG);
            group.add(ZION_PEOPLE_SPAWN_EGG);
        });

    }
}
