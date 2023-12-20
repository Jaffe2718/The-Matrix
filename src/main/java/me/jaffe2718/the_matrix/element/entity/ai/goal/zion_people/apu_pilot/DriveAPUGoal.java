package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;

/**
 * For apu_pilot -> approach an apu and start driving it
 * */
public class DriveAPUGoal extends Goal {
    protected final ZionPeopleEntity apuPilot;
    private Path path;

    public DriveAPUGoal(ZionPeopleEntity apuPilot) {
        this.apuPilot = apuPilot;
    }
    @Override
    public boolean canStart() {
        LivingEntity enemy = this.apuPilot.getTarget();
        boolean checkEnemy = enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
        if (!checkEnemy) return false;   // if no enemy, don't select an apu
        PathAwareEntity targetAPU = this.apuPilot.getTargetVehicle();
        boolean checkAPU = targetAPU != null && targetAPU.isAlive() && (!targetAPU.hasPassengers() || targetAPU.hasPassenger(this.apuPilot));
        if (!checkAPU) return false;     // if no apu, don't select an apu
        if (!this.apuPilot.hasVehicle()) {
            this.path = this.apuPilot.getNavigation().findPathTo(targetAPU, 2);
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
        LivingEntity enemy = this.apuPilot.getTarget();
        boolean noEnemy = enemy == null;
        if (enemy != null) {
            if (!enemy.isAlive()) noEnemy = true;
            else if (!EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass())) noEnemy = true;
            else {
                noEnemy = this.apuPilot.distanceTo(enemy) < this.apuPilot.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            }
        }
        PathAwareEntity vehicle = this.apuPilot.getTargetVehicle();
        boolean noAPU = vehicle == null;
        if (vehicle != null) {
            if (!vehicle.isAlive()) noAPU = true;
            else if (vehicle.hasPassengers() && !vehicle.hasPassenger(this.apuPilot)) noAPU = true;
            else {
                noAPU = this.apuPilot.distanceTo(vehicle) < this.apuPilot.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            }
        }
        // System.out.println("StartDrivingAPUGoalStartDrivingAPUGoal canStop: " + noEnemy + " " + noAPU);
        return noEnemy || noAPU;
    }

    @Override
    public void tick() {
        if (!this.apuPilot.hasVehicle()) {
            if (this.apuPilot.age % 20 == 0) this.path = this.apuPilot.getNavigation().findPathTo(this.apuPilot.getTargetVehicle(), 2);
            this.apuPilot.getNavigation().startMovingAlong(this.path, 1.3);
            if (this.apuPilot.squaredDistanceTo(this.apuPilot.getTargetVehicle()) < 4.0) {
                this.apuPilot.startRiding(this.apuPilot.getTargetVehicle());
            }
        } else if (this.apuPilot.getTarget() != null
                && this.apuPilot.getVehicle() instanceof ArmoredPersonnelUnitEntity
                && this.apuPilot.getTarget().isAlive()
                && EntityRegistry.ROBOT_CLASSES.contains(this.apuPilot.getTarget().getClass())) {
            this.apuPilot.getLookControl().lookAt(this.apuPilot.getTarget(), 180.0F, 90.0F);
//            if (this.apuPilot.age % 4 == 0) {
//                BulletEntity.shoot(this.apuPilot.getVehicle(),
//                        this.apuPilot.getEyePos().add(this.apuPilot.getRotationVector().multiply(2.4)),
//                        MathUnit.relativePos(this.apuPilot.getEyePos(), this.apuPilot.getTarget().getEyePos()).normalize().multiply(12.0));
//                this.apuPilot.getVehicle().playSound(SoundEventRegistry.ARMORED_PERSONNEL_UNIT_SHOOT, 1.0F, 1.0F);
//            }
        }
    }

    @Override
    public void stop() {
        this.apuPilot.getNavigation().stop();
    }
}
