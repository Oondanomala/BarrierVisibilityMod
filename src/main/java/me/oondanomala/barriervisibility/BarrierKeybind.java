package me.oondanomala.barriervisibility;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class BarrierKeybind {
    public final KeyBinding keybind;

    public BarrierKeybind() {
        keybind = new KeyBinding("Toggle Barriers", Keyboard.KEY_NONE, "key.categories.misc");
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (keybind.isPressed()) {
            BarrierVisibility.toggleBarriers();
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (keybind.isPressed()) {
            BarrierVisibility.toggleBarriers();
        }
    }
}
