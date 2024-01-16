package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.client.gui.screen.Game2048Screen;
import me.jaffe2718.the_matrix.client.gui.screen.TeleporterScreen;
import me.jaffe2718.the_matrix.unit.gui.screen.Game2048ScreenHandler;
import me.jaffe2718.the_matrix.unit.gui.screen.TeleporterScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;

public abstract class ScreenRegistry {
    public static ScreenHandlerType<Game2048ScreenHandler> GAME_2048_SCREEN_HANDLER;
    public static ScreenHandlerType<TeleporterScreenHandler> TELEPORTER_SCREEN_HANDLER;

    public static void register() {
        GAME_2048_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                TheMatrix.id("game2048"),
                new ScreenHandlerType<>(Game2048ScreenHandler::new, FeatureSet.empty())
        );

        TELEPORTER_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                TheMatrix.id("teleporter"),
                new ScreenHandlerType<>(TeleporterScreenHandler::new, FeatureSet.empty())
        );

        HandledScreens.register(GAME_2048_SCREEN_HANDLER, Game2048Screen::new);
        HandledScreens.register(TELEPORTER_SCREEN_HANDLER, TeleporterScreen::new);
    }
}
