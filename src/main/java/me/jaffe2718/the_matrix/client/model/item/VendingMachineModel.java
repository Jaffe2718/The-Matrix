package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.element.item.VendingMachineItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

import java.util.Optional;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class VendingMachineModel extends GeoModel<VendingMachineItem> {

    @Override
    public Identifier getModelResource(VendingMachineItem animatable) {
        return new Identifier(MOD_ID, "geo/block/vending_machine.geo.json");
    }

    @Override
    public Identifier getTextureResource(VendingMachineItem animatable) {
        return new Identifier(MOD_ID, "textures/block/vending_machine.png");
    }

    @Override
    public Identifier getAnimationResource(VendingMachineItem animatable) {
        return null;
    }

    @Override
    public BakedGeoModel getBakedModel(Identifier location) {
        BakedGeoModel model = super.getBakedModel(location);
        // set offsetY = -8 to make the model appear on the ground
        Optional<GeoBone> main = model.getBone("main");
        main.ifPresent(geoBone -> geoBone.setModelPosition(geoBone.getModelPosition().add(0, -8, 0)));
        return model;
    }
}
