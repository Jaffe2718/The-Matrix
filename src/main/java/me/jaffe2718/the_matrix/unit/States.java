package me.jaffe2718.the_matrix.unit;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.stat.Stats.CUSTOM;
import static software.bernie.geckolib.GeckoLib.MOD_ID;

public abstract class States {
    public static Identifier INTERACT_WITH_LAPTOP;

    @Contract(pure = true)
    private static @NotNull Identifier register(Identifier identifier, StatFormatter formatter) {
        Registry.register(Registries.CUSTOM_STAT, identifier.toString(), identifier);
        CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static void init() {
        INTERACT_WITH_LAPTOP = register(new Identifier(MOD_ID, "interact_with_computer"), StatFormatter.DEFAULT);
    }
}
