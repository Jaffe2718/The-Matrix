package me.jaffe2718.the_matrix.client;

import me.jaffe2718.the_matrix.client.render.entity.*;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.KeyBindings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class TheMatrixClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        KeyBindings.register();
        EntityRendererRegistry.register(EntityRegistry.AGENT, AgentRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ARMORED_PERSONAL_UNIT, ArmoredPersonnelUnitRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.BULLET, BulletRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ROBOT_SENTINEL, RobotSentinelRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ZION_PEOPLE, ZionPeopleRenderer::new);
    }
}
