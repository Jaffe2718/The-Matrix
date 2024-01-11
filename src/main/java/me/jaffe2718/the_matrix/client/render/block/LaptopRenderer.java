package me.jaffe2718.the_matrix.client.render.block;

import me.jaffe2718.the_matrix.client.model.block.LaptopModel;
import me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@Environment(EnvType.CLIENT)
public class LaptopRenderer extends GeoBlockRenderer<LaptopBlockEntity> {
    public LaptopRenderer(BlockEntityRendererFactory.Context ctx) {
        super(new LaptopModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
