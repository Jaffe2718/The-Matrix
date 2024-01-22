package me.jaffe2718.the_matrix.element.entity.ai.goal.zion_people;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FollowCustomerGoal extends Goal {
    private final ZionPeopleEntity zionPeople;
    public FollowCustomerGoal(ZionPeopleEntity zionPeople) {
        this.zionPeople = zionPeople;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public void start() {
        this.zionPeople.getNavigation().startMovingAlong(this.zionPeople.getNavigation().findPathTo(this.zionPeople.getCustomer(), 1), 1.0);
    }

    public void stop() {
        this.zionPeople.getNavigation().stop();
    }

    @Override
    public boolean canStart() {
        return this.zionPeople.isAlive()
                && this.zionPeople.getCustomer() != null
                && this.zionPeople.squaredDistanceTo(this.zionPeople.getCustomer()) > 8;
    }
}
