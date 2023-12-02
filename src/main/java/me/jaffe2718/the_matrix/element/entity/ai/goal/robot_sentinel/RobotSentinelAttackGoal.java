package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RobotSentinelAttackGoal extends FlyMovementGoal {

    public RobotSentinelAttackGoal(RobotSentinelEntity mob) {
        super(mob);
    }

    @Override
    public boolean canStart() {
        return this.mob.canContinueAttacking();
    }

    @Override
    public boolean canStop() {
        return this.mob.getTarget() == null || !this.mob.getTarget().isAlive();
    }

    @Override
    public void tick() {
        if (this.mob.isInAttackRange(this.mob.getTarget()) && this.mob.canContinueAttacking()) {
            this.mob.tryAttack(this.mob.getTarget());
            this.mob.getNavigation().stop();
            super.tick();
        }
        System.out.println(this.mob.canContinueAttacking() + " " + this.mob.getVelocity().y);    // TODO: remove after debugging
        this.mob.setVelocity(this.calculateVelocity(Objects.requireNonNull(this.mob.getTarget()).getPos()));
        this.adjustFacing();
    }

    private Vec3d calculateVelocity(@NotNull Vec3d destination) {
        Vec3d relativeVec = destination.subtract(this.mob.getPos());
        double hDist_2 = relativeVec.getX() * relativeVec.getX() + relativeVec.getZ() * relativeVec.getZ();
        int sign = this.mob.canContinueAttacking() ? 1 : -1;    // to make the sentinel retreat when it's not attacking
        if (hDist_2 < 100.0) {   // approach the target directly
            return relativeVec.normalize().multiply(1.2D * sign);
        } else {                 // approach the target in horizontal plane
            Vec3d v0 = new Vec3d(
                    relativeVec.getX(),
                    0.5 * MathHelper.sin((float) Math.sqrt(hDist_2 - 100) / 20) * MathHelper.sqrt((float) hDist_2),
                    relativeVec.getZ()
            ).normalize().multiply(1.2D * sign);
            return new Vec3d(
                    v0.getX(),
                    this.mob.canContinueAttacking() ? v0.getY() : Math.min(Math.abs(v0.getY()), 1e-2),
                    v0.getZ()
            );
        }
    }
}
