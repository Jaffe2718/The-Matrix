package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.FleeTask;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.server.world.ServerWorld;

public class RobotSentinelAttackGoal extends MeleeAttackGoal {

    public RobotSentinelAttackGoal(RobotSentinelEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

    @Override
    protected void attack(LivingEntity target) {
        super.attack(target);
        if (this.mob.getWorld() instanceof ServerWorld serverWorld) {
            new FleeTask(2.4F, (entity) -> entity.equals(target)).tryStarting(serverWorld, this.mob, 3);
        }
    }
}
