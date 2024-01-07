package me.jaffe2718.the_matrix;

import me.jaffe2718.the_matrix.unit.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TheMatrix implements ModInitializer {
    public static final String MOD_ID = "the_matrix";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
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
    }
}
