package dev.salmon.keystrokes.hud;

import cc.polyfrost.oneconfig.gui.OneConfigGui;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.utils.color.ColorUtils;
import dev.salmon.keystrokes.config.KeystrokesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class GuiKey extends Gui {

    protected final FontRenderer fr;

    protected float relX, relY, width, height;
    protected KeyBinding keyBinding;
    protected boolean isPressed;
    protected float percentFaded;
    private long lastPress;

    public GuiKey(float x, float y, float width, float height, KeyBinding keyBinding) {
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.relX = x;
        this.relY = y;
        this.width = width;
        this.height = height;
        this.keyBinding = keyBinding;
        this.percentFaded = 0.0F;
        this.lastPress = System.currentTimeMillis();
    }

    public void drawKey(float x, float y, float scale) {
        x += this.relX;
        y += this.relY;
        Platform.getGLPlatform().drawRect(x, y, x + this.width, y + this.height, getBackgroundColor());
        x += (this.width - this.fr.getStringWidth(getKeyName())) / 2;
        y += (this.height - this.fr.FONT_HEIGHT) / 2 + 1;

        GlStateManager.enableBlend();
        this.fr.drawString(getKeyName(), (x + 1), y, getTextColor(), KeystrokesConfig.keystrokesElement.shadow);
        GlStateManager.disableBlend();
    }

    protected int getBackgroundColor() {
        int thisColor = (this.isPressed ? KeystrokesConfig.keystrokesElement.bgPressed : KeystrokesConfig.keystrokesElement.bgUnpressed).getRGB();
        if (this.percentFaded < 1.0F) {
            int lastColor = (this.isPressed ? KeystrokesConfig.keystrokesElement.bgUnpressed : KeystrokesConfig.keystrokesElement.bgPressed).getRGB();
            return getIntermediateColor(thisColor, lastColor, this.percentFaded);
        }
        return thisColor;
    }

    protected int getTextColor() {
        if (KeystrokesConfig.keystrokesElement.chroma)
            return Color.HSBtoRGB((float) (System.currentTimeMillis() % 3000L) / 3000.0F, 0.8F, 1.0F);
        int thisColor = (this.isPressed ? KeystrokesConfig.keystrokesElement.textPressed : KeystrokesConfig.keystrokesElement.textUnpressed).getRGB();
        if (this.percentFaded < 1.0F) {
            int lastColor = (this.isPressed ? KeystrokesConfig.keystrokesElement.textUnpressed : KeystrokesConfig.keystrokesElement.textPressed).getRGB();
            return getIntermediateColor(thisColor, lastColor, this.percentFaded);
        }
        return thisColor;
    }

    private String getKeyName() {
        if (KeystrokesConfig.keystrokesElement.arrows) {
            if (keyBinding == Minecraft.getMinecraft().gameSettings.keyBindForward)
                return "▲";
            if (keyBinding == Minecraft.getMinecraft().gameSettings.keyBindBack)
                return "▼";
            if (keyBinding == Minecraft.getMinecraft().gameSettings.keyBindLeft)
                return "◀";
            if (keyBinding == Minecraft.getMinecraft().gameSettings.keyBindRight)
                return "▶";
        }
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
        if (Minecraft.getMinecraft().currentScreen instanceof OneConfigGui) {
            if (((System.currentTimeMillis() % 1000L > 500L)) != this.isPressed) {
                this.isPressed = !this.isPressed;
                this.percentFaded = 0.5F;
                this.lastPress = System.currentTimeMillis();
            }
        } else if (this.isPressed != isKeyDown(this.keyBinding.getKeyCode())) {
            this.isPressed = !this.isPressed;
            this.lastPress = System.currentTimeMillis();
        }
        this.percentFaded = (float) (System.currentTimeMillis() - this.lastPress) / KeystrokesConfig.keystrokesElement.fadingTime;
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
        return ColorUtils.getColor(avgRed / 255.0F, avgGreen / 255.0F, avgBlue / 255.0F, avgAlpha / 255.0F);
    }

}
