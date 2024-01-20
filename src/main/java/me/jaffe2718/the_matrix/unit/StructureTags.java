package me.jaffe2718.the_matrix.unit;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public interface StructureTags {

    /**
     * The tag for the zion structure, see resources/data/minecraft/tags/worldgen/structure/zion_structures.json
     */
    TagKey<Structure> ZION_STRUCTURES = of("zion_structures");

    private static TagKey<Structure> of(String id) {
        return TagKey.of(RegistryKeys.STRUCTURE, new Identifier(id));
    }
}
