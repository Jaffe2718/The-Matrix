package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.element.item.MachineGunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class MachineGunModel extends GeoModel<MachineGunItem> {
    @Override
    public Identifier getModelResource(MachineGunItem animatable) {
        return new Identifier(MOD_ID, "geo/item/machine_gun.geo.json");
    }

    @Override
    public Identifier getTextureResource(MachineGunItem animatable) {
        return new Identifier(MOD_ID, "textures/item/machine_gun.png");
    }

    @Override
    public Identifier getAnimationResource(MachineGunItem animatable) {
        return null;
    }
}
