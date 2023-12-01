package me.jaffe2718.the_matrix.element.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;

public class TheMatrixEntity     // TODO: unimplemented
        extends MobEntity
        implements Monster {
    public TheMatrixEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }
}
