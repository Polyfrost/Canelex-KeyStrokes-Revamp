package dev.salmon.keystrokes.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.core.ConfigUtils;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.elements.BasicOption;
import cc.polyfrost.oneconfig.gui.elements.config.ConfigHeader;
import cc.polyfrost.oneconfig.gui.elements.config.ConfigSwitch;
import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.hud.KeystrokesElement;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class KeystrokesConfig extends Config {

    @HUD(
            name = "Keystrokes"
    )
    public static KeystrokesElement keystrokesElement = new KeystrokesElement();

    public KeystrokesConfig() {
        super(new Mod(Keystrokes.NAME, ModType.HUD), "keystrokes.json");
        initialize();
        Field keystrokesElementField;
        HUD hudAnnotation;
        try {
            keystrokesElementField = getClass().getDeclaredField("keystrokesElement");
            hudAnnotation = keystrokesElementField.getAnnotation(HUD.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return;
        }
        String category = hudAnnotation.category();
        String subcategory = hudAnnotation.subcategory();
        ArrayList<BasicOption> options = ConfigUtils.getSubCategory(mod.defaultPage, hudAnnotation.category(), hudAnnotation.subcategory()).options;
        try { // we cant do anything about options we dont need being serialized, but we can stop them from showing up in the gui
            options.clear();
            options.add(new ConfigHeader(keystrokesElementField, keystrokesElement, hudAnnotation.name(), category, subcategory, 2));
            options.add(new ConfigSwitch(keystrokesElement.getClass().getField("enabled"), keystrokesElement, "Enabled", category, subcategory, 2));
            options.addAll(ConfigUtils.getClassOptions(keystrokesElement));
        } catch (NoSuchFieldException ignored) {
        }
    }
}
