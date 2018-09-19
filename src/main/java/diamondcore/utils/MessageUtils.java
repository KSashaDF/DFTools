package diamondcore.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;

public class MessageUtils {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    private static final String INFO_PREFIX = "§b❱§3❱ §b";
    private static final String WARN_PREFIX = "§6❱§c❱ §e";
    private static final String ERROR_PREFIX = "§c❱§4❱ §c";
    private static final String ACTION_PREFIX = "§d❱§5❱ §d";
    private static final String NOTE_PREFIX = "§6❱§b❱ §e";
    private static final String SUCCESS_PREFIX = "§a❱§2❱ §a";

    public static void infoMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(INFO_PREFIX + message));
    }

    public static void infoMessage(String message, boolean playSound) {
        minecraft.player.sendMessage(new TextComponentString(INFO_PREFIX + message));
        if (playSound) minecraft.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1f, 0f);
    }
    
    public static void warnMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(WARN_PREFIX + message));
    }
    
    public static void errorMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ERROR_PREFIX + message));
        minecraft.player.playSound(SoundEvents.ENTITY_CAT_HURT, 1f, 1f);
    }

    public static void successMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(SUCCESS_PREFIX + message));
    }

    public static void successMessage(String message, boolean playSound) {
        minecraft.player.sendMessage(new TextComponentString(SUCCESS_PREFIX + message));
        if (playSound) minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 0.75f, -2f);
    }
    
    public static void actionMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(ACTION_PREFIX + message));
    }

    public static void noteMessage(String message) {
        minecraft.player.sendMessage(new TextComponentString(NOTE_PREFIX + message));
    }
}