package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.MachineGunModel;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class MachineGunRenderer extends GeoEntityRenderer<MachineGunEntity> {
    public MachineGunRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MachineGunModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
