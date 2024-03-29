package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public class RobotSentinelModel extends GeoModel<RobotSentinelEntity> {

    public static final RawAnimation ATTACK_0 = RawAnimation.begin().then("animation.robot_sentinel.attack_0", Animation.LoopType.DEFAULT);
    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.robot_sentinel.common", Animation.LoopType.DEFAULT);
    private static final String DEPRESSION = "variable.depression";   // MoLang variable for depression angle
    @Override
    public Identifier getModelResource(RobotSentinelEntity animatable) {
        return TheMatrix.id("geo/entity/robot_sentinel.geo.json");
    }

    @Override
    public Identifier getTextureResource(RobotSentinelEntity animatable) {
        return TheMatrix.id("textures/entity/robot_sentinel.png");
    }

    @Override
    public Identifier getAnimationResource(RobotSentinelEntity animatable) {
        return TheMatrix.id("animations/entity/robot_sentinel.animation.json");
    }

    @Override
    public void applyMolangQueries(RobotSentinelEntity animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);
        MolangParser parser = MolangParser.INSTANCE;
        parser.setMemoizedValue(DEPRESSION, () -> {      // register depression variable to molang parser
            var velocity = animatable.getVelocity();
            double hSpeed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());
            double vSpeed = velocity.getY();
            if (Math.abs(hSpeed) < 1E-3) {   // 1E-3 is error limit
                if (vSpeed > 1E-3) {
                    return -90.0;
                } else if (vSpeed < -1E-3) {
                    return 90.0;
                } else {
                    return 0.0;
                }
            } else {
                return Math.toDegrees(-Math.atan(vSpeed / hSpeed));
            }
        });
    }
}
