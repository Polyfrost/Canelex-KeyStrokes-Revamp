package net.canelex.keystrokes.gui;

import net.canelex.keystrokes.KeystrokesMod;
import net.canelex.keystrokes.draggable.DragGui;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiScreenDrag extends GuiScreen {
    protected KeystrokesMod mod;

    protected DragGui draggedGui;

    protected int lastX;

    protected int lastY;

    public GuiScreenDrag(KeystrokesMod mod) {
        this.mod = mod;
    }

    public void initGui() {
        for (DragGui gui : this.mod.getDragGuis())
            fitGuiIntoScreen(gui);
    }

    public void drawScreen(int x, int y, float partial) {
        super.drawScreen(x, y, partial);
        this.mod.renderGUI();
        if (this.draggedGui != null) {
            this.draggedGui.setPosX(this.draggedGui.getPosX() + x - this.lastX);
            this.draggedGui.setPosY(this.draggedGui.getPosY() + y - this.lastY);
            fitGuiIntoScreen(this.draggedGui);
        }
        this.lastX = x;
        this.lastY = y;
    }

    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        for (DragGui gui : this.mod.getDragGuis()) {
            if (x >= gui.getPosX() && y >= gui.getPosY() && x <= gui.getPosX() + gui.getWidth() * gui.getScale() && y <= gui.getPosY() + gui.getHeight() * gui.getScale()) {
                this.draggedGui = gui;
                this.lastX = x;
                this.lastY = y;
                break;
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.draggedGui = null;
    }

    private void fitGuiIntoScreen(DragGui gui) {
        gui.setPosX(Math.min(this.width - (int) (gui.getWidth() * gui.getScale()), Math.max(0, gui.getPosX())));
        gui.setPosY(Math.min(this.height - (int) (gui.getHeight() * gui.getScale()), Math.max(0, gui.getPosY())));
    }
}
