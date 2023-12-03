package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class SelectTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {

    public SelectTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility) {
        super(mob, targetClass, checkVisibility);
    }

    public SelectTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, checkVisibility, targetPredicate);
    }

    public SelectTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, boolean checkCanNavigate) {
        super(mob, targetClass, checkVisibility, checkCanNavigate);
    }

    public SelectTargetGoal(MobEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
    }

    @Override
    protected Box getSearchBox(double distance) {
        return this.mob.getBoundingBox().expand(distance, 16.0, distance);
    }
}
