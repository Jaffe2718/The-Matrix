package me.jaffe2718.the_matrix.unit;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public abstract class KeyBindings {
    public static final KeyBinding FIRE_SAFETY_CATCH = new KeyBinding("key.the_matrix.fire_safety_catch",
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "key.categories.the_matrix");

    public static void register() {
        KeyBindingHelper.registerKeyBinding(FIRE_SAFETY_CATCH);
    }
}
