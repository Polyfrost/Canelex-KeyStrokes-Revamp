package dev.salmon.keystrokes.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import dev.salmon.keystrokes.Keystrokes;

@Command(value = Keystrokes.ID, aliases = "keystrokes")
public class KeystrokesCommand {
    @Main
    private void main() {
        Keystrokes.INSTANCE.config.openGui();
    }
}
