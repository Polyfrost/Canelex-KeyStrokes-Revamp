package dev.salmon.keystrokes.gui.element;

import dev.salmon.keystrokes.Keystrokes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class FPSElement extends DragElement {

    public boolean enabled;

    public FPSElement(int x, int y, float scale) {
        super(x, y, scale);
        this.enabled = true;
    }

    public void drawUI() {
        if (!this.enabled)
            return;
        if (this.scale != 0.0F) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            String sToDraw = Minecraft.getMinecraft().debug.split(" ")[0] + " FPS";
            Gui.drawRect(this.posX, this.posY, this.posX + getWidth(), this.posY + getHeight(), Keystrokes.Instance.bgUnpressed.getValue());
            int mx = this.posX + (getWidth() - fr.getStringWidth(sToDraw)) / 2;
            int my = this.posY + (getHeight() - fr.FONT_HEIGHT) / 2 + 1;
            GL11.glEnable(3042);
            fr.drawString(sToDraw, mx, my, getColorText(), Keystrokes.Instance.shadow.getValue());
            GL11.glDisable(3042);
        }
    }

    public int getWidth() {
        return 59;
    }

    public int getHeight() {
        return 19;
    }

    private int getColorText() {
        if (Keystrokes.Instance.chroma.isTrue())
            return Color.HSBtoRGB((float) (System.currentTimeMillis() % 3000L) / 3000.0F, 0.8F, 1.0F);
        return Keystrokes.Instance.textUnpressed.getValue();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
