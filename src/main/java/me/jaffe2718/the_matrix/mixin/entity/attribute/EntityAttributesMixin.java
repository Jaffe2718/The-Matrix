package me.jaffe2718.the_matrix.mixin.entity.attribute;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityAttributes.class)
public abstract class EntityAttributesMixin {
    /**
     * Make the Generic Max Health attribute have a max value of 8192 instead of 1024.
     * */
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Ljava/lang/String;DDD)Lnet/minecraft/entity/attribute/ClampedEntityAttribute;"))
    private static @NotNull ClampedEntityAttribute redirectGenericMaxHealth(String translationKey, double baseValue, double minValue, double maxValue) {
        if ("attribute.name.generic.max_health".equals(translationKey)) {
            return new ClampedEntityAttribute("attribute.name.generic.max_health", 20.0, 1.0, 8192.0);
        } else {
            return new ClampedEntityAttribute(translationKey, baseValue, minValue, maxValue);
        }
    }

}
