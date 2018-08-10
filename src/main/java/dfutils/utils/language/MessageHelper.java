package dfutils.utils.language;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * Contains various utilities for sending different message types. (such as an INFO message)
 */
public class MessageHelper {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    /**
     * Adds a prefix (such as an INFO message prefix) onto the front of an ITextComponent object.
     * */
    private static ITextComponent addPrefix(ITextComponent prefix, ITextComponent message) {
        String prefixText = prefix.getUnformattedText();
        String messageText = ITextComponent.Serializer.componentToJson(message);
        
        //If the prefix is not an instance of TextComponentString,
        //just return the message - there is no reliable way to add the prefix to the message.
        if (prefix instanceof TextComponentString) {
            JsonObject messageJson = new JsonParser().parse(messageText).getAsJsonObject();
            
            //If the message is already colored, do not apply the prefix color! Just append the message
            //ITextComponent to the prefix ITextComponent.
            if (messageJson.has("color")) {
                return prefix.appendSibling(message);
            } else {
                messageJson.addProperty("text", prefixText + messageJson.getAsJsonPrimitive("text").getAsString());
            }
            
            return ITextComponent.Serializer.jsonToComponent(messageJson.toString());
        } else {
            return message;
        }
    }
    
    public static void message(String translationKey) {
        minecraft.player.sendMessage(LanguageManager.getMessage(translationKey));
    }
    
    public static void message(String translationKey, String... variables) {
        minecraft.player.sendMessage(LanguageManager.getMessage(translationKey, variables));
    }
    
    public static void infoMessage(String translationKey) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.info"), LanguageManager.getMessage(translationKey)));
    }
    
    public static void infoMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.info"), LanguageManager.getMessage(translationKey, variables)));
    }
    
    public static void actionMessage(String translationKey) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.action"), LanguageManager.getMessage(translationKey)));
    }
    
    public static void actionMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.action"), LanguageManager.getMessage(translationKey, variables)));
    }
    
    public static void warnMessage(String translationKey) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.warn"), LanguageManager.getMessage(translationKey)));
    }
    
    public static void warnMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.warn"), LanguageManager.getMessage(translationKey, variables)));
    }
    
    public static void errorMessage(String translationKey) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.error"), LanguageManager.getMessage(translationKey)));
        minecraft.player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND, 1, 15);
    }
    
    public static void errorMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.error"), LanguageManager.getMessage(translationKey, variables)));
        minecraft.player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND, 1, 15);
    }
    
    public static void noteMessage(String translationKey) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.note"), LanguageManager.getMessage(translationKey)));
    }
    
    public static void noteMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.note"), LanguageManager.getMessage(translationKey, variables)));
    }
}
