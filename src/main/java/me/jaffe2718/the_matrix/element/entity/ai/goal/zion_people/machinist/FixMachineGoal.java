package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.machinist;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;

public class FixMachineGoal extends Goal {

    private final ZionPeopleEntity machinist;
    private Path path;

    public FixMachineGoal(ZionPeopleEntity machinist) {
        this.machinist = machinist;
    }

    @Override
    public boolean canStart() {  // no enemy then start
        PathAwareEntity vehicle = this.machinist.getTargetVehicle();
        if (vehicle == null || vehicle.isDead()) {
            this.machinist.setFixing(false);
            return false;
        }
        this.path = this.machinist.getNavigation().findPathTo(vehicle, 2);
        return (this.machinist.getTarget() == null
                    || this.machinist.getTarget().isDead()
                    || !EntityRegistry.ROBOT_CLASSES.contains(this.machinist.getTarget().getClass()))  // no enemy
                && (vehicle.getHealth() < vehicle.getMaxHealth())    // has vehicle need to fix
                && this.path != null;
    }

    @Override
    public boolean canStop() {
        if (this.machinist.getTarget() != null && !EntityRegistry.ROBOT_CLASSES.contains(this.machinist.getTarget().getClass())) return true;
        PathAwareEntity vehicle = this.machinist.getTargetVehicle();
        return vehicle == null || vehicle.isDead() || vehicle.hasPassengers() || vehicle.getHealth() >= vehicle.getMaxHealth();
    }

    @Override
    public boolean shouldContinue() {
        return !this.canStop();
    }

    @Override
    public void start() {
        this.machinist.getNavigation().startMovingAlong(this.path, 1.0D);
    }

    @Override
    public void tick() {
        PathAwareEntity vehicle = this.machinist.getTargetVehicle();
        if (vehicle != null) {
            double d2 = this.machinist.squaredDistanceTo(vehicle);
            if (this.machinist.age % 20 == 0 && d2 > 4) {   // far away from the vehicle
                this.machinist.setFixing(false);
                this.path = this.machinist.getNavigation().findPathTo(vehicle, 2);
                this.machinist.getNavigation().startMovingAlong(this.path, 1.0D);
            } else if (d2 <= 4) {                // close to the vehicle
                // 1. face the vehicle
                this.machinist.getLookControl().lookAt(vehicle, 90.0F, 30.0F);
                this.machinist.getNavigation().stop();    // stand still
                // 2. fix the vehicle
                this.machinist.setFixing(true);
                vehicle.heal(1.0F);
                if (this.machinist.age % 20 == 0) {
                    this.machinist.playSound(SoundEventRegistry.SPANNER_TWIST, 1.0F, 1.0F);
                }
            }

        }
    }

    @Override
    public void stop() {
        this.machinist.setFixing(false);
        this.machinist.getNavigation().stop();
    }
}
