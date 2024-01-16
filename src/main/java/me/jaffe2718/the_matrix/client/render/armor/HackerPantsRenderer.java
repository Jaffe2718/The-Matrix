package me.jaffe2718.the_matrix.client.render.armor;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.HackerPantsItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HackerPantsRenderer extends GeoArmorRenderer<HackerPantsItem> {
    public HackerPantsRenderer() {
        // MODEL: resources/assets/the_matrix/geo/item/armor/hacker_pants.geo.json
        // TEXTURE: resources/assets/the_matrix/textures/item/armor/hacker_pants.png
        super(new DefaultedItemGeoModel<>(TheMatrix.id("armor/hacker_pants")));
    }
}
