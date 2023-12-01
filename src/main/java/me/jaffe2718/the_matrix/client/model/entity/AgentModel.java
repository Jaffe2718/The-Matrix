package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class AgentModel extends GeoModel<AgentEntity> {

    @Override
    public Identifier getModelResource(AgentEntity animatable) {
        return new Identifier(MOD_ID, "geo/entity/agent.geo.json");
    }

    @Override
    public Identifier getTextureResource(AgentEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/agent.png");
    }

    @Override
    public Identifier getAnimationResource(AgentEntity animatable) {
        return new Identifier(MOD_ID, "animations/entity/agent.animation.json");
    }
}
