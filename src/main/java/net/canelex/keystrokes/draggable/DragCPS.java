package net.canelex.keystrokes.draggable;

import net.canelex.keystrokes.KeystrokesMod;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DragCPS extends DragGui {
    private final List<Long> right;
    private final List<Long> left;
    private boolean rightPressed;
    private boolean leftPressed;

    private boolean enabled;

    public DragCPS(KeystrokesMod mod, int x, int y, float scale) {
        super(mod, x, y, scale);
        this.right = new ArrayList<>();
        this.left = new ArrayList<>();
        this.enabled = true;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void drawUI() {
        if (!this.enabled)
            return;
        if (this.scale != 0.0F) {
            FontRenderer fr = this.mc.fontRendererObj;
            String sToDraw = this.left.size() + " | " + this.right.size() + " CPS";
            Gui.drawRect(this.posX, this.posY, this.posX + getWidth(), this.posY + getHeight(), this.mod.bgUnpressed.getValue());
            int mx = this.posX + (getWidth() - fr.getStringWidth(sToDraw)) / 2;
            int my = this.posY + (getHeight() - fr.FONT_HEIGHT) / 2 + 1;
            GL11.glEnable(3042);
            fr.drawString(sToDraw, mx, my, getColorText(), this.mod.shadow.isTrue());
            GL11.glDisable(3042);
        }
    }

    @SubscribeEvent
    public void render(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        boolean pressed = Mouse.isButtonDown(0);
        if (pressed != leftPressed) {
            leftPressed = pressed;
            if (pressed) left.add(System.currentTimeMillis());
        }

        pressed = Mouse.isButtonDown(1);
        if (pressed != rightPressed) {
            rightPressed = pressed;
            if (pressed) right.add(System.currentTimeMillis());
        }

        left.removeIf(l -> l + 1000 < System.currentTimeMillis());
        right.removeIf(l -> l + 1000 < System.currentTimeMillis());
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
