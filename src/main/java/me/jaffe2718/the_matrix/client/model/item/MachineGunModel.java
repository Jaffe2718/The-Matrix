package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.MachineGunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MachineGunModel extends GeoModel<MachineGunItem> {
    @Override
    public Identifier getModelResource(MachineGunItem animatable) {
        return TheMatrix.id("geo/item/machine_gun.geo.json");
    }

    @Override
    public Identifier getTextureResource(MachineGunItem animatable) {
        return TheMatrix.id("textures/item/machine_gun.png");
    }

    @Override
    public Identifier getAnimationResource(MachineGunItem animatable) {
        return null;
    }
}
