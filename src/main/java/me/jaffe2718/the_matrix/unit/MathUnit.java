package me.jaffe2718.the_matrix.unit;

import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public abstract class MathUnit {
    public static double vec3dCos(@NotNull Vec3d a, @NotNull Vec3d b) {
        return a.dotProduct(b) / (a.length() * b.length());
    }

    public static boolean isBetween(double value, double minInclusive, double maxInclusive) {
        return value >= minInclusive && value <= maxInclusive;
    }

    public static Vec3d relativePos(@NotNull Vec3d from, @NotNull Vec3d to) {
        return to.subtract(from);
    }
}
