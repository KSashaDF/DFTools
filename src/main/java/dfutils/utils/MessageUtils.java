package dfutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;

public class MessageUtils {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    private static final String INFO_PREFIX = "§3\u258E §bINFO §3§l>> §b";
    private static final String WARN_PREFIX = "§c\u258E §6WARN §c§l>> §e";
    private static final String ERROR_PREFIX = "§4\u258E §cERROR §4§l>> §6";
    private static final String ACTION_PREFIX = "§5\u258E §dACTION §5§l>> §d";
    private static final String NOTE_PREFIX = "§6\u258E§4§k::§c NOTE §4§k::§6> §c§o";

    public static void infoMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(INFO_PREFIX + message));
    }
    
    public static void warnMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(WARN_PREFIX + message));
    }
    
    public static void errorMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ERROR_PREFIX + message));
        minecraft.player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND, 1, 15);
    }
    
    public static void actionMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ACTION_PREFIX + message));
    }

    public static void noteMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(NOTE_PREFIX + message));
    }
}
