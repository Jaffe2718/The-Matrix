package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class RobotSentinelModel extends GeoModel<RobotSentinelEntity> {
    private static final String VERTICAL_SPEED = "query.vertical_speed";
    @Override
    public Identifier getModelResource(RobotSentinelEntity animatable) {
        return new Identifier(MOD_ID, "geo/entity/robot_sentinel.geo.json");
    }

    @Override
    public Identifier getTextureResource(RobotSentinelEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/robot_sentinel.png");
    }

    @Override
    public Identifier getAnimationResource(RobotSentinelEntity animatable) {
        return new Identifier(MOD_ID, "animations/entity/robot_sentinel.animation.json");
    }

    @Override
    public void applyMolangQueries(RobotSentinelEntity animatable, double animTime) {  // TODO: check if this is actually working
        super.applyMolangQueries(animatable, animTime);
        MolangParser parser = MolangParser.INSTANCE;
        parser.setMemoizedValue(VERTICAL_SPEED, () -> animatable.getVelocity().getY());
//        System.out.println(animatable.getVelocity().x + " " + animatable.getVelocity().y + " " + animatable.getVelocity().z);
//        System.out.println(parser.getVariable(MolangQueries.GROUND_SPEED).get());
//        System.out.println(parser.getVariable(VERTICAL_SPEED).get());
    }
}
