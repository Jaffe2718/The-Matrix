package me.jaffe2718.the_matrix.element.item.armor_material;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class VirtualArmorMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private static final int[] PROTECTION_VALUES = new int[] {4, 8, 9, 4};

    private static final int DURABILITY_MULTIPLIER = 40;
    private static final int ENCHANTABILITY = 15;
    private static final float TOUGHNESS = 4.0F;
    private static final float KNOCKBACK_RESISTANCE = 0.15F;
    private static final String NAME = "virtual";

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * DURABILITY_MULTIPLIER;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return ENCHANTABILITY;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public float getToughness() {
        return TOUGHNESS;
    }

    @Override
    public float getKnockbackResistance() {
        return KNOCKBACK_RESISTANCE;
    }
}
