package dev.salmon.keystrokes.command;

import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.gui.ConfigGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KeystrokesCommand extends CommandBase {

    public String getCommandName() {
        return Keystrokes.ID;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/" + Keystrokes.ID;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public void processCommand(ICommandSender sender, String[] args) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(new ConfigGUI());
    }

}
