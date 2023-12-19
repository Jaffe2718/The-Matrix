package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot;

import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.MathUnit;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;

/**
 * For apu_pilot -> approach an apu and start driving it
 * */
public class StartDrivingAPUGoal extends Goal {
    protected final ZionPeopleEntity mob;
    private Path path;

    public StartDrivingAPUGoal(ZionPeopleEntity mob) {
        this.mob = mob;
    }
    @Override
    public boolean canStart() {
        LivingEntity enemy = this.mob.getTarget();
        boolean checkEnemy = enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
        if (!checkEnemy) return false;   // if no enemy, don't select an apu
        PathAwareEntity targetAPU = this.mob.getTargetVehicle();
        boolean checkAPU = targetAPU != null && targetAPU.isAlive() && (!targetAPU.hasPassengers() || targetAPU.hasPassenger(this.mob));
        if (!checkAPU) return false;     // if no apu, don't select an apu
        if (!this.mob.hasVehicle()) {
            this.path = this.mob.getNavigation().findPathTo(targetAPU, 2);
        } else {
            this.path = null;
        }
        return this.path != null;
    }

    @Override
    public boolean shouldContinue() {
        return !this.canStop();
    }

    @Override
    public boolean canStop() {
        LivingEntity enemy = this.mob.getTarget();
        boolean noEnemy = enemy == null;
        if (enemy != null) {
            if (!enemy.isAlive()) noEnemy = true;
            else if (!EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass())) noEnemy = true;
            else {
                noEnemy = this.mob.distanceTo(enemy) < this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            }
        }
        PathAwareEntity vehicle = this.mob.getTargetVehicle();
        boolean noAPU = vehicle == null;
        if (vehicle != null) {
            if (!vehicle.isAlive()) noAPU = true;
            else if (vehicle.hasPassengers() && !vehicle.hasPassenger(this.mob)) noAPU = true;
            else {
                noAPU = this.mob.distanceTo(vehicle) < this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            }
        }
        // System.out.println("StartDrivingAPUGoalStartDrivingAPUGoal canStop: " + noEnemy + " " + noAPU);
        return noEnemy || noAPU;
    }

    @Override
    public void tick() {
        if (!this.mob.hasVehicle()) {
            if (this.mob.age % 20 == 0) this.path = this.mob.getNavigation().findPathTo(this.mob.getTargetVehicle(), 2);
            this.mob.getNavigation().startMovingAlong(this.path, 1.3);
            if (this.mob.distanceTo(this.mob.getTargetVehicle()) < 2.0) {
                this.mob.startRiding(this.mob.getTargetVehicle());
            }
        } else if (this.mob.getTarget() != null
                && this.mob.getVehicle() instanceof ArmoredPersonnelUnitEntity
                && this.mob.getTarget().isAlive()
                && EntityRegistry.ROBOT_CLASSES.contains(this.mob.getTarget().getClass())) {
            this.mob.getLookControl().lookAt(this.mob.getTarget(), 180.0F, 60.0F);
            if (this.mob.age % 4 == 0) {
                BulletEntity.shoot(this.mob.getVehicle(),
                        this.mob.getEyePos().add(this.mob.getRotationVector().multiply(2.4)),
                        MathUnit.relativePos(this.mob.getEyePos(), this.mob.getTarget().getEyePos()).normalize().multiply(12.0));
                this.mob.getVehicle().playSound(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_SHOOT, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }
}
