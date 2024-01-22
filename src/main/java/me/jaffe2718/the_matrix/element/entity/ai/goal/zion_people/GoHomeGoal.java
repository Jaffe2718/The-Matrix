package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class GoHomeGoal extends Goal {
    private final ZionPeopleEntity zionPeople;

    public GoHomeGoal(ZionPeopleEntity zionPeople) {
        this.zionPeople = zionPeople;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public void start() {
        Vec3d homePos = this.zionPeople.homePos.toCenterPos();
        this.zionPeople.getNavigation().startMovingTo(homePos.getX(), homePos.getY(), homePos.getZ(), 1.0);
    }

    private static boolean checkHomePos(@NotNull BlockPos pos, @NotNull World world) {
        return world.getBlockState(pos.up(2)).isOf(Blocks.LANTERN)
                && world.getBlockState(pos.up()).isAir()
                && world.getBlockState(pos).isAir()
                && world.getBlockState(pos.down()).isSolid();
    }

    @Override
    public void stop() {
        this.zionPeople.getNavigation().stop();
    }

    @Override
    public boolean canStart() {
        this.zionPeople.hasHome = checkHomePos(this.zionPeople.homePos, this.zionPeople.getWorld());
        double d2 = this.zionPeople.squaredDistanceTo(this.zionPeople.homePos.toCenterPos());
        if (d2 > 4096) this.zionPeople.hasHome = false;
        boolean d2gt8 = this.zionPeople.hasHome && d2 > 8;
        int dayTime = (int) (this.zionPeople.getWorld().getTimeOfDay() % 24000L);
        boolean isNight = dayTime >= 13000 && dayTime <= 23000;
        return this.zionPeople.isAlive() && d2gt8 && isNight;
    }
}
