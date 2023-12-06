package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class EntityRegistry {

    public static final EntityType<AgentEntity> AGENT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "agent"),
            EntityType.Builder.create(AgentEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(0.6F, 1.95F)
                    .build("agent")
    );

    public static final EntityType<ArmoredPersonnelUnitEntity> ARMORED_PERSONAL_UNIT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "armored_personnel_unit"),
            EntityType.Builder.create(ArmoredPersonnelUnitEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(3.5F, 5.5F)
                    .build("armored_personnel_unit")
    );

    public static final EntityType<BulletEntity> BULLET = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "bullet"),
            EntityType.Builder.create(BulletEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(0.125F, 0.125F)
                    .build("bullet")
    );

    public static final EntityType<RobotSentinelEntity> ROBOT_SENTINEL = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "robot_sentinel"),
            EntityType.Builder.create(RobotSentinelEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(2.0F, 1.4F)
                    .build("robot_sentinel")
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(AGENT, AgentEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ARMORED_PERSONAL_UNIT, ArmoredPersonnelUnitEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ROBOT_SENTINEL, RobotSentinelEntity.createAttributes());
    }
}
