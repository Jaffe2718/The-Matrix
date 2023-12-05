package me.jaffe2718.the_matrix.client;

import me.jaffe2718.the_matrix.client.render.entity.AgentRenderer;
import me.jaffe2718.the_matrix.client.render.entity.ArmoredPersonnelUnitRenderer;
import me.jaffe2718.the_matrix.client.render.entity.RobotSentinelRenderer;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class TheMatrixClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.AGENT, AgentRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ARMORED_PERSONAL_UNIT, ArmoredPersonnelUnitRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ROBOT_SENTINEL, RobotSentinelRenderer::new);
    }
}
