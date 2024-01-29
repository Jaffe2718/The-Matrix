package me.jaffe2718.the_matrix.client.render.block.entity;

import me.jaffe2718.the_matrix.client.model.block.EngineCoreModel;
import me.jaffe2718.the_matrix.element.block.entity.EngineCoreBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@Environment(EnvType.CLIENT)
public class EngineCoreRenderer extends GeoBlockRenderer<EngineCoreBlockEntity> {

    public EngineCoreRenderer(BlockEntityRendererFactory.Context ignoredCtx) {
        super(new EngineCoreModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
