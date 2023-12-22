package me.jaffe2718.the_matrix.element.item;

import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;


public class ElectromagneticGunItem extends Item {

    public ElectromagneticGunItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull World world, Entity entity, int slot, boolean selected) {
        if (selected) {
            if (entity instanceof PlayerEntity player && !player.getItemCooldownManager().isCoolingDown(this) &&
                    (player.getInventory().contains(ItemRegistry.BATTERY.getDefaultStack()) || player.isCreative())) {
                if (stack.getOrCreateNbt().getInt("Energy") == 0) {
                    player.sendMessage(Text.translatable("message.the_matrix.electromagnetic_gun.should_charge"), true);
                }
                else {
                    player.sendMessage(Text.of(String.format("%d / 6", stack.getOrCreateNbt().getInt("Energy"))), true);
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.getOrCreateNbt().getInt("Energy") > 0) {   // shoot
            if (!world.isClient) {
                stack.getOrCreateNbt().putInt("Energy", stack.getOrCreateNbt().getInt("Energy") - 1);  // consume energy
                ElectromagneticBulletEntity.shoot(user, user.getEyePos(), user.getRotationVector().multiply(5));
                user.getItemCooldownManager().set(this, 100);
                if (!user.isCreative()) {
                    stack.damage(1, user, (player) -> player.sendToolBreakStatus(hand));
                }
            }
            user.playSound(SoundEventRegistry.ELECTROMAGNETIC_GUN_SHOOT, 1, 1);
            return TypedActionResult.consume(user.getStackInHand(hand));
        } else if ((user.getInventory().contains(ItemRegistry.BATTERY.getDefaultStack()) || user.isCreative())) { // charge the gun
            if (!world.isClient) {
                user.getStackInHand(hand).getOrCreateNbt().putInt("Energy", 6);
                user.getItemCooldownManager().set(this, 100);
            }
            user.getInventory().remove(stack_ -> stack_.getItem() == ItemRegistry.BATTERY, 1, user.getInventory());
            user.playSound(SoundEventRegistry.ELECTROMAGNETIC_GUN_CHARGING, 1, 1);
            return TypedActionResult.success(user.getStackInHand(hand), true);
        } else {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }

}
