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
    public static boolean DO_CUSTOM_COLORS = true;
    public static boolean DO_CUSTOM_CHAT = true;
    
    public static boolean DISCORD_RPC_ENABLED = true;
    
    public static boolean SUPPORT_END_AUTOMATIC_LEAVE = false;
    public static String SUPPORT_START_MESSAGE = "";
    public static String SUPPORT_END_MESSAGE = "";

    public static void init(File configFile) {

        //Creates configuration file.
        config = new Configuration(configFile);

        //Creates configuration categories.
        config.addCustomCategoryComment("General", "Enable or disable various features here.");
        config.addCustomCategoryComment("Discord RPC", "Enable or disable certain features from your Discord RPC.");
        config.addCustomCategoryComment("Support", "Enable or disable certain features if you are a support member.");
    
        config.setCategoryLanguageKey("General", "config.general");
        config.setCategoryLanguageKey("Discord RPC", "config.discord");
        config.setCategoryLanguageKey("Support", "config.support");

        // Migrate old settings category
        if (config.hasCategory("settings")) {
            DO_QUICK_ITEM_RENAME = config.getBoolean("Quick Item Rename", "settings", false, "Enable this to make it so you can shift + left click any item to quickly rename it.");
            DO_VARPURGE_CONFIRM = config.getBoolean("/plot varpurge confirm", "settings", false, "Enable this to make it so you need to type an extra confirmation command to varpurge.");
            DO_PLOTCLEAR_CONFIRM = config.getBoolean("/plot clear confirm", "settings", true, "Disable this to make it so you do not need to type an extra confirmation command to clear a plot.");
            config.removeCategory(config.getCategory("settings"));
            config.save();
        }


        reloadConfig();
    }

    private static void reloadConfig() {
        
        String category;
        
        category = "General";
        DO_QUICK_ITEM_RENAME = config.getBoolean("Quick Item Rename", category, false, "Enable this to make it so you can shift + left click any item to quickly rename it.");
        DO_VARPURGE_CONFIRM = config.getBoolean("/plot varpurge confirm", category, false, "Enable this to make it so you need to type an extra confirmation command to varpurge.");
        DO_PLOTCLEAR_CONFIRM = config.getBoolean("/plot clear confirm", category, true, "Disable this to make it so you do not need to type an extra confirmation command to clear a plot.");
        DO_CUSTOM_COLORS = config.getBoolean("Custom Color Codes", category, true, "Disable this to revert the color code system to the vanilla Minecraft system. (NOT RECOMMENDED)");
        DO_CUSTOM_CHAT = config.getBoolean("Chat Utilities", category, true, "Disable this to turn off all chatbox GUI related utilities. (DO THIS IF YOU ARE HAVING MOD COMPATIBILITY ISSUES)");
        
        category = "Discord RPC";
        DISCORD_RPC_ENABLED = config.getBoolean("Enabled", category, true, "Enable or disable Discord RPC.");
        
        category = "Support";
        SUPPORT_END_AUTOMATIC_LEAVE = config.getBoolean("/spawn after session", category, false, "Automatically return to spawn after you finish a session.");
        SUPPORT_START_MESSAGE = config.getString("Support accept message", category, "", "Automatically send a accept message after you accepted a session.\n\n§a%player% §7 - Current player name");
        SUPPORT_END_MESSAGE = config.getString("Support ending message", category, "", "Automatically send an ending message after you finished the current session.\n\n§a%player% §7 - Current player name");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            reloadConfig();

            if (DISCORD_RPC_ENABLED) PresenceHandler.updatePresence(false);
            else PresenceHandler.destroyPresence();
        }
    }
}
