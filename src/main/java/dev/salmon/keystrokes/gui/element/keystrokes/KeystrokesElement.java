package dev.salmon.keystrokes.gui.element.keystrokes;

import dev.salmon.keystrokes.gui.element.DragElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class KeystrokesElement extends DragElement {

    private int mode;
    private final GuiKey[] keys;

    public KeystrokesElement(int x, int y, float scale) {
        super(x, y, scale);
        GameSettings gs = Minecraft.getMinecraft().gameSettings;
        (this.keys = new GuiKey[7])[0] = new GuiKey(20, 0, 19, 19, gs.keyBindForward);
        this.keys[1] = new GuiKey(0, 20, 19, 19, gs.keyBindLeft);
        this.keys[2] = new GuiKey(20, 20, 19, 19, gs.keyBindBack);
        this.keys[3] = new GuiKey(40, 20, 19, 19, gs.keyBindRight);
        this.keys[4] = new GuiKey(0, 40, 29, 19, gs.keyBindAttack);
        this.keys[5] = new GuiKey(30, 40, 29, 19, gs.keyBindUseItem);
        this.keys[6] = new GuiKeySpace(0, 60, 59, 11, gs.keyBindJump);
    }

    public void drawUI() {
        if (this.scale != 0.0F)
            for (int numKeys = (this.mode == 0) ? 4 : ((this.mode == 1) ? 6 : 7), i = 0; i < numKeys; i++) {
                this.keys[i].updateKeyState();
                this.keys[i].drawKey(this.posX, this.posY);
            }
    }

    public int getWidth() {
        return 59;
    }

    public int getHeight() {
        switch (this.mode) {
            case 0:
                return 40;
            case 1:
                return 60;
        }
        return 72;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

}
