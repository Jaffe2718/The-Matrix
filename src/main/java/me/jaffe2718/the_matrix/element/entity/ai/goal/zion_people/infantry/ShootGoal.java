package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.infantry;

import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.MathUnit;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.Vec3d;

public class ShootGoal extends Goal {

    private final ZionPeopleEntity infantry;
    private int shootCooldown = 100;    // max: 100 ticks

    private Path path;

    public ShootGoal(ZionPeopleEntity infantry) {
        this.infantry = infantry;
    }

    @Override
    public boolean canStart() {
        LivingEntity enemy = this.infantry.getTarget();
        boolean bl = enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
        if (bl) {
            if (this.shootCooldown > 20) {   // retreat
                Vec3d vec3d = NoPenaltyTargeting.findFrom(this.infantry, 96, 8, enemy.getPos());
                if (vec3d != null && (this.path == null || this.path.isFinished())) {
                    this.path = this.infantry.getNavigation().findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
                    this.infantry.getNavigation().startMovingAlong(this.path, 1.3D);
                }
            } else {
                this.infantry.getNavigation().stop();
                this.infantry.getLookControl().lookAt(enemy, 180.0F, 90.0F);
                this.infantry.getNavigation().startMovingTo(enemy, 0.5D);
            }
        }
        return bl;
    }

    @Override
    public boolean shouldContinue() {
        return this.canStart();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.infantry.setAttacking(true);
    }

    @Override
    public void tick() {
        LivingEntity enemy = this.infantry.getTarget();
        if (enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass()))
        {
            if (this.shootCooldown > 0) {
                this.shootCooldown--;
            } else if (this.infantry.canSee(enemy)) {
                // speed = 5 blocks per tick
                Vec3d enemyPos = enemy.getPos().add(0, enemy.getHeight() / 2.0, 0);
                Vec3d muzzlePos = this.infantry.getEyePos().add(this.infantry.getRotationVector());
                double time = enemyPos.distanceTo(muzzlePos) / 5.0;
                Vec3d enemyNextPos = enemyPos.add(enemy.getVelocity().multiply(time));
                Vec3d shootVelocity = MathUnit.relativePos(muzzlePos, enemyNextPos).normalize().multiply(5.0);
                ElectromagneticBulletEntity.shoot(this.infantry, muzzlePos, shootVelocity);
                this.infantry.playSound(SoundEventRegistry.ELECTROMAGNETIC_GUN_SHOOT, 1.0f, 1.0f);
                this.shootCooldown = 100;
            }
        }
    }

    @Override
    public void stop() {
        this.infantry.setAttacking(false);
        this.infantry.getNavigation().stop();
    }
}
