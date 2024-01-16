package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.LaptopItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class LaptopModel extends GeoModel<LaptopItem> {
    @Override
    public Identifier getModelResource(LaptopItem animatable) {
        return TheMatrix.id("geo/block/laptop.geo.json");
    }

    @Override
    public Identifier getTextureResource(LaptopItem animatable) {
        return TheMatrix.id("textures/block/laptop.png");
    }

    @Override
    public Identifier getAnimationResource(LaptopItem animatable) {
        return null;
    }
}
