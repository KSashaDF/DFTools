package dfutils.config;

import dfutils.Reference;
import dfutils.utils.rpc.PresenceHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod.EventBusSubscriber
public class ConfigHandler {

    static Configuration config;

    public static boolean DO_QUICK_ITEM_RENAME = true;
    public static boolean DO_VARPURGE_CONFIRM = false;
    public static boolean DO_PLOTCLEAR_CONFIRM = true;
    public static boolean DISCORD_RPC_ENABLED = true;

    public static void init(File configFile) {

        //Creates configuration file.
        config = new Configuration(configFile);

        //Creates configuration categories.
        config.addCustomCategoryComment("Settings", "Enable or disable various features here.");

        //Creates configuration categories.
        config.addCustomCategoryComment("Discord RPC", "Disable or enable certain features from your Discord RPC");

        reloadConfig();
    }

    private static void reloadConfig() {
        DO_QUICK_ITEM_RENAME = config.getBoolean("Quick Item Rename", "Settings", false, "Enable this to make it so you can shift + left click any item to quickly rename it.");
        DO_VARPURGE_CONFIRM = config.getBoolean("/plot varpurge confirm", "Settings", false, "Enable this to make it so you need to type an extra confirmation command to varpurge.");
        DO_PLOTCLEAR_CONFIRM = config.getBoolean("/plot clear confirm", "Settings", true, "Disable this to make it so you do not need to type an extra confirmation command to clear a plot.");
        DISCORD_RPC_ENABLED = config.getBoolean("Enabled", "Discord RPC", true, "Enable or disable Discord RPC.");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            reloadConfig();

            if(DISCORD_RPC_ENABLED) PresenceHandler.updatePresence(); else PresenceHandler.destroyPresence();
        }
    }
}
