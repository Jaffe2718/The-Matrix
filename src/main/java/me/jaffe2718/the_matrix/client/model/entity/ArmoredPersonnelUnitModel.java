package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public class ArmoredPersonnelUnitModel extends GeoModel<ArmoredPersonnelUnitEntity> {

    public static final RawAnimation SHOOT = RawAnimation.begin().then("animation.armored_personnel_unit.shoot", Animation.LoopType.DEFAULT);
    public static final RawAnimation STATIC = RawAnimation.begin().then("animation.armored_personnel_unit.static", Animation.LoopType.DEFAULT);
    public static final RawAnimation WALK = RawAnimation.begin().then("animation.armored_personnel_unit.walk", Animation.LoopType.DEFAULT);

    private static final String DRIVER_PITCH = "variable.driver_pitch"; // MoLang variable for driver pitch
    private static final String IS_WALKING = "variable.is_walking";   // MoLang variable for walking animation

    @Override
    public Identifier getModelResource(ArmoredPersonnelUnitEntity animatable) {
        return TheMatrix.id("geo/entity/armored_personnel_unit.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArmoredPersonnelUnitEntity animatable) {
        return TheMatrix.id("textures/entity/armored_personnel_unit.png");
    }

    @Override
    public Identifier getAnimationResource(ArmoredPersonnelUnitEntity animatable) {
        return TheMatrix.id("animations/entity/armored_personnel_unit.animation.json");
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
            if (driver instanceof PlayerEntity player) {
                return player.getPitch();
            } else {
                return animatable.getPitch();
            }
            //return Objects.requireNonNullElse(driver, animatable).getPitch();
        });
    }
}
