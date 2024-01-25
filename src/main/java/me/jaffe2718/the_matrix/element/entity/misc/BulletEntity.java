package me.jaffe2718.the_matrix.element.entity.misc;

import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BulletEntity
        extends ArrowEntity
        implements GeoEntity {

    private static final float BULLET_DAMAGE = 18.0F;

    public static void shoot(@NotNull Entity owner, Vec3d pos, Vec3d velocity) {
        BulletEntity bullet = EntityRegistry.BULLET.create(owner.getWorld());//new BulletEntity(EntityRegistry.BULLET, owner.getWorld());
        if (bullet != null) {
            bullet.setOwner(owner);
            bullet.setPosition(pos);
            bullet.setVelocity(velocity);
            owner.getWorld().spawnEntity(bullet);
        }
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public BulletEntity(EntityType<? extends BulletEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(false);
    }

    @Override
    protected void onEntityHit(@NotNull EntityHitResult entityHitResult) {
        // super.onEntityHit(entityHitResult);
        Entity target = entityHitResult.getEntity();
        target.damage(this.getDamageSources().arrow(this, this.getOwner()), BULLET_DAMAGE);
//        Vec3d groundPunchVelocity = this.getVelocity().multiply(1, 0, 1).normalize().multiply(0.3);
//        target.addVelocity(groundPunchVelocity.x, 0.1, groundPunchVelocity.z);
        this.playSound(SoundEventRegistry.BULLET_HITTING_ENTITY, 1.0F, 1.0F);
        this.discard();
    }

    @Override
    protected void onBlockHit(@NotNull BlockHitResult blockHitResult) {
        BlockState blockState = this.getWorld().getBlockState(blockHitResult.getBlockPos());
        blockState.onProjectileHit(this.getWorld(), blockState, blockHitResult, this);
        this.playSound(SoundEventRegistry.BULLET_HITTING_BLOCK, 1.0F, 1.0F);
        // generate particle
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d hitPos = blockHitResult.getPos();
            serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), hitPos.getX(), hitPos.getY(), hitPos.getZ(), 8, 0.5, 0.5, 0.5, 0.0);
        }
        this.discard();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
