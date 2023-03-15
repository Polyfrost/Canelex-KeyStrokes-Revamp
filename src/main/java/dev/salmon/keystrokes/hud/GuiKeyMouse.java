package dev.salmon.keystrokes.hud;

import net.minecraft.client.settings.KeyBinding;

public class GuiKeyMouse extends GuiKey {
    private boolean pressed;

    public GuiKeyMouse(float x, float y, float width, float height, KeyBinding keyBinding) {
        super(x, y, width, height, keyBinding);
    }

    @Override
    protected boolean isKeyDown(int code) {
        boolean down = super.isKeyDown(code) || pressed;
        if (pressed) pressed = false;
        return down;
    }

    public void pressed(int code) {
        if (code != this.keyBinding.getKeyCode()) return;
        pressed = true;
    }
}
