package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.client.render.entity.SpaceshipRenderer;
import me.jaffe2718.the_matrix.element.entity.vehicle.SpaceshipEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

/**
 * The model for the spaceship.
 * @see SpaceshipEntity
 * @see SpaceshipRenderer
 * */
public class SpaceshipModel extends GeoModel<SpaceshipEntity> {

    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.spaceship.common", Animation.LoopType.DEFAULT);

    private static final String HAS_DRIVER = "variable.has_driver";       // MoLang variable for has driver
    private static final String IS_ACCELERATING = "variable.is_accelerating";   // MoLang variable for sprinting animation
    private static final String PITCH = "variable.pitch";                 // MoLang variable for pitch angle

    @Override
    public Identifier getModelResource(SpaceshipEntity animatable) {
        return TheMatrix.id("geo/entity/spaceship.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpaceshipEntity animatable) {
        return TheMatrix.id("textures/entity/spaceship.png");
    }

    @Override
    public Identifier getAnimationResource(SpaceshipEntity animatable) {
        return TheMatrix.id("animations/entity/spaceship.animation.json");
    }

    @Override
    public void applyMolangQueries(SpaceshipEntity animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);
        MolangParser parser = MolangParser.INSTANCE;
        parser.setMemoizedValue(HAS_DRIVER, () -> animatable.hasPassengers() ? 1.0 : 0.0);
        parser.setMemoizedValue(IS_ACCELERATING, () -> animatable.isAccelerating() ? 2.5 : 0.0);
        parser.setMemoizedValue(PITCH, animatable::getPitch);
    }
}
