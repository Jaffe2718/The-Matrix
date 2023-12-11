package me.jaffe2718.the_matrix.client.render.item;

import me.jaffe2718.the_matrix.client.model.item.ElectromagneticGunModel;
import me.jaffe2718.the_matrix.element.item.ElectromagneticGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ElectromagneticGunRenderer extends GeoItemRenderer<ElectromagneticGunItem> {
    public ElectromagneticGunRenderer() {
        super(new ElectromagneticGunModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
