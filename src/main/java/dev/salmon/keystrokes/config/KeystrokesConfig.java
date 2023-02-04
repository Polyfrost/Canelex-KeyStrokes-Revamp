package dev.salmon.keystrokes.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.hud.KeystrokesElement;

public class KeystrokesConfig extends Config {

    @HUD(
            name = "Keystrokes"
    )
    public static KeystrokesElement keystrokesElement = new KeystrokesElement();

    public KeystrokesConfig() {
        super(new Mod(Keystrokes.NAME, ModType.HUD, "/keystrokesrevamp_dark.svg"), "keystrokes.json");
        initialize();
    }
}
