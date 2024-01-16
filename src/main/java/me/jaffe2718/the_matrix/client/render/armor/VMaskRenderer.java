package me.jaffe2718.the_matrix.client.render.armor;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.VMaskItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;


@Environment(EnvType.CLIENT)
public class VMaskRenderer extends GeoArmorRenderer<VMaskItem> {
    public VMaskRenderer() {
        // MODEL: resources/assets/the_matrix/geo/item/armor/v_mask.geo.json
        // TEXTURE: resources/assets/the_matrix/textures/item/armor/v_mask.png
        super(new DefaultedItemGeoModel<>(TheMatrix.id("armor/v_mask")));
    }
}
