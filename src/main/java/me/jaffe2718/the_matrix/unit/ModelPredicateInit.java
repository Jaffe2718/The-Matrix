package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public abstract class ModelPredicateInit {
    public static void register() {
        ModelPredicateProviderRegistry.register(
                TheMatrix.id("electromagnetic_gun_energy"),
                (stack, world, entity, seed) -> stack.getOrCreateNbt().getFloat("Energy") / 6
        );
    }
}
