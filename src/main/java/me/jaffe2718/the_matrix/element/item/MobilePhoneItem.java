package me.jaffe2718.the_matrix.element.item;

import me.jaffe2718.the_matrix.unit.Dimensions;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.ParticleRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MobilePhoneItem extends Item {
    public MobilePhoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.the_matrix.mobile_phone.tooltip"));
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, PlayerEntity user, Hand hand) {
        // check current dimension
        if (world.getRegistryKey() == Dimensions.VIRTUAL_WORLD) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        } else {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 48;
    }

    /**
     * Called when the player finishes using this Item<br>
     * <br>
     * Only coins can be transferred to the zion dimension, {@link me.jaffe2718.the_matrix.unit.ItemRegistry#COIN}<br>
     * Other items will be saved in the player's nbts and will be given back when the player returns to the current dimension<br>
     * */
    @Override
    public ItemStack finishUsing(@NotNull ItemStack stack, @NotNull World world, @NotNull LivingEntity user) {
        // transfer to zion dimension
        if (user.getWorld() instanceof ServerWorld serverWorld) {
            ServerWorld zionWorld = serverWorld.getServer().getWorld(Dimensions.ZION);
            if (user.getVehicle() == null && zionWorld != null) {
                zionWorld.getChunk(BlockPos.ORIGIN);
                if (user instanceof PlayerEntity player) {
                    if (!player.isCreative()) stack.decrement(1);
                    List<ItemStack> coinStacks = new ArrayList<>();
                    for (int i = player.getInventory().size(); i > 0; i--) {     // take all coins from player
                        ItemStack itemStack = player.getInventory().getStack(i);
                        if (itemStack.isOf(ItemRegistry.COIN)) {
                            coinStacks.add(itemStack);
                            player.getInventory().removeStack(i);
                        }
                    }
                    FabricDimensions.teleport(player, zionWorld, new TeleportTarget(Dimensions.getZionSpawnPos(zionWorld), Vec3d.ZERO, player.getYaw(), player.getPitch()));
                    for (ItemStack coinStack : coinStacks) {                     // give all coins to player back
                        player.getInventory().offerOrDrop(coinStack);
                    }
                } else {
                    FabricDimensions.teleport(user, zionWorld, new TeleportTarget(Dimensions.getZionSpawnPos(zionWorld), Vec3d.ZERO, user.getYaw(), user.getPitch()));
                }
            }
        }
        return stack;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks == this.getMaxUseTime(stack) - 1) {
            user.playSound(SoundEventRegistry.DIALING, 1F, 1F);
        }
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleRegistry.MOBILE_PHONE_01, user.getX(), user.getY(), user.getZ(), 1, 0.4, 0, 0.4, 0.1);
        }
    }


}
