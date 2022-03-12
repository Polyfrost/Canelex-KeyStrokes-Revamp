package dev.salmon.keystrokes.gui;

import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.gui.element.DragElement;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiSlider;

public class ConfigGUI extends DragGui {

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 105, this.height / 2 - 87, 103, 20, "BG [Key Up]"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 3, this.height / 2 - 87, 103, 20, "Text [Key Up]"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 105, this.height / 2 - 62, 103, 20, "BG [Key Down]"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 3, this.height / 2 - 62, 103, 20, "Text [Key Down]"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 105, this.height / 2 - 37, 210, 20, "Change Mode"));
        this.buttonList.add(new GuiSlider(5, this.width / 2 - 105, this.height / 2 - 12, 210, 20, "Fade Time: ", "ms", 1.0D, 250.0D, Keystrokes.Instance.fadingTime.getValue(), false, true));
        this.buttonList.add(new GuiSlider(6, this.width / 2 - 105, this.height / 2 + 13, 210, 20, "Scale: ", "%", 0.0D, 200.0D, Keystrokes.Instance.scale.getValue() * 100.0D, false, true));
        this.buttonList.add(new GuiButton(7, this.width / 2 - 105, this.height / 2 + 38, 103, 20, "Toggle CPS"));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 3, this.height / 2 + 38, 103, 20, "Toggle FPS"));
        this.buttonList.add(new GuiButton(9, this.width / 2 - 105, this.height / 2 + 63, 210, 20, "Toggle Chroma"));
        this.buttonList.add(new GuiButton(10, this.width / 2 - 105, this.height / 2 + 88, 210, 20, "Toggle Shadow"));
        this.buttonList.add(new GuiButton(11, this.width / 2 - 105, this.height / 2 + 113, 103, 20, "Toggle CPS indicator"));
        this.buttonList.add(new GuiButton(12, this.width / 2 - 3, this.height / 2 + 113, 103, 20, "Toggle Ping Indicator"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);
        Keystrokes.Instance.fadingTime.setValue(((GuiSlider) this.buttonList.get(5)).getValueInt());
        Keystrokes.Instance.scale.setValue(((GuiSlider) this.buttonList.get(6)).getValueInt() / 100.0F);
        for (DragElement element : Keystrokes.Instance.getDragElements())
            element.setScale(Keystrokes.Instance.scale.getValue());
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new ColorConfigGui(Keystrokes.Instance.bgUnpressed, this, false));
                break;
            case 1:
                this.mc.displayGuiScreen(new ColorConfigGui(Keystrokes.Instance.textUnpressed, this, false));
                break;
            case 2:
                this.mc.displayGuiScreen(new ColorConfigGui(Keystrokes.Instance.bgPressed, this, true));
                break;
            case 3:
                this.mc.displayGuiScreen(new ColorConfigGui(Keystrokes.Instance.textPressed, this, true));
                break;
            case 4:
                Keystrokes.Instance.getKeystrokesElement().setMode((Keystrokes.Instance.getKeystrokesElement().getMode() + 1) % 3);
            case 7:
                Keystrokes.Instance.getCpsElement().setEnabled(!Keystrokes.Instance.getCpsElement().isEnabled());
                break;
            case 8:
                Keystrokes.Instance.getFpsElement().setEnabled(!Keystrokes.Instance.getFpsElement().isEnabled());
                break;
            case 9:
                Keystrokes.Instance.chroma.setValue(!Keystrokes.Instance.chroma.getValue());
                break;
            case 10:
                Keystrokes.Instance.shadow.setValue(!Keystrokes.Instance.shadow.getValue());
                break;
            case 11:
                Keystrokes.Instance.cpsTitle.setValue(!Keystrokes.Instance.cpsTitle.getValue());
                break;
            case 12:
                Keystrokes.Instance.getPingElement().setEnabled(!Keystrokes.Instance.getPingElement().isEnabled());
                break;
        }
    }

    public void onGuiClosed() {
        Keystrokes.Instance.saveConfig();
    }

}
