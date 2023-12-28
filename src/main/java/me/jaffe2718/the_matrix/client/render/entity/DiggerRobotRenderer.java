package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.DiggerRobotModel;
import me.jaffe2718.the_matrix.element.entity.boss.DiggerRobotEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class DiggerRobotRenderer extends GeoEntityRenderer<DiggerRobotEntity> {
    public DiggerRobotRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DiggerRobotModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
