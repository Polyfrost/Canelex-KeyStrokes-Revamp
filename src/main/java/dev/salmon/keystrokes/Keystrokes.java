package dev.salmon.keystrokes;

import dev.salmon.keystrokes.command.KeystrokesCommand;
import dev.salmon.keystrokes.gui.element.CPSElement;
import dev.salmon.keystrokes.gui.element.DragElement;
import dev.salmon.keystrokes.gui.element.FPSElement;
import dev.salmon.keystrokes.gui.element.PingElement;
import dev.salmon.keystrokes.gui.element.keystrokes.KeystrokesElement;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.LinkedHashSet;

@Mod(modid = Keystrokes.ID, name = Keystrokes.NAME, version = Keystrokes.VER)
public class Keystrokes {

    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";

    @Mod.Instance(ID)
    public static Keystrokes Instance;

    private KeystrokesElement keystrokesElement;
    private CPSElement cpsElement;
    private FPSElement fpsElement;
    private PingElement pingElement;
    private LinkedHashSet<DragElement> dragElements;

    public MutableInt bgUnpressed = new MutableInt(-922746880);
    public MutableInt textUnpressed = new MutableInt(-1);
    public MutableInt bgPressed = new MutableInt(-905969665);
    public MutableInt textPressed = new MutableInt(-16777216);
    public MutableInt fadingTime = new MutableInt(100);
    public MutableFloat scale = new MutableFloat(1.0F);
    public MutableBoolean chroma = new MutableBoolean(false);
    public MutableBoolean shadow = new MutableBoolean(true);
    public MutableBoolean cpsTitle = new MutableBoolean(false);

    public File saveFile;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.saveFile = new File(Minecraft.getMinecraft().mcDataDir, "canelexkeystrokes.cfg");
        (this.dragElements = new LinkedHashSet<>()).add(keystrokesElement = new KeystrokesElement(5, 5, 1.0F));
        this.dragElements.add(fpsElement = new FPSElement(70, 13, 1.0f));
        this.dragElements.add(cpsElement = new CPSElement(70, 13, 1.0f));
        this.dragElements.add(pingElement = new PingElement(70, 13, 1.0f));

        this.loadConfig();

