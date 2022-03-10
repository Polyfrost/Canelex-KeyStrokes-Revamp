package net.canelex.keystrokes.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.opengl.GL11;

public class GuiSlider extends GuiButtonExt {
    public double sliderValue;

    public String dispString;

    public boolean dragging;

    public boolean showDecimal;

    public double minValue;

    public double maxValue;

    public int precision;

    public ISlider parent;

    public String suffix;

    public boolean drawString;

    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        this(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, null);
    }

    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par) {
        super(id, xPos, yPos, width, height, prefix);
        String val;
        this.dragging = false;
        this.precision = 1;
        this.drawString = true;
        this.minValue = minVal;
        this.maxValue = maxVal;
        this.sliderValue = (currentVal - this.minValue) / (this.maxValue - this.minValue);
        this.dispString = prefix;
        this.parent = par;
        this.suffix = suf;
        this.showDecimal = showDec;
        if (this.showDecimal) {
            val = Double.toString(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
            this.precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
        } else {
            val = Integer.toString((int) Math.round(this.sliderValue * (this.maxValue - this.minValue) + this.minValue));
            this.precision = 0;
        }
        this.displayString = this.dispString + val + this.suffix;
        this.drawString = drawStr;
        if (!this.drawString)
            this.displayString = "";
    }

    public GuiSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par) {
        this(id, xPos, yPos, 150, 20, displayStr, "", minVal, maxVal, currentVal, true, true, par);
    }

    public int getHoverState(boolean par1) {
        return 0;
    }

    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = ((par2 - this.xPosition + 4) / (this.width - 8));
                updateSlider();
            }
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.sliderValue = ((par2 - this.xPosition + 4) / (this.width - 8));
            updateSlider();
            return this.dragging = true;
        }
        return false;
    }

    public void updateSlider() {
        String val;
        if (this.sliderValue < 0.0D)
            this.sliderValue = 0.0D;
        if (this.sliderValue > 1.0D)
            this.sliderValue = 1.0D;
        if (this.showDecimal) {
            val = Double.toString(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
            if (val.substring(val.indexOf(".") + 1).length() > this.precision) {
                if ((val = val.substring(0, val.indexOf(".") + this.precision + 1)).endsWith("."))
                    val = val.substring(0, val.indexOf(".") + this.precision);
            } else {
                while (val.substring(val.indexOf(".") + 1).length() < this.precision)
                    val = val + "0";
            }
        } else {
            val = Integer.toString((int) Math.round(this.sliderValue * (this.maxValue - this.minValue) + this.minValue));
        }
        if (this.drawString)
            this.displayString = this.dispString + val + this.suffix;
        if (this.parent != null)
            this.parent.onChangeSliderValue(this);
    }

    public void mouseReleased(int par1, int par2) {
        this.dragging = false;
    }

    public int getValueInt() {
        return (int) Math.round(this.sliderValue * (this.maxValue - this.minValue) + this.minValue);
    }

    public double getValue() {
        return this.sliderValue * (this.maxValue - this.minValue) + this.minValue;
    }

    public void setValue(double d) {
        this.sliderValue = (d - this.minValue) / (this.maxValue - this.minValue);
    }

    public interface ISlider {
        void onChangeSliderValue(GuiSlider param1GuiSlider);
    }
}
