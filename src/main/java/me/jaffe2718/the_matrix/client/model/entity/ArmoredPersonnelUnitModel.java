package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class ArmoredPersonnelUnitModel extends GeoModel<ArmoredPersonnelUnitEntity> {
    @Override
    public Identifier getModelResource(ArmoredPersonnelUnitEntity animatable) {
        return new Identifier(MOD_ID, "geo/entity/armored_personnel_unit.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArmoredPersonnelUnitEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/armored_personnel_unit.png");
    }

    @Override
    public Identifier getAnimationResource(ArmoredPersonnelUnitEntity animatable) {
        return new Identifier(MOD_ID, "animations/entity/armored_personnel_unit.animation.json");
    }
}
