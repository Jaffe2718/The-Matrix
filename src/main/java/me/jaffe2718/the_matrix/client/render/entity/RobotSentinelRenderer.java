package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.RobotSentinelModel;
import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class RobotSentinelRenderer extends GeoEntityRenderer<RobotSentinelEntity> {
    public RobotSentinelRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RobotSentinelModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
