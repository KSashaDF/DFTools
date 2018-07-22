package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class MessageUtils {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    private static final String ERROR_PREFIX = "§4\u258E §c§lERROR §4» §c";
    private static final String INFO_PREFIX = "§9\u258E §3§lINFO §9» §3";
    private static final String ACTION_PREFIX = "§5\u258E §d§lACTION §5» §d";
    private static final String NOTE_PREFIX = "§6\u258E§4§k::§c§l NOTE §4§k::§6> §c§o";
    
    public static void errorMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ERROR_PREFIX + message));
    }
    
    public static void infoMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(INFO_PREFIX + message));
    }
    
    public static void actionMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ACTION_PREFIX + message));
    }

    public static void noteMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(NOTE_PREFIX + message));
    }
}
