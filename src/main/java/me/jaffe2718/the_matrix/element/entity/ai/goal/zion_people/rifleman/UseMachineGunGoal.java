package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.rifleman;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;

/**
 * For rifleman -> to use a machine gun (ride it)
 * */
public class UseMachineGunGoal extends Goal {
    protected final ZionPeopleEntity rifleman;
    private Path path;

    public UseMachineGunGoal(ZionPeopleEntity rifleman) {
        this.rifleman = rifleman;
    }

    @Override
    public boolean canStart() {
        LivingEntity enemy = this.rifleman.getTarget();
        boolean checkEnemy = enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
        if (!checkEnemy) return false;   // if no enemy, don't select a machine gun
        PathAwareEntity targetMachineGun = this.rifleman.getTargetVehicle();
        return targetMachineGun != null && targetMachineGun.isAlive() && (!targetMachineGun.hasPassengers() || targetMachineGun.hasPassenger(this.rifleman));     // if no machine gun, don't select a machine gun
    }

    @Override
    public boolean shouldContinue() {
        return !this.canStop();
    }

    @Override
    public boolean canStop() {
        LivingEntity enemy = this.rifleman.getTarget();
        boolean noEnemy = enemy == null;
        if (enemy != null) {
            if (!enemy.isAlive()) noEnemy = true;
            else if (!EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass())) noEnemy = true;
            else {
                noEnemy = this.rifleman.distanceTo(enemy) < this.rifleman.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
            }
        }
        PathAwareEntity vehicle = this.rifleman.getTargetVehicle();
        boolean noMachineGun = vehicle == null;
        if (vehicle != null) {
            if (!vehicle.isAlive()) noMachineGun = true;
            else if (!vehicle.hasPassengers()) noMachineGun = true;
            else {
                noMachineGun = !vehicle.hasPassenger(this.rifleman);
            }
        }
        return noEnemy || noMachineGun;
    }

    @Override
    public void tick() {
        if (!this.rifleman.hasVehicle()) {
            if (this.rifleman.age % 20 == 0) this.path = this.rifleman.getNavigation().findPathTo(this.rifleman.getTargetVehicle(), 1);
            if (this.path != null) this.rifleman.getNavigation().startMovingAlong(this.path, 1.3);
            if (this.rifleman.squaredDistanceTo(this.rifleman.getTargetVehicle()) < 2) {
                this.rifleman.startRiding(this.rifleman.getTargetVehicle());
            }
        } else if (this.rifleman.getTarget() != null
                && this.rifleman.getVehicle() instanceof MachineGunEntity
                && this.rifleman.getTarget().isAlive()
                && EntityRegistry.ROBOT_CLASSES.contains(this.rifleman.getTarget().getClass())) {
            this.rifleman.getLookControl().lookAt(this.rifleman.getTarget(), 180.0F, 90.0F);
        }
    }

    @Override
    public void stop() {
        this.rifleman.getNavigation().stop();
    }
}
