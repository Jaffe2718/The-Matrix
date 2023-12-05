package me.jaffe2718.the_matrix.client.render.entity;

import me.jaffe2718.the_matrix.client.model.entity.AgentModel;
import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AgentRenderer extends GeoEntityRenderer<AgentEntity> {
    public AgentRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AgentModel());
    }

}
