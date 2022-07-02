package dev.salmon.keystrokes.hud;

import cc.polyfrost.oneconfig.platform.Platform;
import dev.salmon.keystrokes.config.KeystrokesConfig;
import net.minecraft.client.settings.KeyBinding;

public class GuiKeySpace extends GuiKey {

    public GuiKeySpace(float x, float y, float width, float height, KeyBinding keyBinding) {
        super(x, y, width, height, keyBinding);
    }

    public void drawKey(float x, float y, float scale) {
        x += this.relX;
        y += this.relY;
        Platform.getGLPlatform().drawRect(x, y, x + this.width, y + this.height, getBackgroundColor());
        int color = getTextColor();
        drawHorizontalLine(x + this.width / 2 - 6, x + this.width / 2 + 6, y + this.height / 2 - 1, color);
        if (KeystrokesConfig.keystrokesElement.shadow) {
            if ((color & 0xFC000000) == 0)
                color |= 0xFF000000;
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
            drawHorizontalLine(x + this.width / 2 - 5, x + this.width / 2 + 7, y + this.height / 2, color);
        }
    }

    protected void drawHorizontalLine(float startX, float endX, float y, int color) {
        if (endX < startX) {
            float i = startX;
            startX = endX;
            endX = i;
        }

        Platform.getGLPlatform().drawRect(startX, y, endX + 1, y + 1, color);
    }

}
