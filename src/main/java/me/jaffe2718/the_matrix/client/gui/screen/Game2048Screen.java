package me.jaffe2718.the_matrix.client.gui.screen;

import me.jaffe2718.the_matrix.unit.gui.screen.Game2048ScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

@Environment(EnvType.CLIENT)
public class Game2048Screen extends HandledScreen<Game2048ScreenHandler> {
    private static final Identifier BACKGROUND = new Identifier(MOD_ID, "textures/gui/container/game2048.png");
    private static final Identifier BUTTONS = new Identifier(MOD_ID, "textures/gui/sprites/game2048/buttons.png");

    public Game2048Screen(Game2048ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(BACKGROUND, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        context.drawTexture(BUTTONS, this.x + 72, this.y + 14, 0F, 0F, 60, 60, 60, 60);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        context.drawText(this.textRenderer, Text.translatable("container.the_matrix.game2048.reward"), 136, 20, 4210752, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMatrix(context);
    }

    private void drawMatrix(@NotNull DrawContext context) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; ++j) {
                int number = (int) Math.pow(2, this.handler.slots.get(37 + i * 4 + j).getStack().getCount());
                number = number > 1 ? number : 0;
                if (number > 1) {
                    context.drawTexture(getTextureForNumber(number), this.x + 6 + 14 * j, this.y + 14 + 14 * i, 0F, 0F, 16, 16, 16, 16);
                }
            }
        }
    }

    @Contract("_ -> new")
    private static @NotNull Identifier getTextureForNumber(int number) {   // no texture for 0
        return new Identifier(MOD_ID, "textures/gui/sprites/game2048/n" + number + ".png");
    }
}
