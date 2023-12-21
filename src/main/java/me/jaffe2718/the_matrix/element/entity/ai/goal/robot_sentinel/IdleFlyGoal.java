package me.jaffe2718.the_matrix.element.entity.ai.goal.robot_sentinel;

import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import me.jaffe2718.the_matrix.unit.MathUnit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

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
    public boolean canStop() {
        return this.mob.canContinueAttacking();
    }

    @Override
    public void start() {
        this.adjustDirection();
    }

    @Override
    public void tick() {
        if (this.mob.getRandom().nextInt(100) == 0 || this.mob.isOnGround() || this.mob.isTouchingWater()) {
            this.adjustDirection();
        } else {
            if (this.isGetStuck()) {
                if (this.mob.getRandom().nextBoolean()) {
                    this.adjustDirection();
                } else {
                    this.invertDirection();
                }
            }
        }
        Vec3d velocity = this.calculateVelocity();
        this.mob.setVelocity(velocity);
        this.adjustFacing();
    }

    private void adjustDirection() {
        this.circlingCenter = this.mob.getBlockPos().add(
                this.mob.getRandom().nextBetween(-25, 25),
                this.mob.getRandom().nextBetween(-10, 10),
                this.mob.getRandom().nextBetween(-25, 25)
        );
        this.rotationVec = new Vec3d(
                this.mob.getRandom().nextBetween(-5, 5),
                this.mob.getRandom().nextBetween(3, 8) * (this.mob.getRandom().nextBoolean() ? 1 : -1),
                this.mob.getRandom().nextBetween(-5, 5)
        ).normalize().multiply(this.mob.getRandom().nextBetween(25, 40));
    }

    private void invertDirection() {
        this.rotationVec = this.rotationVec.multiply(-1);
        BlockPos delta = this.mob.getBlockPos().subtract(this.circlingCenter);
        this.circlingCenter = this.circlingCenter.subtract(delta.multiply(2));
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
        Vec3d relativeVec = MathUnit.relativePos(centerVec, this.mob.getPos());
        if (relativeVec.length() >= this.rotationVec.length()) {
            return this.rotationVec.crossProduct(relativeVec).normalize().multiply(1.2D);
        } else {
            return relativeVec.normalize().multiply(1.2D);
        }
    }

    private boolean isGetStuck() {
        Vec3d viewVec = this.mob.getRotationVector();
        BlockPos frontPos = this.mob.getBlockPos().add((int) Math.round(viewVec.x), (int) Math.round(viewVec.y), (int) Math.round(viewVec.z));
        List<BlockPos> checkPosList = List.of(
                frontPos,
                frontPos.up(),
                frontPos.down(),
                frontPos.north(),
                frontPos.south(),
                frontPos.east(),
                frontPos.west()
        );
        boolean isStuck = false;
        for (BlockPos checkPos : checkPosList) {
            Block checkBlock = this.mob.getWorld().getBlockState(checkPos).getBlock();
            if (checkBlock != Blocks.AIR && checkBlock != Blocks.CAVE_AIR &&
                    checkBlock != Blocks.WATER && checkBlock != Blocks.WATER_CAULDRON) {
                isStuck = true;
                break;
            }
        }
        return isStuck;
    }
}
