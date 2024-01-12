package me.jaffe2718.the_matrix.client.render.item;

import me.jaffe2718.the_matrix.client.model.item.VendingMachineModel;
import me.jaffe2718.the_matrix.element.item.VendingMachineItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class VendingMachineRenderer extends GeoItemRenderer<VendingMachineItem> {
    public VendingMachineRenderer() {
        super(new VendingMachineModel());
    }
}
