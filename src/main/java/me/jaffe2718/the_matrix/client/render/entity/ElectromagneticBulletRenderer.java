package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.ElectromagneticBulletModel;
import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ElectromagneticBulletRenderer extends GeoEntityRenderer<ElectromagneticBulletEntity> {
    public ElectromagneticBulletRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ElectromagneticBulletModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
