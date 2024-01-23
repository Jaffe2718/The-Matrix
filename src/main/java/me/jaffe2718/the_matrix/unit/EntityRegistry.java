package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.entity.boss.DiggerRobotEntity;
import me.jaffe2718.the_matrix.element.entity.misc.BulletEntity;
import me.jaffe2718.the_matrix.element.entity.misc.ElectromagneticBulletEntity;
import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import me.jaffe2718.the_matrix.element.entity.mob.RobotSentinelEntity;
import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;

public abstract class EntityRegistry {

    /**
     * Only robots will be affected by the electromagnetic pulse.
     * */
    public static final List<Class<? extends LivingEntity>> ROBOT_CLASSES = List.of(  // TODO: add more robot classes
            RobotSentinelEntity.class,
            DiggerRobotEntity.class);

    public static final EntityType<AgentEntity> AGENT = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("agent"),
            EntityType.Builder.create(AgentEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(0.6F, 1.95F)
                    .build("agent")
    );

    public static final EntityType<ArmoredPersonnelUnitEntity> ARMORED_PERSONAL_UNIT = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("armored_personnel_unit"),
            EntityType.Builder.create(ArmoredPersonnelUnitEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(3.5F, 5.5F)
                    .build("armored_personnel_unit")
    );

    public static final EntityType<BulletEntity> BULLET = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("bullet"),
            EntityType.Builder.create(BulletEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(0.125F, 0.125F)
                    .build("bullet")
    );

    public static final EntityType<DiggerRobotEntity> DIGGER_ROBOT = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("digger_robot"),
            EntityType.Builder.create(DiggerRobotEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(9.0F, 24.5F)
                    .build("digger_robot")
    );

    public static final EntityType<ElectromagneticBulletEntity> ELECTROMAGNETIC_BULLET = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("electromagnetic_bullet"),
            EntityType.Builder.create(ElectromagneticBulletEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(0.125F, 0.125F)
                    .build("electromagnetic_bullet")
    );

    public static final EntityType<MachineGunEntity> MACHINE_GUN = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("machine_gun"),
            EntityType.Builder.create(MachineGunEntity::new, SpawnGroup.MISC)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(1.2F, 0.5F)
                    .build("machine_gun")
    );

    public static final EntityType<RobotSentinelEntity> ROBOT_SENTINEL = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("robot_sentinel"),
            EntityType.Builder.create(RobotSentinelEntity::new, SpawnGroup.MONSTER)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .setDimensions(2.0F, 1.4F)
                    .build("robot_sentinel")
    );

    public static final EntityType<ZionPeopleEntity> ZION_PEOPLE = Registry.register(
            Registries.ENTITY_TYPE,
            TheMatrix.id("zion_people"),
            EntityType.Builder.create(ZionPeopleEntity::new, SpawnGroup.CREATURE)
                    .maxTrackingRange(10)
                    .setDimensions(0.6F, 1.95F)
                    .build("zion_people")
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(AGENT, AgentEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ARMORED_PERSONAL_UNIT, ArmoredPersonnelUnitEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(DIGGER_ROBOT, DiggerRobotEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(MACHINE_GUN, MachineGunEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ROBOT_SENTINEL, RobotSentinelEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ZION_PEOPLE, ZionPeopleEntity.createAttributes());
    }
}
