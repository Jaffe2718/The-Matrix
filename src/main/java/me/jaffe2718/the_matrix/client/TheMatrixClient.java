package me.jaffe2718.the_matrix.client;

import me.jaffe2718.the_matrix.client.render.block.entity.*;
import me.jaffe2718.the_matrix.client.render.entity.*;
import me.jaffe2718.the_matrix.element.particle.BulletShellParticle;
import me.jaffe2718.the_matrix.element.particle.HealParticle;
import me.jaffe2718.the_matrix.element.particle.MobilePhone01Particle;
import me.jaffe2718.the_matrix.unit.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class TheMatrixClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        KeyBindings.register();
        BlockEntityRendererFactories.register(BlockRegistry.ENGINE_CORE_BLOCK_ENTITY, EngineCoreRenderer::new);
        BlockEntityRendererFactories.register(BlockRegistry.LAPTOP_BLOCK_ENTITY, LaptopRenderer::new);
        BlockEntityRendererFactories.register(BlockRegistry.TELEPORTER_BLOCK_ENTITY, PlasmaEmitterBlockEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.VENDING_MACHINE, RenderLayer.getCutout());
        EntityRendererRegistry.register(EntityRegistry.AGENT, AgentRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ARMORED_PERSONAL_UNIT, ArmoredPersonnelUnitRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.BULLET, BulletRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.DIGGER_ROBOT, DiggerRobotRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ELECTROMAGNETIC_BULLET, ElectromagneticBulletRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MACHINE_GUN, MachineGunRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ROBOT_SENTINEL, RobotSentinelRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SPACESHIP, SpaceshipRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ZION_PEOPLE, ZionPeopleRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.BULLET_SHELL, BulletShellParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.HEAL, HealParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.MOBILE_PHONE_01, MobilePhone01Particle.Factory::new);
        ModelPredicateInit.register();
    }
}
