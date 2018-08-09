package dfutils.utils.language;

import com.google.gson.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

class TranslationComponentHelper {
    
    static boolean isTranslationComponent(JsonElement translationElement) {
        return isSimpleText(translationElement) || isRichText(translationElement) || isTextList(translationElement);
    }
    
    private static boolean isSimpleText(JsonElement translationElement) {
        return translationElement.isJsonPrimitive() && ((JsonPrimitive) translationElement).isString();
    }
    
    private static boolean isRichText(JsonElement translationElement) {
        return translationElement.isJsonObject() &&
                ((JsonObject) translationElement).has("isRichText") &&
                ((JsonObject) translationElement).getAsJsonPrimitive("isRichText").isBoolean() &&
                ((JsonObject) translationElement).getAsJsonPrimitive("isRichText").getAsBoolean() &&
                ((JsonObject) translationElement).has("text") &&
                (((JsonObject) translationElement).get("text").isJsonObject() || ((JsonObject) translationElement).get("text").isJsonArray());
    }
    
    private static boolean isTextList(JsonElement translationElement) {
        if (translationElement.isJsonArray()) {
            for (int i = 0; i < translationElement.getAsJsonArray().size(); i++) {
                if (!(isSimpleText(translationElement.getAsJsonArray().get(i)) || isRichListText(translationElement.getAsJsonArray().get(i)))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    private static boolean isRichListText(JsonElement translationElement) {
        return translationElement.isJsonObject() || translationElement.isJsonArray();
    }
    
    static ITextComponent parseTextComponent(JsonElement translationElement) {
        if (isSimpleText(translationElement)) {
            return parseSimpleText(translationElement.getAsString());
        }
        
        if (isRichText(translationElement)) {
            return parseRichText(translationElement.getAsJsonObject());
        }
        
        if (isTextList(translationElement)) {
            return parseTextList(translationElement.getAsJsonArray());
        }
        
        return null;
    }
    
    private static ITextComponent parseSimpleText(String translationString) {
        return new TextComponentString(translationString);
    }
    
    private static ITextComponent parseRichText(JsonObject translationObject) {
        return ITextComponent.Serializer.jsonToComponent(translationObject.get("text").toString());
    }
    
    private static ITextComponent parseTextList(JsonArray translationArray) {
        JsonArray textComponentArray = new JsonArray();
        JsonObject newLineElement = new JsonObject();
        newLineElement.addProperty("text", "\n");
    
        for (int i = 0; i < translationArray.size(); i++) {
            String textComponentJson = ITextComponent.Serializer.componentToJson(parseListTextComponent(translationArray.get(i)));
            textComponentArray.add(new JsonParser().parse(textComponentJson));
        
            //If this is the end of the translationNbt list, don't add a newLine tag.
            if (i != translationArray.size() - 1) {
                textComponentArray.add(newLineElement);
            }
        }
    
        return ITextComponent.Serializer.jsonToComponent(textComponentArray.toString());
    }
    
    private static ITextComponent parseListTextComponent(JsonElement translationElement) {
        if (translationElement.isJsonPrimitive() && translationElement.getAsJsonPrimitive().isString()) {
            return parseSimpleText(translationElement.getAsString());
        } else if (translationElement.isJsonObject()) {
            return ITextComponent.Serializer.jsonToComponent(translationElement.getAsJsonObject().toString());
        } else if (translationElement.isJsonArray()) {
            return ITextComponent.Serializer.jsonToComponent(translationElement.getAsJsonArray().toString());
        } else {
            return null;
        }
    }
}
