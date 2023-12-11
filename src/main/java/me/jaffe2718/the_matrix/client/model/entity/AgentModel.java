package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class AgentModel extends GeoModel<AgentEntity> {

    public static final RawAnimation IDLE = RawAnimation.begin().then("animation.agent.idle", Animation.LoopType.DEFAULT);
    public static final RawAnimation KICK = RawAnimation.begin().then("animation.agent.kick", Animation.LoopType.DEFAULT);
    public static final RawAnimation WALK = RawAnimation.begin().then("animation.agent.walk", Animation.LoopType.DEFAULT);
    public static final RawAnimation WAVE_FIST = RawAnimation.begin().then("animation.agent.wave_fist", Animation.LoopType.DEFAULT);

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
