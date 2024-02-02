package me.jaffe2718.the_matrix.mixin.server.network;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import me.jaffe2718.the_matrix.element.entity.vehicle.SpaceshipEntity;
import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Unique private boolean isSafetyCatchPressed = false;
    @Unique private boolean isAttackKeyPressed = false;
    @Unique private boolean isUseKeyPressed = false;

    /**
     * -128: safety catch pressed<br>
     * -127: attack key pressed<br>
     * -126: use key pressed<br>
     * -125: safety catch released<br>
     * -124: attack key released<br>
     * -123: use key released<br>
     * @see me.jaffe2718.the_matrix.unit.KeyBindings#BUTTON_EVENT_IDS
     * */
    @Inject(method = "onButtonClick", at = @At("HEAD"))
    private void onButtonClick(@NotNull ButtonClickC2SPacket packet, CallbackInfo ci) {
        switch (packet.getButtonId()) {    // update the status of the key
            case (-128) -> isSafetyCatchPressed = true;
            case (-127) -> isAttackKeyPressed = true;
            case (-126) -> isUseKeyPressed = true;
            case (-125) -> isSafetyCatchPressed = false;
            case (-124) -> isAttackKeyPressed = false;
            case (-123) -> isUseKeyPressed = false;
        }
        if (this.player.getVehicle() instanceof ArmoredPersonnelUnitEntity apu
                && isSafetyCatchPressed && isAttackKeyPressed && apu.shootCooldown == 0) {
            apu.shoot();
        } else if (player.getVehicle() instanceof MachineGunEntity machineGun
                && isSafetyCatchPressed && isAttackKeyPressed && machineGun.shootCooldown == 0) {
            machineGun.shoot();
        } else if (player.getVehicle() instanceof SpaceshipEntity spaceship
                && spaceship.getPower() > 0 && isSafetyCatchPressed) {
            if (isAttackKeyPressed && spaceship.shootCooldown == 0) {
                spaceship.shoot();
            }
            if (isUseKeyPressed && !spaceship.isAccelerating()) {
                spaceship.startAccelerating();
            }
        }
    }
}
