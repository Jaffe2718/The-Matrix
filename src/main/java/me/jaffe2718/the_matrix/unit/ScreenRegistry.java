package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.unit.gui.screen.*;
import me.jaffe2718.the_matrix.client.gui.screen.*;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class ScreenRegistry {
    public static ScreenHandlerType<Game2048ScreenHandler> GAME_2048_SCREEN_HANDLER;
    public static ScreenHandlerType<TeleporterScreenHandler> TELEPORTER_SCREEN_HANDLER;

    public static void register() {
        GAME_2048_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                new Identifier(MOD_ID, "game2048"),
                new ScreenHandlerType<>(Game2048ScreenHandler::new, FeatureSet.empty())
        );

        TELEPORTER_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                new Identifier(MOD_ID, "teleporter"),
                new ScreenHandlerType<>(TeleporterScreenHandler::new, FeatureSet.empty())
        );

        HandledScreens.register(GAME_2048_SCREEN_HANDLER, Game2048Screen::new);
        HandledScreens.register(TELEPORTER_SCREEN_HANDLER, TeleporterScreen::new);
    }
}
