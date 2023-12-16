package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people.apu;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.util.math.Box;

public class SelectRobotGoal extends ActiveTargetGoal<LivingEntity> {
    public SelectRobotGoal(ArmoredPersonnelUnitEntity apu) {
        super(apu, LivingEntity.class, true,
                livingEntity -> livingEntity.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(livingEntity.getClass()));
    }

    @Override
    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 24.0, distance).offset(0, 10, 0);
    }
}
