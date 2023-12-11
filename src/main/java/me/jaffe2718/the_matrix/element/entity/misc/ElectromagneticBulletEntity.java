package me.jaffe2718.the_matrix.element.entity.misc;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import me.jaffe2718.the_matrix.network.packet.s2c.play.ElectromagneticBulletEntitySpawnS2CPacket;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
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

import java.util.List;

import static me.jaffe2718.the_matrix.client.model.entity.ElectromagneticBulletModel.COMMON;
import static me.jaffe2718.the_matrix.client.model.entity.ElectromagneticBulletModel.EXPLOSION;

public class ElectromagneticBulletEntity
        extends ProjectileEntity
        implements GeoEntity {

    /**
     * Only robots will be affected by the electromagnetic pulse.
     * */
    private static final List<Class<? extends LivingEntity>> ROBOT_CLASSES = List.of(  // TODO: add more robot classes
            RobotSentinelEntity.class);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int explosionDuration = 5;   // max = 5, min = 0, destroy when 0
    private boolean isExploding = false;

    public static void shoot(@NotNull Entity owner, Vec3d pos, Vec3d velocity) {
        ElectromagneticBulletEntity bullet = new ElectromagneticBulletEntity(EntityRegistry.ELECTROMAGNETIC_BULLET, owner.getWorld());
        bullet.setOwner(owner);
        bullet.setPosition(pos);
        bullet.setVelocity(velocity);
        owner.getWorld().spawnEntity(bullet);
    }

    public ElectromagneticBulletEntity(EntityType<? extends ElectromagneticBulletEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.explosionDuration = nbt.getInt("ExplosionDuration");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("ExplosionDuration", this.explosionDuration);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new ElectromagneticBulletEntitySpawnS2CPacket(this);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        if (packet instanceof ElectromagneticBulletEntitySpawnS2CPacket ePacket) {
            this.explosionDuration = ePacket.getExplosionDuration();
            this.isExploding = ePacket.isExploding();
        }
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.isExploding) {
            this.explosionDuration--;
            if (this.explosionDuration == 0) {
                this.destroy();
            }
        }
    }

    @Override
    protected void onCollision(@NotNull HitResult hitResult) {
        super.onCollision(hitResult);
        this.setVelocity(0, 0, 0);  // stop moving when hit & explode
        if (!this.isExploding) {   // start exploding
            Box box = this.getBoundingBox().expand(4);
            DamageSource explosion = this.getDamageSources().explosion(this, this.getOwner());
            for (LivingEntity entity : this.getWorld().getEntitiesByClass(LivingEntity.class, box, entity -> ROBOT_CLASSES.contains(entity.getClass()))) {
                entity.damage(explosion, 80.0F);
            }
            this.isExploding = true;
        }
    }

    private void destroy() {
        this.discard();
        this.getWorld().emitGameEvent(GameEvent.ENTITY_DAMAGE, this.getPos(), GameEvent.Emitter.of(this));
    }

    public int getExplosionDuration() {
        return explosionDuration;
    }

    public boolean isExploding() {
        return this.isExploding;
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
