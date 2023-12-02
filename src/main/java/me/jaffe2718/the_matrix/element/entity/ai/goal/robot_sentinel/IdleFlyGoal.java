package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class IdleFlyGoal extends FlyMovementGoal {

    private Vec3d rotationVec;   // length means radius, direction means rotation axis, right hand rule
    private BlockPos circlingCenter;

    public IdleFlyGoal(RobotSentinelEntity mob) {
        super(mob);
    }

    @Override
    public boolean canStart() {
        return this.mob.getTarget() == null;
    }

    @Override
    public void start() {
        this.adjustDirection();
    }

    @Override
    public void tick() {
        if (this.mob.getRandom().nextInt(100) == 0) {
            this.adjustDirection();
        }
        Vec3d velocity = this.calculateVelocity();
        this.mob.setVelocity(velocity);
        this.mob.setYaw((float) Math.toDegrees(MathHelper.atan2(velocity.getZ(), velocity.getX())) - 90.0F);

    }

    private void adjustDirection() {
        this.circlingCenter = this.mob.getBlockPos().add(
                this.mob.getRandom().nextBetween(-25, 25),
                this.mob.getRandom().nextBetween(-5, 5),
                this.mob.getRandom().nextBetween(-25, 25)
        );
        this.rotationVec = new Vec3d(
                this.mob.getRandom().nextBetween(-5, 5),
                this.mob.getRandom().nextBetween(3, 8) * (this.mob.getRandom().nextBoolean() ? 1 : -1),
                this.mob.getRandom().nextBetween(-5, 5)
        ).normalize().multiply(this.mob.getRandom().nextBetween(25, 40));
        // System.out.println("circlingCenter: " + this.circlingCenter + ", rotationVec: " + this.rotationVec);
    }

    /**
     * velocityLength = 1.2D<br>
     * rotationRadius = rotationVec.l2Norm()<br>
     * if relativeVec.l2Norm() >= rotationRadius then<br>
     *     direction = cross(rotationVec, relativeVec).normalize()   // surround the center<br>
     * else<br>
     *     direction = relativeVec.normalize()       // back to the center<br>
     * */
    private Vec3d calculateVelocity() {
        Vec3d centerVec = new Vec3d(
                this.circlingCenter.getX(),
                this.circlingCenter.getY(),
                this.circlingCenter.getZ()
        );
        Vec3d relativeVec = this.mob.getPos().subtract(centerVec);
        if (relativeVec.length() >= this.rotationVec.length()) {
            return this.rotationVec.crossProduct(relativeVec).normalize().multiply(1.2D);
        } else {
            return relativeVec.normalize().multiply(1.2D);
        }
    }
}