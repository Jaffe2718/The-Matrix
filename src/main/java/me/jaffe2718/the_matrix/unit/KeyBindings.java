package me.jaffe2718.the_matrix.unit;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public abstract class KeyBindings {

    /**
     * [0] Fire safety catch key pressed<br>
     * [1] Attack key pressed<br>
     * [2] Use key pressed<br>
     * [3] Fire safety catch key released<br>
     * [4] Attack key released<br>
     * [5] Use key released<br>
     * */
    public static final int[] BUTTON_EVENT_IDS = {-128, -127, -126, -125, -124, -123};

    public static final KeyBinding FIRE_SAFETY_CATCH = new KeyBinding("key.the_matrix.fire_safety_catch",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.categories.the_matrix");

    public static void register() {
        KeyBindingHelper.registerKeyBinding(FIRE_SAFETY_CATCH);
    }
}
