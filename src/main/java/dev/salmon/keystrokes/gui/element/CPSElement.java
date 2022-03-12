package dev.salmon.keystrokes.gui.element;

import dev.salmon.keystrokes.Keystrokes;
import net.minecraft.client.Minecraft;
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

public class CPSElement extends DragElement {

    private final List<Long> left, right;
    private boolean leftPressed, rightPressed, enabled;

    public CPSElement(int x, int y, float scale) {
        super(x, y, scale);
        this.right = new ArrayList<>();
        this.left = new ArrayList<>();
        this.enabled = true;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void drawUI() {
        if (!this.enabled) return;
        String title;
        if (Keystrokes.Instance.cpsTitle.getValue()) {
            title = " CPS";
        } else {
            title = "";
        }
        if (Keystrokes.Instance.scale.getValue() != 0.0f) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            String sToDraw = this.left.size() + " | " + this.right.size() + title;
            Gui.drawRect(this.posX, this.posY, this.posX + getWidth(), this.posY + getHeight(), Keystrokes.Instance.bgUnpressed.getValue());
            int mx = this.posX + (getWidth() - fr.getStringWidth(sToDraw)) / 2;
            int my = this.posY + (getHeight() - fr.FONT_HEIGHT) / 2 + 1;
            GL11.glEnable(3042);
            fr.drawString(sToDraw, mx, my, getColorText(), Keystrokes.Instance.shadow.getValue());
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
        return 19;
    }

    private int getColorText() {
        if (Keystrokes.Instance.chroma.getValue())
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
