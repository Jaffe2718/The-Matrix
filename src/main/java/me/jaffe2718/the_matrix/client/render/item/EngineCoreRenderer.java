package me.jaffe2718.the_matrix.client.render.item;

import me.jaffe2718.the_matrix.client.model.item.EngineCoreModel;
import me.jaffe2718.the_matrix.element.item.EngineCoreItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class EngineCoreRenderer extends GeoItemRenderer<EngineCoreItem> {
    public EngineCoreRenderer() {
        super(new EngineCoreModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
