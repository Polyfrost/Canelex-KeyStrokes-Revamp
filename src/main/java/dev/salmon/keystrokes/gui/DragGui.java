package dev.salmon.keystrokes.gui;

import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.gui.element.DragElement;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class DragGui extends GuiScreen {

    protected DragElement dragElements;
    protected int lastX, lastY;

    public void initGui() {
        for (DragElement element : Keystrokes.Instance.getDragElements())
            fitGuiIntoScreen(element);
    }

    public void drawScreen(int x, int y, float partial) {
        super.drawScreen(x, y, partial);
        Keystrokes.Instance.renderGUI();
        if (this.dragElements != null) {
            this.dragElements.setPosX(this.dragElements.getPosX() + x - this.lastX);
            this.dragElements.setPosY(this.dragElements.getPosY() + y - this.lastY);
            fitGuiIntoScreen(this.dragElements);
        }
        this.lastX = x;
        this.lastY = y;
    }

    protected void mouseClicked(int x, int y, int button) throws IOException {
        super.mouseClicked(x, y, button);
        for (DragElement elements : Keystrokes.Instance.getDragElements()) {
            if (x >= elements.getPosX() && y >= elements.getPosY() && x <= elements.getPosX() + elements.getWidth() * elements.getScale() && y <= elements.getPosY() + elements.getHeight() * elements.getScale()) {
                this.dragElements = elements;
                this.lastX = x;
                this.lastY = y;
                break;
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragElements = null;
    }

    private void fitGuiIntoScreen(DragElement elements) {
        elements.setPosX(Math.min(this.width - (int)(elements.getWidth() * elements.getScale()), Math.max(0, elements.getPosX())));
        elements.setPosY(Math.min(this.height - (int)(elements.getHeight() * elements.getScale()), Math.max(0, elements.getPosY())));
    }

}
