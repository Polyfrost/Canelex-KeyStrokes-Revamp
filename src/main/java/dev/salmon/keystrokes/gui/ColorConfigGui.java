package dev.salmon.keystrokes.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.apache.commons.lang3.mutable.MutableInt;

import java.awt.*;

public class ColorConfigGui extends DragGui {

    public boolean showPressed;
    public MutableInt color;
    private final GuiScreen last;
    private GuiSlider red, green, blue, alpha;

    public ColorConfigGui(MutableInt mutableInt, GuiScreen last, boolean showPressed) {
        this.color = mutableInt;
        this.last = last;
        this.showPressed = showPressed;
    }

    public void initGui() {
        super.initGui();
        int rgba = this.color.getValue();
        this.buttonList.add(this.red = new GuiSlider(0, this.width / 2 - 75, this.height / 2 - 50, 150, 20, "Red: ", "", 0.0D, 255.0D, (rgba >> 16 & 0xFF), false, true));
        this.buttonList.add(this.green = new GuiSlider(1, this.width / 2 - 75, this.height / 2 - 25, 150, 20, "Green: ", "", 0.0D, 255.0D, (rgba >> 8 & 0xFF), false, true));
        this.buttonList.add(this.blue = new GuiSlider(2, this.width / 2 - 75, this.height / 2, 150, 20, "Blue: ", "", 0.0D, 255.0D, (rgba & 0xFF), false, true));
        this.buttonList.add(this.alpha = new GuiSlider(3, this.width / 2 - 75, this.height / 2 + 25, 150, 20, "Alpha: ", "", 10.0D, 255.0D, (rgba >> 24 & 0xFF), false, true));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 75, this.height / 2 + 50, 150, 20, "Done"));
    }

    public void drawScreen(int x, int y, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(x, y, partialTicks);
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
