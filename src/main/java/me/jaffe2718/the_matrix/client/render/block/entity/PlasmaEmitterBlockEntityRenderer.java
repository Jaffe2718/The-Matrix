package me.jaffe2718.the_matrix.client.render.block.entity;

import me.jaffe2718.the_matrix.element.block.entity.PlasmaEmitterBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class PlasmaEmitterBlockEntityRenderer implements BlockEntityRenderer<PlasmaEmitterBlockEntity> {
    private static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/beacon_beam.png");

    @Contract(pure = true)
    public PlasmaEmitterBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(@NotNull PlasmaEmitterBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        long worldTime = entity.getWorld() == null ? 0 : entity.getWorld().getTime();
        BeaconBlockEntityRenderer.renderBeam(matrices, vertexConsumers, BEAM_TEXTURE,
                tickDelta, 1.0F, worldTime, 1, 384,
                new float[] {0.3764705882352941F, 0.8392156862745098F, 1.0F}, 0.15F, 0.175F);
    }
}
