package me.jaffe2718.the_matrix.element.item;

import me.jaffe2718.the_matrix.client.render.armor.VMaskRenderer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static me.jaffe2718.the_matrix.unit.ItemRegistry.VIRTUAL_ARMOR_MATERIAL;
public class VMaskItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public VMaskItem(Settings settings) {
        super(VIRTUAL_ARMOR_MATERIAL, Type.HELMET, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.the_matrix.v_mask.tooltip"));
    }

    /**
     * Create your RenderProvider reference here.<br>
     * <b><u>MUST provide an anonymous class</u></b><br>
     * Example Code:
     * <pre>{@code
     * @Override
     * public void createRenderer(Consumer<RenderProvider> consumer) {
     * 	consumer.accept(new RenderProvider() {
     * 		private final GeoArmorRenderer<?> renderer = new MyArmorRenderer();
     *
     *        @Override
     *        GeoArmorRenderer<?> getRenderer(GeoArmor armor) {
     * 			return this.renderer;
     *        }
     *    }
     * }
     * }</pre>
     *
     * @param consumer The consumer to accept your anonymous class
     */
    @Override
    public void createRenderer(@NotNull Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new VMaskRenderer();
                }
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return renderer;
            }
        });
    }

    /**
     * Getter for the cached RenderProvider in your class
     */
    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    /**
     * Register your {@link software.bernie.geckolib.core.animation.AnimationController AnimationControllers} and their respective animations and conditions.
     * Override this method in your animatable object and add your controllers via {@link AnimatableManager.ControllerRegistrar#add ControllerRegistrar.add}.
     * You may add as many controllers as wanted.
     * <br><br>
     * Each controller can only play <u>one</u> animation at a time, and so animations that you intend to play concurrently should be handled in independent controllers.
     * Note having multiple animations playing via multiple controllers can override parts of one animation with another if both animations use the same bones or child bones.
     *
     * @param controllers The object to register your controller instances to
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    /**
     * Each instance of a {@code GeoAnimatable} must return an instance of an {@link AnimatableInstanceCache}, which handles instance-specific animation info.
     * Generally speaking, you should create your cache using {@code GeckoLibUtil#createCache} and store it in your animatable instance, returning that cached instance when called.
     *
     * @return A cached instance of an {@code AnimatableInstanceCache}
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
