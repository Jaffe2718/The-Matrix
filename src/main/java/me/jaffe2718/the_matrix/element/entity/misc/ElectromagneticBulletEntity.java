package me.jaffe2718.the_matrix.element.entity.misc;

import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import static me.jaffe2718.the_matrix.client.model.entity.ElectromagneticBulletModel.COMMON;
import static me.jaffe2718.the_matrix.client.model.entity.ElectromagneticBulletModel.EXPLOSION;

public class ElectromagneticBulletEntity
        extends ArrowEntity
        implements GeoEntity {

    private static final TrackedData<Integer> EXPLOSION_DURATION = DataTracker.registerData(ElectromagneticBulletEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IS_EXPLODING = DataTracker.registerData(ElectromagneticBulletEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static void shoot(@NotNull Entity owner, Vec3d pos, Vec3d velocity) {
        ElectromagneticBulletEntity bullet = new ElectromagneticBulletEntity(EntityRegistry.ELECTROMAGNETIC_BULLET, owner.getWorld());
        bullet.setOwner(owner);
        bullet.setPosition(pos);
        bullet.setVelocity(velocity);
        owner.getWorld().spawnEntity(bullet);
    }

    public ElectromagneticBulletEntity(EntityType<? extends ElectromagneticBulletEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(EXPLOSION_DURATION, 80);
        this.dataTracker.startTracking(IS_EXPLODING, false);
    }

    private int getExplosionDuration() {
        return this.dataTracker.get(EXPLOSION_DURATION);
    }

    private void decreaseExplosionDuration() {
        this.dataTracker.set(EXPLOSION_DURATION, this.getExplosionDuration() - 1);
    }

    private boolean isExploding() {
        return this.dataTracker.get(IS_EXPLODING);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(EXPLOSION_DURATION, nbt.getInt("ExplosionDuration"));
        this.dataTracker.set(IS_EXPLODING, this.getExplosionDuration() < 80);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("ExplosionDuration", this.getExplosionDuration());
    }

    @Override
    protected boolean canHit(Entity entity) {
        return super.canHit(entity) && !this.isExploding() && !this.isOwner(entity);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isExploding()) {
            this.decreaseExplosionDuration();
            if (this.getExplosionDuration() == 0) {
                this.destroy();
            } else if (this.getExplosionDuration() % 16 == 0) {
                this.applyExplosion();
            }
        }
    }

    @Override
    protected void onCollision(@NotNull HitResult hitResult) {
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockState blockState = this.getWorld().getBlockState(blockHitResult.getBlockPos());
            if (!blockState.isSolidBlock(this.getWorld(), blockHitResult.getBlockPos()) || blockState.isAir()) {
                return;
            }
        }
        super.onCollision(hitResult);
        this.setVelocity(0, 0, 0);  // stop moving when hit & explode
        this.setPosition(hitResult.getPos());
        this.dataTracker.set(IS_EXPLODING, true);
        this.applyExplosion();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
    }

    @Override
    protected void onBlockHit(@NotNull BlockHitResult blockHitResult) {
        BlockState blockState = this.getWorld().getBlockState(blockHitResult.getBlockPos());
        blockState.onProjectileHit(this.getWorld(), blockState, blockHitResult, this);
    }

    @Override
    public boolean shouldRender(double distanceSquared) {
        return distanceSquared < 65536;
    }

    private void destroy() {
        try {
            this.getWorld().emitGameEvent(GameEvent.ENTITY_DAMAGE, this.getPos(), GameEvent.Emitter.of(this));
            this.discard();
        } catch (Exception ignored) {}
    }

    /**
     * Explode and damage all robots in a 4-block radius.
     * */
    private void applyExplosion() {
        Box box = this.getBoundingBox().expand(4);
        DamageSource explosion = this.getDamageSources().explosion(this, this.getOwner());
        for (LivingEntity entity : this.getWorld().getEntitiesByClass(LivingEntity.class, box, entity -> EntityRegistry.ROBOT_CLASSES.contains(entity.getClass()))) {
            entity.damage(explosion, 30 + this.getExplosionDuration() / 2.0F);
        }
        if (!this.getWorld().isClient) {
            this.playSound(SoundEventRegistry.ELECTROMAGNETIC_EXPLOSION, 1, 1);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<ElectromagneticBulletEntity> state) {
        if (this.isExploding()) {
            return state.setAndContinue(EXPLOSION);
        } else {
            return state.setAndContinue(COMMON);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
