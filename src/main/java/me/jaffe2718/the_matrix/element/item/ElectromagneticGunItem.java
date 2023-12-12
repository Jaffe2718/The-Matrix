package me.jaffe2718.the_matrix.element.item;

import me.jaffe2718.the_matrix.client.render.item.ElectromagneticGunRenderer;
import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static me.jaffe2718.the_matrix.client.model.item.ElectromagneticGunModel.POWER_INDICATOR;

public class ElectromagneticGunItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    private int energy;     // min = 0, max = 6, 0 means empty, 6 means full

    public ElectromagneticGunItem(Settings settings) {
        super(settings);
        // get the energy from the NBT tag
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        this.energy = stack.getOrCreateNbt().getInt("Energy");
        if (this.energy == 0 &&
                entity instanceof PlayerEntity player && !player.getItemCooldownManager().isCoolingDown(this) &&
                (player.getInventory().contains(ItemRegistry.BATTERY.getDefaultStack()) || player.isCreative())) {
            player.sendMessage(Text.translatable("message.the_matrix.electromagnetic_gun.should_charge"), true);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (this.energy > 0) {   // shoot
            if (!world.isClient) {
                ItemStack stack = user.getStackInHand(hand);
                stack.getOrCreateNbt().putInt("Energy", this.energy - 1);
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
            user.getInventory().remove(stack -> stack.getItem() == ItemRegistry.BATTERY, 1, user.getInventory());
            return TypedActionResult.success(user.getStackInHand(hand), true);
        } else {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }

    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void createRenderer(@NotNull Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final ElectromagneticGunRenderer renderer = new ElectromagneticGunRenderer();
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", this::predicate));
    }

    private PlayState predicate(@NotNull AnimationState<ElectromagneticGunItem> state) {
        return state.setAndContinue(POWER_INDICATOR);

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
