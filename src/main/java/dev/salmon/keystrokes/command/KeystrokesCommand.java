package dev.salmon.keystrokes.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import dev.salmon.keystrokes.Keystrokes;

@Command(Keystrokes.ID)
public class KeystrokesCommand {
    @Main
    private static void main() {
        Keystrokes.INSTANCE.config.openGui();
    }
}
