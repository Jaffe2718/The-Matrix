package me.jaffe2718.the_matrix.element.entity.ai.goal.apu;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ApproachRobotGoal extends Goal {
    protected final ArmoredPersonnelUnitEntity apu;

    public ApproachRobotGoal(ArmoredPersonnelUnitEntity apu) {
        super();
        this.apu = apu;
    }
    @Override
    public boolean canStart() {
        if (this.apu.getTarget() == null) return false;
        return this.apu.getFirstPassenger() instanceof ZionPeopleEntity
                && this.apu.getTarget().isAlive()
                && EntityRegistry.ROBOT_CLASSES.contains(this.apu.getTarget().getClass());
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity enemy = this.apu.getTarget();
        if (enemy == null || !enemy.isAlive()) return false;
        this.apu.getMoveControl().moveTo(enemy.getX(), enemy.getY(), enemy.getZ(), 1.0);
        return EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass());
    }

    @Override
    public void start() {
        //this.apu.getNavigation().startMovingAlong(this.path, 1.0);
        this.apu.setAttacking(true);
    }

    @Override
    public void tick() {
        // LivingEntity enemy = this.apu.getTarget();
        LivingEntity enemy = this.apu.getTarget();
        if (enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass())) {
            // force the APU to face the enemy
            this.apu.lookAtEntity(enemy, 30.0F, 90.0F);
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        super.stop();
        this.apu.setTarget(null);
        this.apu.getNavigation().stop();
        this.apu.setAttacking(false);
        if (this.apu.getFirstPassenger() instanceof ZionPeopleEntity apuPilot) {
            apuPilot.stopRiding();
        }
    }
}
