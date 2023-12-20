package me.jaffe2718.the_matrix.element.entity.ai.goal.machine_gun;

import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.MathUnit;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

/**
 * For
 * */
public class ShootEnemyGoal extends Goal {

    protected final MachineGunEntity machineGun;
    protected int stayNoEnemyTicks = 200;

    public ShootEnemyGoal(MachineGunEntity machineGun) {
        this.machineGun = machineGun;
    }

    @Override
    public boolean canStart() {
        if (this.machineGun.getTarget() == null) return false;
        return this.machineGun.getFirstPassenger() instanceof ZionPeopleEntity
                && this.machineGun.getTarget().isAlive()
                && EntityRegistry.ROBOT_CLASSES.contains(this.machineGun.getTarget().getClass());
    }

    private boolean checkEnemy() {
        LivingEntity enemy = this.machineGun.getTarget();
        if (enemy == null || !enemy.isAlive()) return false;
        return EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
    }

    @Override
    public boolean shouldContinue() {
        if (this.checkEnemy()) {
            this.machineGun.setAttacking(true);
            this.stayNoEnemyTicks = 200;
            return true;
        } else {
            this.machineGun.setAttacking(false);
            this.stayNoEnemyTicks--;
            return this.stayNoEnemyTicks > 0;
        }
    }

    @Override
    public void start() {
        this.machineGun.setAttacking(true);
    }

    @Override
    public void tick() {
        LivingEntity enemy = this.machineGun.getTarget();
        if (this.machineGun.age % 4 == 0 && enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass())) {
            // shot the enemy
            Vec3d eyePos = this.machineGun.getPos().add(0, this.machineGun.getDimensions(this.machineGun.getPose()).height, 0);
            double bulletTime = eyePos.distanceTo(enemy.getPos().add(0, enemy.getDimensions(enemy.getPose()).height / 2, 0)) / enemy.speed;
            // bullet speed = 12 blocks per tick
            Vec3d newPos = enemy.getPos().add(0, enemy.getDimensions(EntityPose.STANDING).height / 2, 0).add(enemy.getVelocity().multiply(bulletTime));
            Vec3d bulletVelocity = MathUnit.relativePos(eyePos, newPos).normalize().multiply(12);
            Vec3d muzzlePos = eyePos.add(bulletVelocity.normalize().multiply(2.0));
            BulletEntity.shoot(this.machineGun, muzzlePos, bulletVelocity);
            this.machineGun.playSound(SoundEventRegistry.MACHINE_GUN_SHOOT, 1.0F, 1.0F);
            // TODO: add particle effect
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        this.machineGun.setTarget(null);
        this.machineGun.setAttacking(false);
        if (this.machineGun.getFirstPassenger() instanceof ZionPeopleEntity rifleman) {
            rifleman.stopRiding();
        }
    }
}
