package dev.salmon.keystrokes.hud;

import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.NanoVGHelper;
import dev.salmon.keystrokes.config.KeystrokesConfig;
import net.minecraft.client.settings.KeyBinding;

public class GuiKeySpace extends GuiKey {

    public GuiKeySpace(float x, float y, float width, float height, KeyBinding keyBinding) {
        super(x, y, width, height, keyBinding);
    }

    public void drawKey(float x, float y, float scale) {
        float finalX = x + relX * scale;
        float finalY = y + relY * scale;
        x += this.relX;
        y += this.relY;
        if (KeystrokesConfig.keystrokesElement.rounded) {
            NanoVGHelper.INSTANCE.setupAndDraw(true, vg -> NanoVGHelper.INSTANCE.drawRoundedRect(vg, finalX, finalY, width * scale, height * scale, getBackgroundColor(), KeystrokesConfig.keystrokesElement.cornerRadius * scale));
        } else {
            Platform.getGLPlatform().drawRect(x, y, x + this.width, y + this.height, getBackgroundColor());
        }
        int color = getTextColor();
        drawHorizontalLine(x + this.width / 2 - 6, x + this.width / 2 + 5, y + this.height / 2 - 0.5f, color);
        if (KeystrokesConfig.keystrokesElement.shadow) {
            if ((color & 0xFC000000) == 0)
                color |= 0xFF000000;
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
            drawHorizontalLine(x + this.width / 2 - 5, x + this.width / 2 + 6, y + this.height / 2 + 0.5f, color);
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
