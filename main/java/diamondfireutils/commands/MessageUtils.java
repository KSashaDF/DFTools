package diamondfireutils.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class MessageUtils {
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    private static final String ERROR_PREFIX = "§8§l<§4ERROR§8§l> §c";
    private static final String INFO_PREFIX = "§8§l<§6INFO§8§l> §e";
    private static final String ACTION_PREFIX = "§8§l<§5ACTION§8§l> §d";
    
    public static void commandError(String message) {
        minecraft.player.sendMessage(new TextComponentString(ERROR_PREFIX + message));
    }
    
    public static void commandInfo(String message) {
        minecraft.player.sendMessage(new TextComponentString(INFO_PREFIX + message));
    }
    
    public static void commandAction(String message) {
        minecraft.player.sendMessage(new TextComponentString(ACTION_PREFIX + message));
    }
}
