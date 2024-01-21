package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SelectHomeGoal extends Goal {
    private final ZionPeopleEntity zionPeople;

    public SelectHomeGoal(ZionPeopleEntity zionPeople) {
        super();
        this.zionPeople = zionPeople;
    }

    @Override
    public boolean canStart() {
        return this.zionPeople.isAlive() && !this.zionPeople.hasHome && this.zionPeople.age % 40 == 0;
    }

    @Override
    public void start() {
        BlockPos currentPos = this.zionPeople.getBlockPos();
        World world = this.zionPeople.getWorld();
        BlockPos nearestLantern = null;
        for (int dx = -16; dx <= 16; dx++) {
            for (int dy = 4; dy > -3; dy--) {
                for (int dz = -16; dz <= 16; dz++) {
                    BlockPos pos = currentPos.add(dx, dy, dz);
                    if (world.getBlockState(pos).isOf(Blocks.LANTERN)
                            && world.getBlockState(pos.down()).isAir()
                            && world.getBlockState(pos.down(2)).isAir()
                            && world.getBlockState(pos.down(3)).isSolid()
                            && (nearestLantern == null
                                || pos.getSquaredDistance(currentPos) < nearestLantern.getSquaredDistance(currentPos))) {
                        nearestLantern = pos;
                    }
                }
            }
        }
        if (nearestLantern != null) {
            this.zionPeople.homePos = nearestLantern.down(2);
            this.zionPeople.hasHome = true;
            TheMatrix.LOGGER.info("[Zion People] " + this.zionPeople.getUuidAsString() + " has selected home at " + this.zionPeople.homePos.toShortString());
        }
    }
}
