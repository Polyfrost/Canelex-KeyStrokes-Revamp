package net.canelex.keystrokes.draggable;

import net.canelex.keystrokes.KeystrokesMod;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class DragFPS extends DragGui {
    public boolean enabled;

    public DragFPS(KeystrokesMod mod, int x, int y, float scale) {
        super(mod, x, y, scale);
        this.enabled = true;
    }

    public void drawUI() {
        if (!this.enabled)
            return;
        if (this.scale != 0.0F) {
            FontRenderer fr = this.mc.fontRendererObj;
            String sToDraw = this.mc.debug.split(" ")[0] + " FPS";
            Gui.drawRect(this.posX, this.posY, this.posX + getWidth(), this.posY + getHeight(), this.mod.bgUnpressed.getValue());
            int mx = this.posX + (getWidth() - fr.getStringWidth(sToDraw)) / 2;
            int my = this.posY + (getHeight() - fr.FONT_HEIGHT) / 2 + 1;
            GL11.glEnable(3042);
            fr.drawString(sToDraw, mx, my, getColorText(), this.mod.shadow.isTrue());
            GL11.glDisable(3042);
        }
    }

    public int getWidth() {
        return 59;
    }

    public int getHeight() {
        return 14;
    }

    private int getColorText() {
        if (this.mod.chroma.getValue())
            return Color.HSBtoRGB((float) (System.currentTimeMillis() % 3000L) / 3000.0F, 0.8F, 1.0F);
        return this.mod.textUnpressed.getValue();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
