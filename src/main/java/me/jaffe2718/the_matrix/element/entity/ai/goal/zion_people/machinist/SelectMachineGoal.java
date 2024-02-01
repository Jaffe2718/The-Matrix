package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.machinist;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.SpaceshipEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SelectMachineGoal extends TrackTargetGoal {

    @Nullable
    protected PathAwareEntity targetMachine;

    public SelectMachineGoal(ZionPeopleEntity machinist) {
        super(machinist, false);
    }

    @Override
    public boolean canStart() {
        LivingEntity enemy = this.mob.getTarget();
        this.selectClosestMachine();
        return enemy == null
                || enemy.isDead()
                || !EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
    }

    protected void selectClosestMachine() {
        List<PathAwareEntity> allMachines = this.mob.getWorld().getEntitiesByClass(
                PathAwareEntity.class,
                this.getSearchBox(this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                machine -> (machine instanceof ArmoredPersonnelUnitEntity
                            || machine instanceof MachineGunEntity
                            || machine instanceof SpaceshipEntity)
                        && machine.isAlive()
                        && !machine.hasPassengers()
                        && machine.getHealth() < machine.getMaxHealth()
        );
        PathAwareEntity closestMachine = null;
        double minD2 = Double.MAX_VALUE;
        for (PathAwareEntity machine : allMachines) {
            double d2 = this.mob.squaredDistanceTo(machine);
            if (d2 < minD2) {
                closestMachine = machine;
                minD2 = d2;
            }
        }
        this.targetMachine = closestMachine;
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0D, distance);
    }

    @Override
    public void start() {
        if (this.mob instanceof ZionPeopleEntity zionPeople && this.targetMachine != null) {
            zionPeople.setTargetVehicle(this.targetMachine);
        }
        super.start();
    }
}
