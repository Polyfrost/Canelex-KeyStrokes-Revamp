package dev.salmon.keystrokes.gui.element.keystrokes;

import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.gui.ColorConfigGui;
import dev.salmon.keystrokes.gui.ConfigGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiKey extends Gui {

    protected final FontRenderer fr;

    protected int relX, relY, width, height;
    protected KeyBinding keyBinding;
    protected boolean isPressed;
    protected float percentFaded;
    private long lastPress;

    public GuiKey(int x, int y, int width, int height, KeyBinding keyBinding) {
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.relX = x;
        this.relY = y;
        this.width = width;
        this.height = height;
        this.keyBinding = keyBinding;
        this.percentFaded = 0.0F;
        this.lastPress = System.currentTimeMillis();
    }

    public void drawKey(int x, int y) {
        x += this.relX;
        y += this.relY;
        drawRect(x, y, x + this.width, y + this.height, getBackgroundColor());
        x += (this.width - this.fr.getStringWidth(getKeyName())) / 2;
        y += (this.height - this.fr.FONT_HEIGHT) / 2 + 1;
        GL11.glEnable(3042);
        this.fr.drawString(getKeyName(), (x + 1), y, getTextColor(), Keystrokes.Instance.shadow.isTrue());
        GL11.glDisable(3042);
    }

    protected int getBackgroundColor() {
        int thisColor = this.isPressed ? Keystrokes.Instance.bgPressed.getValue() : Keystrokes.Instance.bgUnpressed.getValue();
        if (this.percentFaded < 1.0F) {
            int lastColor = this.isPressed ? Keystrokes.Instance.bgUnpressed.getValue() : Keystrokes.Instance.bgPressed.getValue();
            return getIntermediateColor(thisColor, lastColor, this.percentFaded);
        }
        return thisColor;
    }

    protected int getTextColor() {
        if (Keystrokes.Instance.chroma.getValue())
            return Color.HSBtoRGB((float) (System.currentTimeMillis() % 3000L) / 3000.0F, 0.8F, 1.0F);
        int thisColor = this.isPressed ? Keystrokes.Instance.textPressed.getValue() : Keystrokes.Instance.textUnpressed.getValue();
        if (this.percentFaded < 1.0F) {
            int lastColor = this.isPressed ? Keystrokes.Instance.textUnpressed.getValue() : Keystrokes.Instance.textPressed.getValue();
            return getIntermediateColor(thisColor, lastColor, this.percentFaded);
        }
        return thisColor;
    }

    private String getKeyName() {
        int code = this.keyBinding.getKeyCode();
        switch (code) {
            case -100:
                return "LMB";
            case -99:
                return "RMB";
            case -98:
                return "MMB";
            case 200:
                return "U";
            case 203:
                return "L";
            case 205:
                return "R";
            case 208:
                return "D";
            case 210:
                return "INS";
            case 29:
                return "LCTRL";
            case 157:
                return "RCTRL";
            case 56:
                return "LALT";
            case 184:
                return "RALT";
        }
        if (code >= 0 && code <= 223)
            return Keyboard.getKeyName(code);
        if (code >= -100 && code <= -84)
            return Mouse.getButtonName(code + 100);
        return "[]";
    }

    public void updateKeyState() {
        if (Minecraft.getMinecraft().currentScreen instanceof ColorConfigGui) {
            this.isPressed = ((ColorConfigGui) Minecraft.getMinecraft().currentScreen).showPressed;
            this.percentFaded = 1.0F;
        } else if (Minecraft.getMinecraft().currentScreen instanceof ConfigGUI) {
            if (((System.currentTimeMillis() % 1000L > 500L)) != this.isPressed) {
                this.isPressed = !this.isPressed;
                this.percentFaded = 0.5F;
                this.lastPress = System.currentTimeMillis();
            }
        } else if (this.isPressed != isKeyDown(this.keyBinding.getKeyCode())) {
            this.isPressed = !this.isPressed;
            this.lastPress = System.currentTimeMillis();
        }
        this.percentFaded = (float) (System.currentTimeMillis() - this.lastPress) / Keystrokes.Instance.fadingTime.getValue();
        if (this.percentFaded > 1.0F)
            this.percentFaded = 1.0F;
    }

    private boolean isKeyDown(int code) {
        if (code < 0)
            return Mouse.isButtonDown(code + 100);
        return Keyboard.isKeyDown(code);
    }

    protected int getIntermediateColor(int a, int b, float percent) {
        float avgRed = (a >> 16 & 0xFF) * percent + (b >> 16 & 0xFF) * (1.0F - percent);
        float avgGreen = (a >> 8 & 0xFF) * percent + (b >> 8 & 0xFF) * (1.0F - percent);
        float avgBlue = (a & 0xFF) * percent + (b & 0xFF) * (1.0F - percent);
        float avgAlpha = (a >> 24 & 0xFF) * percent + (b >> 24 & 0xFF) * (1.0F - percent);
        return (new Color(avgRed / 255.0F, avgGreen / 255.0F, avgBlue / 255.0F, avgAlpha / 255.0F)).getRGB();
    }

}
