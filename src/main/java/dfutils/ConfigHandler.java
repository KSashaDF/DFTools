package dfutils;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler {

    private static Configuration config;

    public static boolean DO_QUICK_ITEM_RENAME = false;

    static void init(FMLPreInitializationEvent event) {

        //Creates the mod configuration directory.
        File configDir = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
        configDir.mkdirs();

        //Creates configuration file.
        config = new Configuration(new File(configDir, Reference.MOD_ID + ".cfg"));

        //Creates configuration elements.
        config.addCustomCategoryComment("Settings", "Enable or disable various features here.");

        DO_QUICK_ITEM_RENAME = config.getBoolean("Do Quick Item Rename", "Settings", false, "Enable this to make it so you can shift + left click items to quickly rename them.");

        config.save();
    }
}
