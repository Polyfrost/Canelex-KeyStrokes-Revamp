package net.canelex.keystrokes.gui;

import net.canelex.keystrokes.KeystrokesMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.mutable.MutableInt;

import java.awt.*;

public class GuiEditColor extends GuiScreenDrag {
    public boolean showPressed;

    public MutableInt color;

    private final GuiScreen last;

    private GuiSlider red;

    private GuiSlider green;

    private GuiSlider blue;

    private GuiSlider alpha;

    public GuiEditColor(KeystrokesMod mod, MutableInt mutableInt, GuiScreen last, boolean showPressed) {
        super(mod);
        this.color = mutableInt;
        this.last = last;
        this.showPressed = showPressed;
    }

    public void initGui() {
        super.initGui();
        int rgba = this.color.getValue().intValue();
        this.buttonList.add(this.red = new GuiSlider(0, this.width / 2 - 75, this.height / 2 - 50, 150, 20, "Red: ", "", 0.0D, 255.0D, (rgba >> 16 & 0xFF), false, true));
        this.buttonList.add(this.green = new GuiSlider(1, this.width / 2 - 75, this.height / 2 - 25, 150, 20, "Green: ", "", 0.0D, 255.0D, (rgba >> 8 & 0xFF), false, true));
        this.buttonList.add(this.blue = new GuiSlider(2, this.width / 2 - 75, this.height / 2, 150, 20, "Blue: ", "", 0.0D, 255.0D, (rgba & 0xFF), false, true));
        this.buttonList.add(this.alpha = new GuiSlider(3, this.width / 2 - 75, this.height / 2 + 25, 150, 20, "Alpha: ", "", 10.0D, 255.0D, (rgba >> 24 & 0xFF), false, true));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Done"));
    }

    public void drawScreen(int x, int y, float partial) {
        drawDefaultBackground();
        super.drawScreen(x, y, partial);
        updateColor();
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 4)
            this.mc.displayGuiScreen(this.last);
    }

    private void updateColor() {
        int r = this.red.getValueInt();
        int g = this.green.getValueInt();
        int b = this.blue.getValueInt();
        int a = this.alpha.getValueInt();
        this.color.setValue((new Color(r, g, b, a)).getRGB());
    }
}
