package net.canelex.keystrokes.draggable;

import net.canelex.keystrokes.KeystrokesMod;
import net.minecraft.client.Minecraft;

public abstract class DragGui {
    protected Minecraft mc;

    protected KeystrokesMod mod;

    protected int posX;

    protected int posY;

    protected float scale;

    public DragGui(KeystrokesMod mod, int x, int y, float scale) {
        this.mc = Minecraft.getMinecraft();
        this.mod = mod;
        this.posX = x;
        this.posY = y;
        this.scale = scale;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean contains(int x, int y) {
        return (x > this.posX && y > this.posY && x < this.posX + getWidth() * this.scale && y < this.posY + getHeight() * this.scale);
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void drawUI();
}
