package net.canelex.keystrokes;

import net.canelex.keystrokes.command.CommandEditStrokes;
import net.canelex.keystrokes.draggable.DragCPS;
import net.canelex.keystrokes.draggable.DragFPS;
import net.canelex.keystrokes.draggable.DragGui;
import net.canelex.keystrokes.draggable.keystrokes.DragKeystrokes;
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

@Mod(modid = KeystrokesMod.ID, name = KeystrokesMod.NAME, version = KeystrokesMod.VER)
public class KeystrokesMod {

    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";
    public MutableInt bgUnpressed = new MutableInt(-922746880);
    public MutableInt textUnpressed = new MutableInt(-1);
    public MutableInt bgPressed = new MutableInt(-905969665);
    public MutableInt textPressed = new MutableInt(-16777216);
    public MutableInt fadingTime = new MutableInt(100);
    public MutableFloat scale = new MutableFloat(1.0F);
    public MutableBoolean chroma = new MutableBoolean(false);
    public MutableBoolean shadow = new MutableBoolean(true);
    private Minecraft mc;
    private File saveFile;
    private DragKeystrokes guiKeystrokes;
    private DragFPS guiFPS;
    private DragCPS guiCPS;
    private LinkedHashSet<DragGui> dragGuis;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.mc = Minecraft.getMinecraft();
        this.saveFile = new File(this.mc.mcDataDir, "canelexkeystrokes.cfg");
        (this.dragGuis = new LinkedHashSet<>()).add(this.guiKeystrokes = new DragKeystrokes(this, 5, 5, 1.0F));
        this.dragGuis.add(this.guiFPS = new DragFPS(this, 70, 5, 1.0F));
        this.dragGuis.add(this.guiCPS = new DragCPS(this, 130, 5, 1.0F));
        loadSettings();
        ClientCommandHandler.instance.registerCommand(new CommandEditStrokes(this));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL && this.mc.currentScreen == null && !this.mc.gameSettings.showDebugInfo)
            renderGUI();
    }

    public LinkedHashSet<DragGui> getDragGuis() {
        return this.dragGuis;
    }

    public DragKeystrokes getGuiKeystrokes() {
        return this.guiKeystrokes;
    }

    public DragCPS getGuiCPS() {
        return this.guiCPS;
    }

    public DragFPS getGuiFPS() {
        return this.guiFPS;
    }

    public void renderGUI() {
        for (DragGui gui : this.dragGuis) {
            GL11.glTranslatef(-gui.getPosX() * (gui.getScale() - 1.0F), -gui.getPosY() * (gui.getScale() - 1.0F), 0.0F);
            GL11.glScalef(gui.getScale(), gui.getScale(), 1.0F);
            gui.drawUI();
            GL11.glScalef(1.0F / gui.getScale(), 1.0F / gui.getScale(), 1.0F);
            GL11.glTranslatef(gui.getPosX() * (gui.getScale() - 1.0F), gui.getPosY() * (gui.getScale() - 1.0F), 0.0F);
        }
    }

    public void saveSettings() {
        Configuration config = new Configuration(this.saveFile);
        updateSettings(config, true);
        config.save();
    }

    public void loadSettings() {
        Configuration config = new Configuration(this.saveFile);
        config.load();
        updateSettings(config, false);
        this.guiKeystrokes.setScale(this.scale.getValue());
        this.guiCPS.setScale(this.scale.getValue());
        this.guiFPS.setScale(this.scale.getValue());
    }

    private void updateSettings(Configuration config, boolean save) {
        Property prop = config.get("global", "bgUnpressed", -922746880);
        if (save) {
            prop.set(this.bgUnpressed.getValue());
        } else {
            this.bgUnpressed = new MutableInt(prop.getInt());
        }
        prop = config.get("global", "bgPressed", -905969665);
        if (save) {
            prop.set(this.bgPressed.getValue());
        } else {
            this.bgPressed = new MutableInt(prop.getInt());
        }
        prop = config.get("global", "textUnpressed", -1);
        if (save) {
            prop.set(this.textUnpressed.getValue());
        } else {
            this.textUnpressed = new MutableInt(prop.getInt());
        }
        prop = config.get("global", "textPressed", -16777216);
        if (save) {
            prop.set(this.textPressed.getValue());
        } else {
            this.textPressed = new MutableInt(prop.getInt());
        }
        prop = config.get("global", "chroma", false);
        if (save) {
            prop.set(this.chroma.getValue());
        } else {
            this.chroma = new MutableBoolean(prop.getBoolean());
        }
        prop = config.get("global", "shadow", true);
        if (save) {
            prop.set(this.shadow.getValue());
        } else {
            this.shadow = new MutableBoolean(prop.getBoolean());
        }
        prop = config.get("global", "fadingtime", 100);
        if (save) {
            prop.set(this.fadingTime.getValue());
        } else {
            this.fadingTime = new MutableInt(prop.getInt());
        }
        prop = config.get("global", "scale", 1.0D);
        if (save) {
            prop.set(this.scale.getValue());
        } else {
            this.scale = new MutableFloat(prop.getDouble());
        }
        prop = config.get("guiKeystrokes", "mode", 0);
        if (save) {
            prop.set(this.guiKeystrokes.getMode());
        } else {
            this.guiKeystrokes.setMode(prop.getInt());
        }
        prop = config.get("guiKeystrokes", "posX", 5);
        if (save) {
            prop.set(this.guiKeystrokes.getPosX());
        } else {
            this.guiKeystrokes.setPosX(prop.getInt());
        }
        prop = config.get("guiKeystrokes", "posY", 5);
        if (save) {
            prop.set(this.guiKeystrokes.getPosY());
        } else {
            this.guiKeystrokes.setPosY(prop.getInt());
        }
        prop = config.get("cpsmod", "enabled", true);
        if (save) {
            prop.set(this.guiCPS.isEnabled());
        } else {
            this.guiCPS.setEnabled(prop.getBoolean());
        }
        prop = config.get("cpsmod", "posX", 5);
        if (save) {
            prop.set(this.guiCPS.getPosX());
        } else {
            this.guiCPS.setPosX(prop.getInt());
        }
        prop = config.get("cpsmod", "posY", 45);
        if (save) {
            prop.set(this.guiCPS.getPosY());
        } else {
            this.guiCPS.setPosY(prop.getInt());
        }
        prop = config.get("fpsmod", "enabled", true);
        if (save) {
            prop.set(this.guiFPS.isEnabled());
        } else {
            this.guiFPS.setEnabled(prop.getBoolean());
        }
        prop = config.get("fpsmod", "posX", 5);
        if (save) {
            prop.set(this.guiFPS.getPosX());
        } else {
            this.guiFPS.setPosX(prop.getInt());
        }
        prop = config.get("fpsmod", "posY", 60);
        if (save) {
            prop.set(this.guiFPS.getPosY());
        } else {
            this.guiFPS.setPosY(prop.getInt());
        }
    }
}