        ClientCommandHandler.instance.registerCommand(new KeystrokesCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL && Minecraft.getMinecraft().currentScreen == null && !Minecraft.getMinecraft().gameSettings.showDebugInfo)
            renderGUI();
    }

    public void renderGUI() {
        for (DragElement gui : this.dragElements) {
            GL11.glTranslatef(-gui.getPosX() * (gui.getScale() - 1.0F), -gui.getPosY() * (gui.getScale() - 1.0F), 0.0F);
            GL11.glScalef(gui.getScale(), gui.getScale(), 1.0F);
            gui.drawUI();
            GL11.glScalef(1.0F / gui.getScale(), 1.0F / gui.getScale(), 1.0F);
            GL11.glTranslatef(gui.getPosX() * (gui.getScale() - 1.0F), gui.getPosY() * (gui.getScale() - 1.0F), 0.0F);
        }
    }

    // instances..

    public LinkedHashSet<DragElement> getDragElements() {
        return this.dragElements;
    }

    public KeystrokesElement getKeystrokesElement() {
        return this.keystrokesElement;
    }

    public CPSElement getCpsElement() {
        return this.cpsElement;
    }

    public FPSElement getFpsElement() {
        return this.fpsElement;
    }

    public PingElement getPingElement() {
        return this.pingElement;
    }

    // config...

    public void saveConfig() {
        Configuration config = new Configuration(this.saveFile);
        updateConfig(config, true);
        config.save();
    }

    public void loadConfig() {
        Configuration config = new Configuration(this.saveFile);
        config.load();
        updateConfig(config, false);
        this.keystrokesElement.setScale(this.scale.getValue());
        this.cpsElement.setScale(this.scale.getValue());
        this.fpsElement.setScale(this.scale.getValue());
    }

    public void updateConfig(Configuration config, boolean save) {
        Property prop;

        prop = config.get("global", "bgUnpressed", -922746880);
        if (save) prop.set(this.bgUnpressed.getValue());
        else this.bgUnpressed = new MutableInt(prop.getInt());

        prop = config.get("global", "bgPressed", -905969665);
        if (save) prop.set(this.bgPressed.getValue());
        else this.bgPressed = new MutableInt(prop.getInt());

        prop = config.get("global", "textUnpressed", -1);
        if (save) prop.set(this.textUnpressed.getValue());
        else this.textUnpressed = new MutableInt(prop.getInt());

        prop = config.get("global", "textPressed", -16777216);
        if (save) prop.set(this.textPressed.getValue());
        else this.textPressed = new MutableInt(prop.getInt());

        prop = config.get("global", "chroma", false);
        if (save) prop.set(this.chroma.getValue());
        else this.chroma = new MutableBoolean(prop.getBoolean());

        prop = config.get("global", "shadow", true);
        if (save) prop.set(this.shadow.getValue());
        else this.shadow = new MutableBoolean(prop.getBoolean());

        prop = config.get("global", "fadingtime", 100);
        if (save) prop.set(this.fadingTime.getValue());
        else this.fadingTime = new MutableInt(prop.getInt());

        prop = config.get("global", "scale", 1.0D);
        if (save) prop.set(this.scale.getValue());
        else this.scale = new MutableFloat(prop.getDouble());

        prop = config.get("guiKeystrokes", "mode", 0);
        if (save) prop.set(this.keystrokesElement.getMode());
        else this.keystrokesElement.setMode(prop.getInt());

        prop = config.get("guiKeystrokes", "posX", 5);
        if (save) prop.set(this.keystrokesElement.getPosX());
        else this.keystrokesElement.setPosX(prop.getInt());

        prop = config.get("guiKeystrokes", "posY", 5);
        if (save) prop.set(this.keystrokesElement.getPosY());
        else this.keystrokesElement.setPosY(prop.getInt());

        prop = config.get("cpsmod", "enabled", true);
        if (save) prop.set(this.cpsElement.isEnabled());
        else this.cpsElement.setEnabled(prop.getBoolean());

        prop = config.get("cpsmod", "cpsTitle", false);
        if (save) prop.set(this.cpsTitle.getValue());
        else this.cpsTitle = new MutableBoolean(prop.getBoolean());

        prop = config.get("cpsmod", "posX", 5);
        if (save) prop.set(this.cpsElement.getPosX());
        else this.cpsElement.setPosX(prop.getInt());

        prop = config.get("cpsmod", "posY", 45);
        if (save) prop.set(this.cpsElement.getPosY());
        else this.cpsElement.setPosY(prop.getInt());

        prop = config.get("fpsmod", "enabled", true);
        if (save) prop.set(this.fpsElement.isEnabled());
        else this.fpsElement.setEnabled(prop.getBoolean());

        prop = config.get("fpsmod", "posX", 5);
        if (save) prop.set(this.fpsElement.getPosX());
        else this.fpsElement.setPosX(prop.getInt());

        prop = config.get("fpsmod", "posY", 60);
        if (save) prop.set(this.fpsElement.getPosY());
        else this.fpsElement.setPosY(prop.getInt());

        prop = config.get("pingmod", "enabled", true);
        if (save) prop.set(this.pingElement.isEnabled());
        else this.pingElement.setEnabled(prop.getBoolean());

        prop = config.get("pingmod", "posX", 5);
        if (save) prop.set(this.pingElement.getPosX());
        else this.pingElement.setPosX(prop.getInt());

        prop = config.get("pingmod", "posY", 75);
        if (save) prop.set(this.fpsElement.getPosY());
        else this.pingElement.setPosY(prop.getInt());
    }

}

