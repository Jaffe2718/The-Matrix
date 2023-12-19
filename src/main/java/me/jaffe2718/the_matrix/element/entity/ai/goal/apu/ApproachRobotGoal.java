package me.jaffe2718.the_matrix.element.entity.ai.goal.apu;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;

public class ApproachRobotGoal extends Goal {
    protected final ArmoredPersonnelUnitEntity apu;
    private Path path;

    public ApproachRobotGoal(ArmoredPersonnelUnitEntity apu) {
        super();
        this.apu = apu;
    }
    @Override
    public boolean canStart() {
        if (this.apu.getTarget() == null) return false;
        this.path = this.apu.getNavigation().findPathTo(this.apu.getTarget(), 0);
        return this.path != null
                && this.apu.getFirstPassenger() instanceof ZionPeopleEntity
                && this.apu.getTarget().isAlive()
                && EntityRegistry.ROBOT_CLASSES.contains(this.apu.getTarget().getClass());
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity enemy = this.apu.getTarget();
        if (enemy == null || !enemy.isAlive()) {
            return false;
        } else {
            return !(enemy instanceof PlayerEntity) || !enemy.isSpectator() && !((PlayerEntity)enemy).isCreative();
        }
    }

    @Override
    public void start() {
        System.out.println("Start approaching");
        this.apu.getNavigation().startMovingAlong(this.path, 1.0);
        this.apu.setAttacking(true);
    }

    @Override
    public void tick() {
        // LivingEntity enemy = this.apu.getTarget();
        LivingEntity enemy = this.apu.getTarget();
        if (enemy != null && enemy.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(enemy.getClass())) {
            this.apu.getLookControl().lookAt(enemy.getPos());
            // this.apu.setPitch(MathUnit.getPitchDeg(MathUnit.relativePos(this.apu.getPos(), enemy.getPos()).normalize()));
        }
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
