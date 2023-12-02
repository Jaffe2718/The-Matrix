package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public abstract class FlyMovementGoal extends Goal {
    protected RobotSentinelEntity mob;
    public FlyMovementGoal(RobotSentinelEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    protected void adjustFacing() {
        this.mob.setYaw((float) Math.toDegrees(
                MathHelper.atan2(this.mob.getVelocity().getZ(), this.mob.getVelocity().getX())) - 90.0F);
    }
}
