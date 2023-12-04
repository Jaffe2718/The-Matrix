package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.element.item.HackerCloakItem;
import me.jaffe2718.the_matrix.element.item.HackerPantsItem;
import me.jaffe2718.the_matrix.element.item.VMaskItem;
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

    public static final ArmorMaterial VIRTUAL_ARMOR_MATERIAL = new VirtualArmorMaterial();
    public static final Item V_MASK = new VMaskItem(new FabricItemSettings().rarity(Rarity.EPIC));
    public static final Item HACKER_CLOAK = new HackerCloakItem(new FabricItemSettings().rarity(Rarity.EPIC));
    public static final Item HACKER_PANTS = new HackerPantsItem(new FabricItemSettings().rarity(Rarity.EPIC));

    public static final Item AGENT_SPAWN_EGG = new SpawnEggItem(EntityRegistry.AGENT,
            0x404040, 0xE5E15A,
            new FabricItemSettings());
    public static final Item ROBOT_SENTINEL_SPAWN_EGG = new SpawnEggItem(EntityRegistry.ROBOT_SENTINEL,
            0x303030, 0xFF0000,
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
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "agent_spawn_egg"), AGENT_SPAWN_EGG);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "robot_sentinel_spawn_egg"), ROBOT_SENTINEL_SPAWN_EGG);

        // add items to item group
        var groupRegistryKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(MOD_ID, "item_group"));
        ItemGroupEvents.modifyEntriesEvent(groupRegistryKey).register(groupEntries -> {
            groupEntries.add(V_MASK);
            groupEntries.add(HACKER_CLOAK);
            groupEntries.add(HACKER_PANTS);
            groupEntries.add(AGENT_SPAWN_EGG);
            groupEntries.add(ROBOT_SENTINEL_SPAWN_EGG);
        });

    }
}
