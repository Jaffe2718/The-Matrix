package me.jaffe2718.the_matrix.element.item;

import me.jaffe2718.the_matrix.client.render.item.ElectromagneticGunRenderer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static me.jaffe2718.the_matrix.client.model.item.ElectromagneticGunModel.POWER_INDICATOR;

public class ElectromagneticGunItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    private int energy = 0;     // min = 0, max = 6, 0 means empty, 6 means full

    public ElectromagneticGunItem(Settings settings) {
        super(settings);
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
        controllers.add(new AnimationController<>(this, "controller", 20, this::predicate));
    }

    private PlayState predicate(@NotNull AnimationState<ElectromagneticGunItem> state) {
        return state.setAndContinue(POWER_INDICATOR);

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
