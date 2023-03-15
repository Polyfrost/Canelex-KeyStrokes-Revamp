package dev.salmon.keystrokes;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.KeyInputEvent;
import cc.polyfrost.oneconfig.events.event.MouseInputEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import dev.salmon.keystrokes.command.KeystrokesCommand;
import dev.salmon.keystrokes.config.KeystrokesConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@Mod(modid = Keystrokes.ID, name = Keystrokes.NAME, version = Keystrokes.VER)
public class Keystrokes {

    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";

    @Mod.Instance(ID)
    public static Keystrokes INSTANCE;
    public KeystrokesConfig config;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        EventManager.INSTANCE.register(this);
        CommandManager.INSTANCE.registerCommand(new KeystrokesCommand());
        config = new KeystrokesConfig();
    }

    @Subscribe
    public void keyboardInput(KeyInputEvent event) {
        if (!Keyboard.getEventKeyState()) return;
        KeystrokesConfig.keystrokesElement.pressed(Keyboard.getEventKey());
    }


    @Subscribe
    public void mouseInput(MouseInputEvent event) {
        if (!Mouse.getEventButtonState()) return;
        KeystrokesConfig.keystrokesElement.pressed(Mouse.getEventButton() - 100);

    }
}

