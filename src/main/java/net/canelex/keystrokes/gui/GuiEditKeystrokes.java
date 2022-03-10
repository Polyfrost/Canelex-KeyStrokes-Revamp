package net.canelex.keystrokes.gui;

import net.canelex.keystrokes.KeystrokesMod;
import net.canelex.keystrokes.draggable.DragGui;
import net.minecraft.client.gui.GuiButton;

public class GuiEditKeystrokes extends GuiScreenDrag {
    public GuiEditKeystrokes(KeystrokesMod mod) {
        super(mod);
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 105, this.height / 2 - 87, 103, 20, "BG [Key Up]"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 3, this.height / 2 - 87, 103, 20, "Text [Key Up]"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 105, this.height / 2 - 62, 103, 20, "BG [Key Down]"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 3, this.height / 2 - 62, 103, 20, "Text [Key Down]"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 105, this.height / 2 - 37, 210, 20, "Change Mode"));
        this.buttonList.add(new GuiSlider(5, this.width / 2 - 105, this.height / 2 - 12, 210, 20, "Fade Time: ", "", 1.0D, 250.0D, this.mod.fadingTime.getValue(), false, true));
        this.buttonList.add(new GuiSlider(6, this.width / 2 - 105, this.height / 2 + 13, 210, 20, "Scale: ", "%", 0.0D, 200.0D, this.mod.scale.getValue() * 100.0D, false, true));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 105, this.height / 2 + 38, 103, 20, "Toggle CPS"));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 3, this.height / 2 + 38, 103, 20, "Toggle FPS"));
        this.buttonList.add(new GuiButton(9, this.width / 2 - 105, this.height / 2 + 63, 210, 20, "Toggle Chroma"));
        this.buttonList.add(new GuiButton(10, this.width / 2 - 105, this.height / 2 + 88, 210, 20, "Toggle Shadow"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.mod.fadingTime.setValue(((GuiSlider) this.buttonList.get(5)).getValueInt());
        this.mod.scale.setValue(((GuiSlider) this.buttonList.get(6)).getValueInt() / 100.0F);
        for (DragGui gui : this.mod.getDragGuis())
            gui.setScale(this.mod.scale.getValue());
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiEditColor(this.mod, this.mod.bgUnpressed, this, false));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiEditColor(this.mod, this.mod.textUnpressed, this, false));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiEditColor(this.mod, this.mod.bgPressed, this, true));
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiEditColor(this.mod, this.mod.textPressed, this, true));
                break;
            case 4:
                this.mod.getGuiKeystrokes().setMode((this.mod.getGuiKeystrokes().getMode() + 1) % 3);
                break;
            case 7:
                this.mod.getGuiCPS().setEnabled(!this.mod.getGuiCPS().isEnabled());
                break;
            case 8:
                this.mod.getGuiFPS().setEnabled(!this.mod.getGuiFPS().isEnabled());
                break;
            case 9:
                this.mod.chroma.setValue(!this.mod.chroma.getValue());
                break;
            case 10:
                this.mod.shadow.setValue(!this.mod.shadow.getValue());
                break;
        }
    }

    public void onGuiClosed() {
        this.mod.saveSettings();
    }
}
