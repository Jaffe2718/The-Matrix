package me.jaffe2718.the_matrix.unit;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class ModelPredicateInit {
    public static void register() {
        ModelPredicateProviderRegistry.register(
                new Identifier(MOD_ID, "electromagnetic_gun_energy"),
                (stack, world, entity, seed) -> stack.getOrCreateNbt().getFloat("Energy") / 6
        );
    }
}
