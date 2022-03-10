package net.canelex.keystrokes.command;

import net.canelex.keystrokes.KeystrokesMod;
import net.canelex.keystrokes.gui.GuiEditKeystrokes;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CommandEditStrokes extends CommandBase {
    private final KeystrokesMod mod;

    public CommandEditStrokes(KeystrokesMod mod) {
        this.mod = mod;
    }

    public String getCommandName() {
        return KeystrokesMod.ID;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/" + KeystrokesMod.ID;
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
        Minecraft.getMinecraft().displayGuiScreen(new GuiEditKeystrokes(this.mod));
    }
}
