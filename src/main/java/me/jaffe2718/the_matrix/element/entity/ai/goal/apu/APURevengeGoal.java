package me.jaffe2718.the_matrix.element.entity.ai.goal.apu;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class APURevengeGoal extends RevengeGoal {
    public APURevengeGoal(PathAwareEntity mob) {
        super(mob);
    }

    @Override
    public boolean canStart() {
        Entity driver = this.mob.getFirstPassenger();
        boolean checkDriver = driver != null && driver.isAlive() && driver instanceof ZionPeopleEntity;
        if (!checkDriver) return false;
        LivingEntity attacker = this.mob.getAttacker();
        boolean checkEnemy = attacker != null && attacker.isAlive() && EntityRegistry.ROBOT_CLASSES.contains(attacker.getClass());
        if (!checkEnemy) {
            return false;
        }
        else if (driver instanceof ZionPeopleEntity zionPeople) {
            zionPeople.setTarget(attacker);
        }
        return super.canStart();
    }
}
