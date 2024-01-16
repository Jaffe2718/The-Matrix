package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public class MachineGunModel extends GeoModel<MachineGunEntity> {

    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.machine_gun.common", Animation.LoopType.DEFAULT);

    private static final String GUN_PITCH = "variable.gun_pitch";  // register gun_pitch variable to molang parser
    private static final String IS_SHOOTING = "variable.is_shooting";

    @Override
    public Identifier getModelResource(MachineGunEntity animatable) {
        return TheMatrix.id("geo/entity/machine_gun.geo.json");
    }

    @Override
    public Identifier getTextureResource(MachineGunEntity animatable) {
        return TheMatrix.id("textures/entity/machine_gun.png");
    }

    @Override
    public Identifier getAnimationResource(MachineGunEntity animatable) {
        return TheMatrix.id("animations/entity/machine_gun.animation.json");
    }

    @Override
    public void applyMolangQueries(MachineGunEntity animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);
        MolangParser parser = MolangParser.INSTANCE;
        parser.setMemoizedValue(GUN_PITCH, animatable::getPitch);
        parser.setMemoizedValue(IS_SHOOTING, () -> animatable.isShooting() ? 1 : 0);
    }
}
