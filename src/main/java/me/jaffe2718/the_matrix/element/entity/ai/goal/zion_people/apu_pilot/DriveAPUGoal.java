package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
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
    protected final ZionPeopleEntity mob;
    private Path path;

    public DriveAPUGoal(ZionPeopleEntity mob) {
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
            this.path = this.mob.getNavigation().findPathTo(targetAPU, 0);
        }
        return false;
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
        return noEnemy || noAPU;
    }

    @Override
    public void stop() {
        if (this.mob.hasVehicle()) {
            this.mob.stopRiding();
        }
        this.mob.getNavigation().stop();
    }
}
