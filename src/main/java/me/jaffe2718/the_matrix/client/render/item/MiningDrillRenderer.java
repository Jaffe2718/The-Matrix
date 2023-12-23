package me.jaffe2718.the_matrix.client.render.item;

import me.jaffe2718.the_matrix.client.model.item.MiningDrillModel;
import me.jaffe2718.the_matrix.element.item.MiningDrillItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class MiningDrillRenderer extends GeoItemRenderer<MiningDrillItem> {
    public MiningDrillRenderer() {
        super(new MiningDrillModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
