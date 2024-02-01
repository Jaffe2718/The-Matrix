package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.SpaceshipModel;
import me.jaffe2718.the_matrix.element.entity.vehicle.SpaceshipEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SpaceshipRenderer extends GeoEntityRenderer<SpaceshipEntity> {
    public SpaceshipRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SpaceshipModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
