package diamondcore.utils.language;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;

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

    public static void infoMessage(String translationKey, boolean playSound) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.info"), LanguageManager.getMessage(translationKey)));
        if (playSound) minecraft.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1f, 0f);
    }

    public static void infoMessage(String translationKey, boolean playSound, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.info"), LanguageManager.getMessage(translationKey, variables)));
        if (playSound) minecraft.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1f, 0f);
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
        minecraft.player.playSound(SoundEvents.ENTITY_CAT_HURT, 1f, 1f);
    }
    
    public static void errorMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.error"), LanguageManager.getMessage(translationKey, variables)));
        minecraft.player.playSound(SoundEvents.ENTITY_CAT_HURT, 1f, 1f);
    }
    
    public static void noteMessage(String translationKey) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.note"), LanguageManager.getMessage(translationKey)));
    }
    
    public static void noteMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.note"), LanguageManager.getMessage(translationKey, variables)));
    }

    public static void successMessage(String translationKey) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.success"), LanguageManager.getMessage(translationKey)));
    }

    public static void successMessage(String translationKey, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.success"), LanguageManager.getMessage(translationKey, variables)));
    }

    public static void successMessage(String translationKey, boolean playSound) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.success"), LanguageManager.getMessage(translationKey)));
        if (playSound) minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, -2f);
    }

    public static void successMessage(String translationKey, boolean playSound, String... variables) {
        minecraft.player.sendMessage(addPrefix(LanguageManager.getMessage("prefix.success"), LanguageManager.getMessage(translationKey, variables)));
        if (playSound) minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, -2f);
    }

    /**
     * Sends a command help message to the player.
     *
     * @param translationKey The command to get.
     */
    public static void commandHelp(String translationKey) {
        String title = LanguageManager.getString("command." + translationKey + ".header.title");
        String subtitle = LanguageManager.getString("command." + translationKey + ".header.subTitle");

        String command = LanguageManager.getString("command." + translationKey + ".header.command");

        String mainColor = LanguageManager.getString("command." + translationKey + ".header.mainColor");
        String subColor = LanguageManager.getString("command." + translationKey + ".header.subColor");

        String paramColor = LanguageManager.getString("command." + translationKey + ".header.paramColor");
        String paramSubColor = LanguageManager.getString("command." + translationKey + ".header.paramSubColor");

        String descriptionColor = LanguageManager.getString("command." + translationKey + ".header.descriptionColor");

        JsonArray parameters = LanguageManager.getArray("command." + translationKey + ".parameters");

        minecraft.player.sendMessage(new TextComponentString(mainColor + "§m          " + subColor + " [ " + mainColor + title + subColor + " - " + mainColor + subtitle + " " + subColor + "] " + subColor + "§m          "));
        minecraft.player.sendMessage(new TextComponentString(""));

        boolean isOptional = false;
        String parameterString = "";
        ArrayList<String> parametersDone = new ArrayList<String>();
        ArrayList<String> parameterDescriptionsDone = new ArrayList<String>();
        for (int i = 0; i < parameters.size(); i++) {
            if (parameters.get(i).getAsJsonObject().has("optional")) {
                isOptional = parameters.get(i).getAsJsonObject().get("optional").getAsBoolean();
            } else isOptional = false;
            if (!parameters.get(i).getAsJsonObject().get("name").isJsonArray()) {
                if (!isOptional) {
                    parametersDone.add(paramColor + "[" + paramSubColor + parameters.get(i).getAsJsonObject().get("name").getAsString() + paramColor + "] ");
                    parameterString = parameterString + parametersDone.get(i);
                } else {
                    parametersDone.add(paramColor + "<" + paramSubColor + parameters.get(i).getAsJsonObject().get("name").getAsString() + paramColor + "> ");
                    parameterString = parameterString + parametersDone.get(i);
                }
            } else {
                String parameterNameString = "";
                for (int i1 = 0; i1 < parameters.get(i).getAsJsonObject().get("name").getAsJsonArray().size(); i1++) {
                    if (i1 + 1 != parameters.get(i).getAsJsonObject().get("name").getAsJsonArray().size()) {
                        parameterNameString = parameterNameString + paramSubColor + parameters.get(i).getAsJsonObject().get("name").getAsJsonArray().get(i1).getAsString() + paramColor + " | ";
                    } else {
                        parameterNameString = parameterNameString + paramSubColor + parameters.get(i).getAsJsonObject().get("name").getAsJsonArray().get(i1).getAsString();
                    }
                }
                if (!isOptional) {
                    parametersDone.add(paramColor + "[" + paramSubColor + parameterNameString + paramColor + "] ");
                    parameterNameString = parametersDone.get(i);
                } else {
                    parametersDone.add(paramColor + "<" + paramSubColor + parameterNameString + paramColor + "> ");
                    parameterNameString = parametersDone.get(i);
                }
                parameterString = parameterString + parameterNameString;
            }

            String parameterDescriptionsString = "";
            if (parameters.get(i).getAsJsonObject().get("description").isJsonArray()) {
                for (int i1 = 0; i1 < parameters.get(i).getAsJsonObject().get("description").getAsJsonArray().size(); i1++) {
                    parameterDescriptionsString = parameterDescriptionsString + paramSubColor + "    ❱" + paramColor + "❱ " + descriptionColor + parameters.get(i).getAsJsonObject().get("description").getAsJsonArray().get(i1).getAsString() + "\n";
                }
            } else {
                parameterDescriptionsString = paramSubColor + "    ❱" + paramColor + "❱ " + descriptionColor + parameters.get(i).getAsJsonObject().get("description").getAsString() + "\n";
            }

            parameterDescriptionsDone.add(parameterDescriptionsString);
        }

        minecraft.player.sendMessage(new TextComponentString(mainColor + "❱" + subColor + "❱ " + mainColor + "/" + command + " " + parameterString));
        minecraft.player.sendMessage(new TextComponentString(""));
        for (int i = 0; i < parametersDone.size(); i++) {
            minecraft.player.sendMessage(new TextComponentString(paramSubColor + "❱" + paramColor + "❱ " + parametersDone.get(i)));
            minecraft.player.sendMessage(new TextComponentString(parameterDescriptionsDone.get(i)));
        }
        minecraft.player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1f, 0f);
    }
}
