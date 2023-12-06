package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class BulletModel extends GeoModel<BulletEntity> {
    @Override
    public Identifier getModelResource(BulletEntity animatable) {
        return new Identifier(MOD_ID, "geo/entity/bullet.geo.json");
    }

    @Override
    public Identifier getTextureResource(BulletEntity animatable) {
        return new Identifier(MOD_ID, "textures/entity/bullet.png");
    }

    @Override
    public Identifier getAnimationResource(BulletEntity animatable) {
        return null;
    }
}
