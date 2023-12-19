package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu_pilot;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static me.jaffe2718.the_matrix.unit.EntityRegistry.ROBOT_CLASSES;

/**
 * For apu_pilot -> to drive an apu
 * */
public class SelectAPUGoal extends TrackTargetGoal {
    @Nullable
    protected ArmoredPersonnelUnitEntity targetAPU;

    public SelectAPUGoal(ZionPeopleEntity driver) {
        super(driver, false);
    }

    @Override
    public boolean canStart() {
        LivingEntity enemy = this.mob.getTarget();
        this.selectClosestAPU();
        boolean hasTargetAPU = false;
        if (this.mob instanceof ZionPeopleEntity zionPeople) {
            hasTargetAPU = zionPeople.getTargetVehicle() != null;
        }
        return !this.mob.hasVehicle()  // if already driving a vehicle, don't select another one
                && !hasTargetAPU       // if already selected an apu, don't select another one
                && enemy != null       // if no enemy, don't select an apu
                && enemy.isAlive()     // if enemy is dead, don't select an apu
                && ROBOT_CLASSES.contains(enemy.getClass());   // enemy must be a robot
    }

    protected void selectClosestAPU() {
        if (this.mob.getVehicle() instanceof ArmoredPersonnelUnitEntity apu) {
            this.targetAPU = apu;
        } else {
            List<ArmoredPersonnelUnitEntity> allAPUs = this.mob.getWorld().getEntitiesByClass(
                    ArmoredPersonnelUnitEntity.class,
                    this.getSearchBox(this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                    apu -> apu.isAlive() && !apu.hasPassengers()
            );
            ArmoredPersonnelUnitEntity closestAPU = null;
            double minD2 = Double.MAX_VALUE;
            for (ArmoredPersonnelUnitEntity apu : allAPUs) {
                double d2 = this.mob.squaredDistanceTo(apu);
                if (d2 < minD2) {
                    closestAPU = apu;
                    minD2 = d2;
                }
            }
            this.targetAPU = closestAPU;
        }
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0D, distance);
    }

    @Override
    public void start() {
        if (this.mob instanceof ZionPeopleEntity zionPeople && this.targetAPU != null) {
            zionPeople.setTargetVehicle(this.targetAPU);
        }
        super.start();
    }
}
