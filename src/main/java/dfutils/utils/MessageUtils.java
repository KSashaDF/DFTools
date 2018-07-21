package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class MessageUtils {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    private static final String ERROR_PREFIX = "§4\u258E §c§lERROR §4> §c";
    private static final String INFO_PREFIX = "§1\u258E §9§lINFO §1> §9";
    private static final String ACTION_PREFIX = "§b\u258E §d§lACTION §b> §d";
    
    public static void errorMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ERROR_PREFIX + message));
    }
    
    public static void infoMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(INFO_PREFIX + message));
    }
    
    public static void actionMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ACTION_PREFIX + message));
    }
}
