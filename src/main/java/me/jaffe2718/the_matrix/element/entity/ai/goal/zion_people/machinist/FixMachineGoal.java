package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.machinist;

import me.jaffe2718.the_matrix.TheMatrix;
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
        this.path = this.machinist.getNavigation().findPathTo(vehicle, 0);
        return (this.machinist.getTarget() == null
                    || this.machinist.getTarget().isDead()
                    || !EntityRegistry.ROBOT_CLASSES.contains(this.machinist.getTarget().getClass()))  // no enemy
                && (vehicle.getHealth() < vehicle.getMaxHealth())    // has vehicle need to fix
                && (this.path != null || this.machinist.squaredDistanceTo(vehicle) <= 4);  // can reach the vehicle
    }

    @Override
    public boolean shouldContinue() {
        if (!this.machinist.isFixing() && (this.path.isFinished() || this.path == null)) {
            // System.out.println("re-path");
            this.path = this.machinist.getNavigation().findPathTo(this.machinist.getTargetVehicle(), 0);
            this.machinist.getNavigation().startMovingAlong(this.path, 1.0D);
        }
        if (this.machinist.getTarget() != null           // cannot fix when enemy exists
                && !this.machinist.getTarget().isDead()
                && !EntityRegistry.ROBOT_CLASSES.contains(this.machinist.getTarget().getClass())) return false;
        PathAwareEntity vehicle = this.machinist.getTargetVehicle();
        return vehicle != null && vehicle.isAlive() && !vehicle.hasPassengers() && vehicle.getHealth() < vehicle.getMaxHealth();
    }

    @Override
    public void start() {
        if (this.path != null && !this.path.isFinished() && this.machinist.getTargetVehicle() != null) {
            this.machinist.getNavigation().startMovingAlong(this.path, 1.0D);
            this.machinist.setFixing(false);
            TheMatrix.LOGGER.info("Machinist start moving to fix the " + this.machinist.getTargetVehicle());
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        PathAwareEntity vehicle = this.machinist.getTargetVehicle();
        if (vehicle != null) {
            this.machinist.getLookControl().lookAt(vehicle, 180.0F, 30.0F);
            double d2 = this.machinist.squaredDistanceTo(vehicle);
            if (d2 > (vehicle.getWidth() * vehicle.getWidth() / 4 + 4)) {   // far away from the vehicle
                this.machinist.setFixing(false);
            } else {   // close to the vehicle
                // fix the vehicle
                // this.machinist.getNavigation().stop();
                this.machinist.setFixing(true);
                if (this.machinist.age % 20 == 0) {
                    vehicle.heal(1F);
                    this.machinist.playSound(SoundEventRegistry.SPANNER_TWIST, 1.0F, 1.0F);
                }
            }
        }
    }

    @Override
    public void stop() {
        this.machinist.setFixing(false);
        this.machinist.setTargetVehicle(null);
        this.machinist.getNavigation().stop();
        TheMatrix.LOGGER.info("Machinist stop fixing");
    }
}
