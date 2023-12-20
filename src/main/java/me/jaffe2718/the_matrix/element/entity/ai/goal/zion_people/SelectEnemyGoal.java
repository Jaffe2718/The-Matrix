package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.util.math.Box;

/**
 * Select enemy goal for apu_pilot/infantry/rifleman of {@link ZionPeopleEntity}
 * */
public class SelectEnemyGoal extends ActiveTargetGoal<LivingEntity> {
    public SelectEnemyGoal(ZionPeopleEntity mob) {
        super(mob, LivingEntity.class, false,
                livingEntity -> livingEntity.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(livingEntity.getClass()));
    }

    @Override
    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 24.0, distance).offset(0, 18, 0);
    }
}
