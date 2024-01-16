package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.stat.Stats.CUSTOM;

public abstract class States {
    public static Identifier INTERACT_WITH_LAPTOP;
    public static Identifier INTERACT_WITH_TELEPORTER;

    @Contract(pure = true)
    private static @NotNull Identifier register(Identifier identifier, StatFormatter formatter) {
        Registry.register(Registries.CUSTOM_STAT, identifier.toString(), identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static void init() {
        INTERACT_WITH_LAPTOP = register(TheMatrix.id("interact_with_computer"), StatFormatter.DEFAULT);
        INTERACT_WITH_TELEPORTER = register(TheMatrix.id("interact_with_teleporter"), StatFormatter.DEFAULT);
    }
}
