package me.jaffe2718.the_matrix.client.render.item;

import me.jaffe2718.the_matrix.client.model.item.MachineGunModel;
import me.jaffe2718.the_matrix.element.item.MachineGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class MachineGunRenderer extends GeoItemRenderer<MachineGunItem> {
    public MachineGunRenderer() {
        super(new MachineGunModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
