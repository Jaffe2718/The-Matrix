package me.jaffe2718.the_matrix.client.render.armor;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.HackerBootsItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HackerBootsRenderer extends GeoArmorRenderer<HackerBootsItem> {

    // MODEL: resources/assets/the_matrix/geo/item/armor/hacker_boots.geo.json
    // TEXTURE: resources/assets/the_matrix/textures/item/armor/hacker_boots.png
    public HackerBootsRenderer() {
        super(new DefaultedItemGeoModel<>(TheMatrix.id("armor/hacker_boots")));
    }
}
