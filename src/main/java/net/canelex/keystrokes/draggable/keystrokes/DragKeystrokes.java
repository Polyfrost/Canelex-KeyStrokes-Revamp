package net.canelex.keystrokes.draggable.keystrokes;

import net.canelex.keystrokes.KeystrokesMod;
import net.canelex.keystrokes.draggable.DragGui;
import net.minecraft.client.settings.GameSettings;

public class DragKeystrokes extends DragGui {
    private int mode;

    private final GuiKey[] keys;

    public DragKeystrokes(KeystrokesMod mod, int x, int y, float scale) {
        super(mod, x, y, scale);
        GameSettings gs = this.mc.gameSettings;
        (this.keys = new GuiKey[7])[0] = new GuiKey(mod, 20, 0, 19, 19, gs.keyBindForward);
        this.keys[1] = new GuiKey(mod, 0, 20, 19, 19, gs.keyBindLeft);
        this.keys[2] = new GuiKey(mod, 20, 20, 19, 19, gs.keyBindBack);
        this.keys[3] = new GuiKey(mod, 40, 20, 19, 19, gs.keyBindRight);
        this.keys[4] = new GuiKey(mod, 0, 40, 29, 19, gs.keyBindAttack);
        this.keys[5] = new GuiKey(mod, 30, 40, 29, 19, gs.keyBindUseItem);
        this.keys[6] = new GuiKeySpace(mod, 0, 60, 59, 11, gs.keyBindJump);
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
