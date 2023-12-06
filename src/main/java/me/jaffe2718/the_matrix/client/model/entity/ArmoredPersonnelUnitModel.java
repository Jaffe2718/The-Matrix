package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class ArmoredPersonnelUnitModel extends GeoModel<ArmoredPersonnelUnitEntity> {

    private static final String DRIVER_PITCH = "variable.driver_pitch"; // MoLang variable for driver pitch
    private static final String IS_WALKING = "variable.is_walking";   // MoLang variable for walking animation

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

    @Override
    public void applyMolangQueries(ArmoredPersonnelUnitEntity animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);
        MolangParser parser = MolangParser.INSTANCE;
        parser.setMemoizedValue(IS_WALKING, () -> {      // register is_walking variable to molang parser
            var velocity = animatable.getVelocity();
            double hSpeed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());
            return hSpeed > 5E-3 ? 1.0 : 0.0;
        });
        parser.setMemoizedValue(DRIVER_PITCH, () -> {      // register driver_pitch variable to molang parser
            Entity driver = animatable.getControllingPassenger();
            if (driver == null) {
                return 0.0;
            } else {
                return driver.getPitch();
            }
        });
    }
}
