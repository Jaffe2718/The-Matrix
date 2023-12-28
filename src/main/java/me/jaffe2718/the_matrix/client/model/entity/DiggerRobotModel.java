package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.boss.DiggerRobotEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class DiggerRobotModel extends GeoModel<DiggerRobotEntity> {

    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.digger_robot.common", Animation.LoopType.DEFAULT);
    @Override
    public Identifier getModelResource(DiggerRobotEntity animatable) {
        return new Identifier(MOD_ID, "geo/entity/digger_robot.geo.json");
    }

    @Override
    public Identifier getTextureResource(DiggerRobotEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/digger_robot.png");
    }

    @Override
    public Identifier getAnimationResource(DiggerRobotEntity animatable) {
        return new Identifier(MOD_ID, "animations/entity/digger_robot.animation.json");
    }
}
