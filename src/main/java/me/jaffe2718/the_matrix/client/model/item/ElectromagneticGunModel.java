package me.jaffe2718.the_matrix.client.model.item;

import me.jaffe2718.the_matrix.element.item.ElectromagneticGunItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class ElectromagneticGunModel extends GeoModel<ElectromagneticGunItem> {

    public static final RawAnimation POWER_INDICATOR = RawAnimation.begin().then("animation.electromagnetic_gun.power_indicator", Animation.LoopType.DEFAULT);
    private static final String ENERGY = "variable.energy";   // MoLang variable for energy

    @Override
    public Identifier getModelResource(ElectromagneticGunItem animatable) {
        return new Identifier(MOD_ID, "geo/item/electromagnetic_gun.geo.json");
    }

    @Override
    public Identifier getTextureResource(ElectromagneticGunItem animatable) {
        return new Identifier(MOD_ID, "textures/item/electromagnetic_gun.png");
    }

    @Override
    public Identifier getAnimationResource(ElectromagneticGunItem animatable) {
        return new Identifier(MOD_ID, "animations/item/electromagnetic_gun.animation.json");
    }

    @Override
    public void applyMolangQueries(ElectromagneticGunItem animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);
        MolangParser parser = MolangParser.INSTANCE;
        parser.setMemoizedValue(ENERGY, animatable::getEnergy);
    }
}
