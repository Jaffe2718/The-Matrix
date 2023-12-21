package me.jaffe2718.the_matrix.element.item.armor_material;

import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

public class MechanicalMaterial implements ArmorMaterial {

    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};   // feet, legs, chest, head
    private static final int[] PROTECTION_VALUES = new int[] {8, 14, 16, 10};     // feet, legs, chest, head

    private static final int DURABILITY_MULTIPLIER = 40;
    private static final int ENCHANTABILITY = 15;
    private static final float TOUGHNESS = 4.5F;
    private static final float KNOCKBACK_RESISTANCE = 0.4F;
    private static final String NAME = "mechanical";

    @Override
    public int getDurability(ArmorItem.@NotNull Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * DURABILITY_MULTIPLIER;
    }

    @Override
    public int getProtection(ArmorItem.@NotNull Type type) {
        return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return ENCHANTABILITY;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEventRegistry.MECHANICAL_ARMOR_EQUIPS;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ItemRegistry.MACHINE_PART);
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
