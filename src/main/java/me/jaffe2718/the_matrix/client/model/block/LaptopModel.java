package me.jaffe2718.the_matrix.client.model.block;

import me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity;
import me.jaffe2718.the_matrix.TheMatrix;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;


public class LaptopModel extends GeoModel<LaptopBlockEntity> {
    @Override
    public Identifier getModelResource(LaptopBlockEntity animatable) {
        return TheMatrix.id("geo/block/laptop.geo.json");
    }

    @Override
    public Identifier getTextureResource(LaptopBlockEntity animatable) {
        return TheMatrix.id("textures/block/laptop.png");
    }

    @Override
    public Identifier getAnimationResource(LaptopBlockEntity animatable) {
        return null;
    }

    @Override
    public RenderLayer getRenderType(LaptopBlockEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
    }
}
