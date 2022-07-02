package dev.salmon.keystrokes;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import dev.salmon.keystrokes.command.KeystrokesCommand;
import dev.salmon.keystrokes.config.KeystrokesConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Keystrokes.ID, name = Keystrokes.NAME, version = Keystrokes.VER)
public class Keystrokes {

    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";

    @Mod.Instance(ID)
    public static Keystrokes INSTANCE;
    public KeystrokesConfig config;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CommandManager.INSTANCE.registerCommand(KeystrokesCommand.class);
        config = new KeystrokesConfig();
    }

}

