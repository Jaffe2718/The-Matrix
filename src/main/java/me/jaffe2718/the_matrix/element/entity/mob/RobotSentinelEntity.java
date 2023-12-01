package me.jaffe2718.the_matrix.element.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RobotSentinelEntity extends FlyingEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public RobotSentinelEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO: register controllers
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
