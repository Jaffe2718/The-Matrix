package me.jaffe2718.the_matrix.client.render.armor;

import me.jaffe2718.the_matrix.element.item.HackerBootsItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class HackerBootsRenderer extends GeoArmorRenderer<HackerBootsItem> {

    // MODEL: resources/assets/the_matrix/geo/item/armor/hacker_boots.geo.json
    // TEXTURE: resources/assets/the_matrix/textures/item/armor/hacker_boots.png
    public HackerBootsRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(MOD_ID, "armor/hacker_boots")));
    }
}
