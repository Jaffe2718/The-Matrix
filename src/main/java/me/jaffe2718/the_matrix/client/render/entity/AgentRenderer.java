package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.AgentModel;
import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class AgentRenderer extends GeoEntityRenderer<AgentEntity> {
    public AgentRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AgentModel());
    }

    @Override
    public Identifier getTextureLocation(AgentEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/agent.png");
    }
}
