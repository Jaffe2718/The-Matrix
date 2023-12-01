package me.jaffe2718.the_matrix.mixin.ui;

import net.minecraft.server.integrated.IntegratedServerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(IntegratedServerLoader.class)
public abstract class IntegratedServerLoaderMixin {
    @ModifyVariable(
            method = "start(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;ZZ)V",
            at = @At("HEAD"),
            argsOnly = true,
            index = 4
    )
    private boolean removeWarningOnLoad(boolean original) {
        return false;
    }

    // Set bypassWarnings = true
    @ModifyVariable(
            method = "tryLoad",
            at = @At("HEAD"),
            argsOnly = true,
            index = 4
    )
    private static boolean removeWarningOnCreation(boolean original) {
        return true;
    }
}
