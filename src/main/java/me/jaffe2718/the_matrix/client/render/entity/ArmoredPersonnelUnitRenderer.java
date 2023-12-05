package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.ArmoredPersonnelUnitModel;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ArmoredPersonnelUnitRenderer extends GeoEntityRenderer<ArmoredPersonnelUnitEntity> {
    public ArmoredPersonnelUnitRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ArmoredPersonnelUnitModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

}
