package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.EngineCoreItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;

public class EngineCoreModel extends GeoModel<EngineCoreItem> {
    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.engine_core.common", Animation.LoopType.DEFAULT);
    @Override
    public Identifier getModelResource(EngineCoreItem animatable) {
        return TheMatrix.id("geo/block/engine_core.geo.json");
    }

    @Override
    public Identifier getTextureResource(EngineCoreItem animatable) {
        return TheMatrix.id("textures/block/engine_core.png");
    }

    @Override
    public Identifier getAnimationResource(EngineCoreItem animatable) {
        return TheMatrix.id("animations/block/engine_core.animation.json");
    }
}
