package me.jaffe2718.the_matrix.mixin.ui;

import me.jaffe2718.the_matrix.element.entity.vehicle.ArmoredPersonnelUnitEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier BULLET_TEXTURE = new Identifier(MOD_ID, "hud/bullet");
    @Unique
    private static final Identifier VEHICLE_CONTAINER_HEART_TEXTURE = new Identifier("hud/heart/vehicle_container");
    @Unique
    private static final Identifier VEHICLE_FULL_HEART_TEXTURE = new Identifier("hud/heart/vehicle_full");
    @Unique
    private static final Identifier VEHICLE_HALF_HEART_TEXTURE = new Identifier("hud/heart/vehicle_half");

    @Shadow protected abstract LivingEntity getRiddenEntity();

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract int getHeartCount(LivingEntity entity);

    @Shadow @Final private static Identifier JUMP_BAR_BACKGROUND_TEXTURE;

    @Shadow @Final private static Identifier JUMP_BAR_COOLDOWN_TEXTURE;

    /**
     * @author Jaffe2718
     * @reason Remove original mount health rendering (multiple hearts) when mount's health is too high,
     *        which is replaced by the new mount health rendering (bar shape).
     * @param context the context to draw in
     * COLOR SCHEME: 0xAARRGGBB
     */
    @Overwrite
    private void renderMountHealth(DrawContext context) {
        LivingEntity mount = this.getRiddenEntity();
        if (mount != null && mount.getMaxHealth() != 0) {  // not mine-cart or boat
            double health = mount.getHealth();
            if (mount.getMaxHealth() > 40) {
                int rightX = this.scaledWidth / 2 + 90;
                int upY = this.scaledHeight - 39;
                int healthLength = (int) (health / mount.getMaxHealth() * 80.0D);
                context.fill(rightX - 80, upY, rightX, upY + 9, 0xFFAAAAAA);
                context.fill(rightX - healthLength, upY, rightX, upY + 9, 0xFF8F0000);
                context.drawVerticalLine(rightX - 80, upY, upY + 9, 0xFF000000);
                context.drawVerticalLine(rightX, upY, upY + 9, 0xFF000000);
                context.drawHorizontalLine(rightX - 80, rightX, upY, 0xFF000000);
                context.drawHorizontalLine(rightX - 80, rightX, upY + 9, 0xFF000000);
                context.drawGuiTexture(VEHICLE_CONTAINER_HEART_TEXTURE, rightX - 75, upY, 9, 9);
                context.drawGuiTexture(VEHICLE_FULL_HEART_TEXTURE, rightX - 75, upY, 9, 9);
                context.drawText(this.client.textRenderer, String.format("%d/%d", (int) health, (int) mount.getMaxHealth()), rightX - 60, upY + 1, 0xFFFFFFFF, true);
            } else {     // use vanilla rendering
                int i = this.getHeartCount(mount);
                if (i != 0) {
                    int j = (int)Math.ceil(mount.getHealth());
                    this.client.getProfiler().swap("mountHealth");
                    int k = this.scaledHeight - 39;
                    int l = this.scaledWidth / 2 + 91;
                    int m = k;

                    for(int n = 0; i > 0; n += 20) {
                        int o = Math.min(i, 10);
                        i -= o;

                        for(int p = 0; p < o; ++p) {
                            int q = l - p * 8 - 9;
                            context.drawGuiTexture(VEHICLE_CONTAINER_HEART_TEXTURE, q, m, 9, 9);
                            if (p * 2 + 1 + n < j) {
                                context.drawGuiTexture(VEHICLE_FULL_HEART_TEXTURE, q, m, 9, 9);
                            }

                            if (p * 2 + 1 + n == j) {
                                context.drawGuiTexture(VEHICLE_HALF_HEART_TEXTURE, q, m, 9, 9);
                            }
                        }

                        m -= 10;
                    }

                }
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void registerCustomRenderers(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (this.getRiddenEntity() instanceof ArmoredPersonnelUnitEntity apu) {
            this.renderAPUBulletNumBar(context, apu);
        }
    }

    @Unique
    private void renderAPUBulletNumBar(@NotNull DrawContext context, @NotNull ArmoredPersonnelUnitEntity mount) {
        this.client.getProfiler().push("jumpBar");
        int x = this.scaledWidth / 2 - 91;
        int y = this.scaledHeight - 29;
        float bulletPercentage = (float) mount.getBulletNum() / (float) ArmoredPersonnelUnitEntity.MAX_BULLET_NUM;
        int bulletLength = (int) (bulletPercentage * 182.0F);
        context.drawGuiTexture(BULLET_TEXTURE, x - 10, y - 2, 9, 9);
        context.drawGuiTexture(JUMP_BAR_BACKGROUND_TEXTURE, x, y, 182, 5);
        context.drawGuiTexture(JUMP_BAR_COOLDOWN_TEXTURE, x, y, 182, 5);
        if (bulletPercentage > 0.0F) {
            context.drawGuiTexture(JUMP_BAR_BACKGROUND_TEXTURE, 182, 5, 0, 0, x, y, bulletLength, 5);
        }
    }
}
