package me.jaffe2718.the_matrix.client.render.block.entity;

import me.jaffe2718.the_matrix.client.model.block.VendingMachineModel;
import me.jaffe2718.the_matrix.element.block.entity.VendingMachineBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class VendingMachineRenderer extends GeoBlockRenderer<VendingMachineBlockEntity> {
    public VendingMachineRenderer(BlockEntityRendererFactory.Context ctx) {
        super(new VendingMachineModel());
    }

}
