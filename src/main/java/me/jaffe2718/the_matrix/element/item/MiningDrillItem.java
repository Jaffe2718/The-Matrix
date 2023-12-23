package me.jaffe2718.the_matrix.element.item;

import me.jaffe2718.the_matrix.client.render.item.MiningDrillRenderer;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static me.jaffe2718.the_matrix.client.model.item.MiningDrillModel.COMMON;


public class MiningDrillItem
        extends MiningToolItem
        implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public MiningDrillItem(Settings settings) {
        super(1F, -1F, new MechanicalToolMaterial(), BlockTags.PICKAXE_MINEABLE, settings);
    }

    @Override
    public void createRenderer(@NotNull Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final MiningDrillRenderer renderer = new MiningDrillRenderer();

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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> state.setAndContinue(COMMON)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private static class MechanicalToolMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 2700;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 12.0F;
        }

        @Override
        public float getAttackDamage() {
            return 5F;
        }

        @Override
        public int getMiningLevel() {
            return 4;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(ItemRegistry.MACHINE_PART);
        }
    }
}
