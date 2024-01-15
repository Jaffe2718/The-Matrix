package me.jaffe2718.the_matrix.client.gui.screen;

import me.jaffe2718.the_matrix.unit.gui.screen.TeleporterScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

@Environment(EnvType.CLIENT)
public class TeleporterScreen extends HandledScreen<TeleporterScreenHandler> {
    private static final Identifier BACKGROUND = new Identifier(MOD_ID, "textures/gui/container/teleporter.png");
    private static final Identifier BUTTON = new Identifier(MOD_ID, "textures/gui/sprites/teleporter/button.png");

    /**
     * Origin: 720x480<br>
     * Scaled: 72x48
     * */
    private static final Identifier ROBOT_WORLD_TEXTURE = new Identifier(MOD_ID, "textures/gui/sprites/teleporter/robot_world.png");

    /**
     * Origin: 720x480<br>
     * Scaled: 72x48
     * */
    private static final Identifier VIRTUAL_WORLD_TEXTURE = new Identifier(MOD_ID, "textures/gui/sprites/teleporter/virtual_world.png");

    public TeleporterScreen(TeleporterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(@NotNull DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(BACKGROUND, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        context.drawTexture(ROBOT_WORLD_TEXTURE, this.x + 30, this.y + 30, 0F, 0F, 72, 48, 72, 48);
        context.drawTexture(BUTTON, this.x + 130, this.y + 44, 0F, 0F, 20, 20, 20, 20);
        context.drawTexture(VIRTUAL_WORLD_TEXTURE, this.x + 30, this.y + 100, 0F, 0F, 72, 48, 72, 48);
        context.drawTexture(BUTTON, this.x + 130, this.y + 114, 0F, 0F, 20, 20, 20, 20);
    }

    @Override
    protected void drawForeground(@NotNull DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
        context.drawText(this.textRenderer, Text.translatable("container.the_matrix.teleporter.robot_world"), this.titleX + 30, this.titleY + 15, 4210752, false);
        context.drawText(this.textRenderer, Text.translatable("container.the_matrix.teleporter.virtual_world"), this.titleX + 30, this.titleY + 85, 4210752, false);
    }
}
