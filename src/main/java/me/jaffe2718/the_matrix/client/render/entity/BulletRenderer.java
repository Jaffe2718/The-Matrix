package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.BulletModel;
import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class BulletRenderer extends GeoEntityRenderer<BulletEntity> {
    public BulletRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BulletModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
