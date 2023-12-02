package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public abstract class FlyMovementGoal extends Goal {
    protected RobotSentinelEntity mob;
    public FlyMovementGoal(RobotSentinelEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    protected boolean isNearTarget() {
        LivingEntity target = this.mob.getTarget();
        return target != null && this.mob.squaredDistanceTo(target) < 16.0D;
    }
}
