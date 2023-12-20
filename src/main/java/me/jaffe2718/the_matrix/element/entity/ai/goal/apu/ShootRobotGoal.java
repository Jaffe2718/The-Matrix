package me.jaffe2718.the_matrix.element.entity.ai.goal.apu;

import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.MathUnit;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

public class ShootRobotGoal extends Goal {
    protected final ArmoredPersonnelUnitEntity apu;
    protected int stayNoEnemyTicks = 200;

    public ShootRobotGoal(ArmoredPersonnelUnitEntity apu) {
        this.apu = apu;
    }
    @Override
    public boolean canStart() {
        if (this.apu.getTarget() == null) return false;
        return this.apu.getFirstPassenger() instanceof ZionPeopleEntity
                && this.apu.getTarget().isAlive()
                && EntityRegistry.ROBOT_CLASSES.contains(this.apu.getTarget().getClass());
    }

    private boolean checkEnemy() {
        LivingEntity enemy = this.apu.getTarget();
        if (enemy == null || !enemy.isAlive()) return false;
        this.apu.getMoveControl().moveTo(enemy.getX(), enemy.getY(), enemy.getZ(), 1.0);
        return EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
    }

    @Override
    public boolean shouldContinue() {
        if (this.checkEnemy()) {
            this.apu.setAttacking(true);
            this.stayNoEnemyTicks = 200;
            return true;
        } else {
            this.apu.setAttacking(false);
            this.stayNoEnemyTicks--;
            return this.stayNoEnemyTicks > 0;
        }
    }

    @Override
    public void start() {
        //this.apu.getNavigation().startMovingAlong(this.path, 1.0);
        this.apu.setAttacking(true);
    }

    @Override
    public void tick() {
        // LivingEntity enemy = this.apu.getTarget();
        LivingEntity enemy = this.apu.getTarget();
        if (this.apu.age % 4 == 0 && enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass())) {
            // shot the enemy
            Vec3d eyePos = this.apu.getPos().add(0, this.apu.getDimensions(EntityPose.STANDING).height - 1.6, 0);
            double bulletTime = eyePos.distanceTo(enemy.getPos().add(0, enemy.getDimensions(EntityPose.STANDING).height / 2, 0)) / enemy.speed;
            // bullet speed = 12 blocks per tick
            Vec3d newPos = enemy.getPos().add(0, enemy.getDimensions(EntityPose.STANDING).height / 2, 0).add(enemy.getVelocity().multiply(bulletTime));
            Vec3d bulletVelocity = MathUnit.relativePos(eyePos, newPos).normalize().multiply(12);
            Vec3d gunPos = eyePos.add(bulletVelocity.normalize().multiply(2.4));
            BulletEntity.shoot(this.apu, gunPos, bulletVelocity);
            this.apu.playSound(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_SHOOT, 1.0F, 1.0F);
            // TODO: add shoot particle

        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        this.apu.setTarget(null);
        this.apu.getNavigation().stop();
        this.apu.setAttacking(false);
        if (this.apu.getFirstPassenger() instanceof ZionPeopleEntity apuPilot) {
            apuPilot.stopRiding();
        }
    }
}
