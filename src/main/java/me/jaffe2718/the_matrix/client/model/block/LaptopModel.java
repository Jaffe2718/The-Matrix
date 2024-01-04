package me.jaffe2718.the_matrix.client.model.block;

import me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class LaptopModel extends GeoModel<LaptopBlockEntity> {
    @Override
    public Identifier getModelResource(LaptopBlockEntity animatable) {
        return new Identifier(MOD_ID, "geo/block/laptop.geo.json");
    }

    @Override
    public Identifier getTextureResource(LaptopBlockEntity animatable) {
        return new Identifier(MOD_ID, "textures/block/laptop.png");
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
