package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.element.entity.mob.AgentEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public class EntityRegistry {

    public static final EntityType<AgentEntity> AGENT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "agent"),
            FabricEntityTypeBuilder
                    .create(SpawnGroup.MONSTER, AgentEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(AGENT, AgentEntity.createAgentAttributes());
    }
}
