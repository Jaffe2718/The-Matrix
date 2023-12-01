package me.jaffe2718.the_matrix.client.render.armor;

import me.jaffe2718.the_matrix.element.item.VMaskItem;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;


@Environment(net.fabricmc.api.EnvType.CLIENT)
public class VMaskRenderer extends GeoArmorRenderer<VMaskItem> {
    public VMaskRenderer() {
        // MODEL: resources/assets/the_matrix/geo/item/models/armor/v_mask.geo.json
        // TEXTURE: resources/assets/the_matrix/textures/item/models/armor/v_mask.png
        super(new DefaultedItemGeoModel<>(new Identifier(MOD_ID, "models/armor/v_mask")));
    }
}
