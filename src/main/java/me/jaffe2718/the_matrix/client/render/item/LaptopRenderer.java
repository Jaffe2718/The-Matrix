package me.jaffe2718.the_matrix.client.render.item;

import me.jaffe2718.the_matrix.client.model.item.LaptopModel;
import me.jaffe2718.the_matrix.element.item.LaptopItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LaptopRenderer extends GeoItemRenderer<LaptopItem> {
    public LaptopRenderer() {
        super(new LaptopModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
