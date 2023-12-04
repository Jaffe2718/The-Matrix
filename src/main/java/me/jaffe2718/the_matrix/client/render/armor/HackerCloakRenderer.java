package me.jaffe2718.the_matrix.client.render.armor;

import me.jaffe2718.the_matrix.element.item.HackerCloakItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

@Environment(EnvType.CLIENT)
public class HackerCloakRenderer extends GeoArmorRenderer<HackerCloakItem> {

    // MODEL: resources/assets/the_matrix/geo/item/armor/hacker_cloak.geo.json
    // TEXTURE: resources/assets/the_matrix/textures/item/armor/hacker_cloak.png
    public HackerCloakRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(MOD_ID, "armor/hacker_cloak")));
    }
}
