package dev.salmon.keystrokes.gui.element;

public abstract class DragElement {

    protected int posX, posY;
    protected float scale;

    public DragElement(int x, int y, float scale) {
        this.posX = x;
        this.posY = y;
        this.scale = scale;
    }

    public int getPosX() {
        return this.posX;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return this.posY;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public float getScale() {
        return this.scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract void drawUI();

}
