package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;

public class SelectTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {

    public SelectTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility) {
        super(mob, targetClass, checkVisibility);
    }

    @Override
    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 16.0, distance);
    }
}
