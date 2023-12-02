package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.RobotSentinelModel;
import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class RobotSentinelRenderer extends GeoEntityRenderer<RobotSentinelEntity> {
    public RobotSentinelRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RobotSentinelModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(RobotSentinelEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/robot_sentinel.png");
    }
}
