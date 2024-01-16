package me.jaffe2718.the_matrix.client.render.armor;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.item.MechanicalArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class MechanicalArmorRenderer extends GeoArmorRenderer<MechanicalArmorItem> {

    public MechanicalArmorRenderer() {
        // MODEL: resources/assets/the_matrix/geo/item/armor/mechanical_armor.geo.json
        // TEXTURE: resources/assets/the_matrix/textures/item/armor/mechanical_armor.png
        // GLOWMASK: resources/assets/the_matrix/textures/item/armor/mechanical_armor_glowmask.png
        // ANIMATION: resources/assets/the_matrix/animation/item/armor/mechanical_armor.animation.json
        super(new DefaultedItemGeoModel<>(TheMatrix.id("armor/mechanical_armor")));
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
