package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;

public class ElectromagneticBulletModel extends GeoModel<ElectromagneticBulletEntity> {
    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.electromagnetic_bullet.common", Animation.LoopType.DEFAULT);
    public static final RawAnimation EXPLOSION = RawAnimation.begin().then("animation.electromagnetic_bullet.explosion", Animation.LoopType.DEFAULT);

    @Override
    public Identifier getModelResource(ElectromagneticBulletEntity animatable) {
        return TheMatrix.id("geo/entity/electromagnetic_bullet.geo.json");
    }

    @Override
    public Identifier getTextureResource(ElectromagneticBulletEntity animatable) {
        return TheMatrix.id("textures/entity/electromagnetic_bullet.png");
    }

    @Override
    public Identifier getAnimationResource(ElectromagneticBulletEntity animatable) {
        return TheMatrix.id("animations/entity/electromagnetic_bullet.animation.json");
    }
}
