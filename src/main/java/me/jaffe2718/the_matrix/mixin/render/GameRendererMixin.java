package me.jaffe2718.the_matrix.mixin.render;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    /**
     * Change the fov based on the zoom level.
     * @since 1.6-MC1.20.2
     * @author Jaffe2718
     * {@link net.minecraft.client.render.GameRenderer#renderWorld}
     * */
    @ModifyVariable(method = "renderWorld", at = @At(value = "STORE"), ordinal = 0)
    private double injectFov(double d) {
        if (MinecraftClient.getInstance().player != null &&
                MinecraftClient.getInstance().player.getVehicle() instanceof ArmoredPersonnelUnitEntity) {
            return d + 20;
        } else {
            return d;
        }
    }
}
