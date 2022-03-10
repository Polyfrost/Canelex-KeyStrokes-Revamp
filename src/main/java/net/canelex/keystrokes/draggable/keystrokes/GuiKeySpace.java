package net.canelex.keystrokes.draggable.keystrokes;

import net.canelex.keystrokes.KeystrokesMod;
import net.minecraft.client.settings.KeyBinding;

public class GuiKeySpace extends GuiKey {
    public GuiKeySpace(KeystrokesMod mod, int x, int y, int width, int height, KeyBinding keyBinding) {
        super(mod, x, y, width, height, keyBinding);
    }

    public void drawKey(int x, int y) {
        x += this.relX;
        y += this.relY;
        drawRect(x, y, x + this.width, y + this.height, getBackgroundColor());
        int color = getTextColor();
        drawHorizontalLine(x + this.width / 2 - 6, x + this.width / 2 + 6, y + this.height / 2 - 1, color);
        if (this.mod.shadow.isTrue()) {
            if ((color & 0xFC000000) == 0)
                color |= 0xFF000000;
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
            drawHorizontalLine(x + this.width / 2 - 5, x + this.width / 2 + 7, y + this.height / 2, color);
        }
    }
}
