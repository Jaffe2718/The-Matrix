package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.ZionPeopleModel;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ZionPeopleRenderer extends GeoEntityRenderer<ZionPeopleEntity> {
    public ZionPeopleRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ZionPeopleModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
