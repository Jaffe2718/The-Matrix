package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ZionAPUDrivingGoal extends Goal {  // TODO: implement this

    protected final ArmoredPersonnelUnitEntity apu;

    public ZionAPUDrivingGoal(ArmoredPersonnelUnitEntity apu) {
        this.apu = apu;
    }
    @Override
    public boolean canStart() {
        return this.apu.getFirstPassenger() instanceof ZionPeopleEntity;
    }
}
