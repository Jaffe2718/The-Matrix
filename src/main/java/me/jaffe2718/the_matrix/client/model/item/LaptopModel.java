package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.element.item.LaptopItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class LaptopModel extends GeoModel<LaptopItem> {
    @Override
    public Identifier getModelResource(LaptopItem animatable) {
        return new Identifier(MOD_ID, "geo/block/laptop.geo.json");
    }

    @Override
    public Identifier getTextureResource(LaptopItem animatable) {
        return new Identifier(MOD_ID, "textures/block/laptop.png");
    }

    @Override
    public Identifier getAnimationResource(LaptopItem animatable) {
        return null;
    }
}
