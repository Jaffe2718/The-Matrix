package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.element.item.MiningDrillItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class MiningDrillModel extends GeoModel<MiningDrillItem> {

    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.mining_drill.common", Animation.LoopType.DEFAULT);
    @Override
    public Identifier getModelResource(MiningDrillItem animatable) {
        return new Identifier(MOD_ID, "geo/item/mining_drill.geo.json");
    }

    @Override
    public Identifier getTextureResource(MiningDrillItem animatable) {
        return new Identifier(MOD_ID, "textures/item/mining_drill.png");
    }

    @Override
    public Identifier getAnimationResource(MiningDrillItem animatable) {
        return new Identifier(MOD_ID, "animations/item/mining_drill.animation.json");
    }
}
