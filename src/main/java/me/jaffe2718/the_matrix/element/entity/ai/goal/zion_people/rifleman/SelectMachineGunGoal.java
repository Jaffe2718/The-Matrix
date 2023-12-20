package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.rifleman;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static me.jaffe2718.the_matrix.unit.EntityRegistry.ROBOT_CLASSES;

/**
 * For rifleman -> to select a machine gun
 * */
public class SelectMachineGunGoal extends TrackTargetGoal {

    @Nullable
    protected MachineGunEntity targetMachineGun;

    public SelectMachineGunGoal(ZionPeopleEntity rifleman) {
        super(rifleman, false);
    }

    @Override
    public boolean canStart() {
        LivingEntity enemy = this.mob.getTarget();
        this.selectClosestGun();
        boolean hasTargetMachineGun = this.mob instanceof ZionPeopleEntity zionPeople && zionPeople.getTargetVehicle() != null;
        return !hasTargetMachineGun       // if already selected a machine gun, don't select another one
                && enemy != null       // if no enemy, don't select a machine gun
                && enemy.isAlive()     // if enemy is dead, don't select a machine gun
                && ROBOT_CLASSES.contains(enemy.getClass());   // enemy must be a robot
    }

    protected void selectClosestGun() {
        if (this.mob.getVehicle() instanceof MachineGunEntity machineGun) {
            this.targetMachineGun = machineGun;
        } else {
            List<MachineGunEntity> allGuns = this.mob.getWorld().getEntitiesByClass(
                    MachineGunEntity.class,
                    this.getSearchBox(this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                    gun -> gun.isAlive() && !gun.hasPassengers()
            );
            MachineGunEntity closestGun = null;
            double minD2 = Double.MAX_VALUE;
            for (MachineGunEntity gun : allGuns) {
                double d2 = this.mob.squaredDistanceTo(gun);
                if (d2 < minD2) {
                    closestGun = gun;
                    minD2 = d2;
                }
            }
            this.targetMachineGun = closestGun;
        }
    }

    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 4.0D, distance);
    }

    @Override
    public void start() {
        if (this.mob instanceof ZionPeopleEntity zionPeople && this.targetMachineGun != null) {
            zionPeople.setTargetVehicle(this.targetMachineGun);
        }
        super.start();
    }
}
