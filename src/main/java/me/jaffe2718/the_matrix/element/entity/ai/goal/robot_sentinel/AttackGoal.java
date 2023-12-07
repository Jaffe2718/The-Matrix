package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import me.jaffe2718.the_matrix.unit.MathUnit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AttackGoal extends FlyMovementGoal {

    public AttackGoal(RobotSentinelEntity mob) {
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
        this.mob.addVelocity(this.calculateAcceleration(Objects.requireNonNull(this.mob.getTarget()).getPos()));
        this.adjustFacing();
    }

    private Vec3d calculateVelocity(@NotNull Vec3d destination) {
        Vec3d relativeVec = MathUnit.relativePos(this.mob.getPos(), destination);
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

    /**
     * Calculate the acceleration needed to reach the target velocity.<br>
     * Use acceleration instead of velocity to avoid unable to be knocked back.
     *
     * @param destination the target velocity
     * @return the acceleration needed to reach the target velocity
     */
    private Vec3d calculateAcceleration(@NotNull Vec3d destination) {
        Vec3d newVelocity = this.calculateVelocity(destination);
        return newVelocity.subtract(this.mob.getVelocity());
    }
}
