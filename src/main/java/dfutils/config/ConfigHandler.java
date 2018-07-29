package dfutils.config;

import dfutils.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigHandler {

    static Configuration config;

    public static boolean DO_QUICK_ITEM_RENAME = false;

    public static void init(File configFile) {

        //Creates configuration file.
        config = new Configuration(configFile);

        //Creates configuration elements.
        config.addCustomCategoryComment("Settings", "Enable or disable various features here.");

        DO_QUICK_ITEM_RENAME = config.getBoolean("Do Quick Item Rename", "Settings", false, "Enable this to make it so you can shift + left click items to quickly rename them.");

        config.save();
    }

    private static void reloadConfig() {
        DO_QUICK_ITEM_RENAME = config.getBoolean("Do Quick Item Rename", "Settings", false, "Enable this to make it so you can shift + left click items to quickly rename them.");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            reloadConfig();
        }
    }
}
