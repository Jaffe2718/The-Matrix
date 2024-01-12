package me.jaffe2718.the_matrix.client.model.block;

import me.jaffe2718.the_matrix.element.block.entity.VendingMachineBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class VendingMachineModel extends GeoModel<VendingMachineBlockEntity> {
    @Override
    public Identifier getModelResource(VendingMachineBlockEntity animatable) {
        return new Identifier(MOD_ID, "geo/block/vending_machine.geo.json");
    }

    @Override
    public Identifier getTextureResource(VendingMachineBlockEntity animatable) {
        return new Identifier(MOD_ID, "textures/block/vending_machine.png");
    }

    @Override
    public Identifier getAnimationResource(VendingMachineBlockEntity animatable) {
        return null;
    }

//    @Override
//    public RenderLayer getRenderType(VendingMachineBlockEntity animatable, Identifier texture) {
//        return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable), false);
//    }
}
