package me.jaffe2718.the_matrix;

import me.jaffe2718.the_matrix.unit.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TheMatrix implements ModInitializer {

    public static final String MOD_ID = "the_matrix";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Contract("_ -> new")
    public static @NotNull Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        BlockRegistry.register();
        EntityRegistry.register();
        EventHandler.register();
        ItemRegistry.register();
        ParticleRegistry.register();
        ScreenRegistry.register();
        SoundEventRegistry.register();
        States.init();
        TheMatrix.LOGGER.info("The Matrix has been initialized.");
    }
}
