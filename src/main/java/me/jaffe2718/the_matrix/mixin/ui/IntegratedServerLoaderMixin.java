package me.jaffe2718.the_matrix.mixin.ui;

import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntegratedServerLoader.class)
public abstract class IntegratedServerLoaderMixin {
//    @ModifyArg(
//            method = "start(Lnet/minecraft/world/level/storage/LevelStorage$Session;Lcom/mojang/serialization/Dynamic;ZZLjava/lang/Runnable;)V",//"start(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;ZZ)V",
//            at = @At("HEAD"),
//            index = 3
//    )
//    private boolean removeWarningOnLoad(boolean canShowBackupPrompt) {
//        return false;
//    }
//    @Overwrite
//    private void showBackupPromptScreen(LevelStorage.Session session, boolean customized, @NotNull Runnable callback, Runnable onCancel) {
//        callback.run();
//    }
    @Inject(method = "showBackupPromptScreen", at = @At("HEAD"), cancellable = true)
    private void onShowBackupPromptScreen(LevelStorage.Session session, boolean customized, Runnable callback, Runnable onCancel, CallbackInfo ci) {
        if (!customized) {
            ci.cancel();
            callback.run();
        }
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
