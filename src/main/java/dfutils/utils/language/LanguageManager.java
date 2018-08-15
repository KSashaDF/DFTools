package dfutils.utils.language;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.InputStream;

/**
 * This class manages the loading of language files and
 * also manages the getting of translation components.
 */
public class LanguageManager {
    
    private static String loadedLanguageName = "";
    private static String suppressLanguage = "";
    private static JsonObject languageData;
    private static JsonObject defaultLanguageData;
    private static boolean disableDefaultLanguage = false;
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final LanguageManager instance = new LanguageManager();
    private static final String VARIABLE_CODE = "%VAR%";
    private static final String REGEX_VARIABLE_CODE = "(%VAR%)";
    private static final TextComponentString MISSING_FILE = new TextComponentString("MISSING LANGUAGE FILE");
    private static final TextComponentString MISSING_TRANSLATION = new TextComponentString("MISSING TRANSLATION");
    private static final TextComponentString INVALID_TRANSLATION = new TextComponentString("INVALID TRANSLATION");
    
    private LanguageManager() {}
    
    public static ITextComponent getMessage(String translationKey) {
        return getMessage(translationKey, false);
    }
    
    private static ITextComponent getMessage(String translationKey, boolean useDefaultLang) {
        
        //If the default language file has not been loaded yet load it.
        //Also check if the default language file has not been disabled due to an error.
        if (defaultLanguageData == null && !disableDefaultLanguage) {
            instance.loadDefaultLanguageFile();
        }
        
        //If the currently selected language does not equal the loaded language, reload the language file.
        //Also checks if the currently selected language is not suppressed.
        if (!loadedLanguageName.equals(minecraft.getLanguageManager().getCurrentLanguage().getLanguageCode()) && !suppressLanguage.equals(minecraft.getLanguageManager().getCurrentLanguage().getLanguageCode())) {
            instance.loadLanguageFile(minecraft.getLanguageManager().getCurrentLanguage().getLanguageCode());
        }
    
        if (useDefaultLang && defaultLanguageData == null) {
            return MISSING_FILE;
        }
        
        //If the specified lang file is missing, use the default lang file.
        if (languageData == null) {
            useDefaultLang = true;
        }
        
        String[] translationPath = translationKey.split("[.]");
        int pathPosition = -1;
        JsonObject searchData;
        
        if (useDefaultLang) {
            searchData = defaultLanguageData;
        } else {
            searchData = languageData;
        }
        
        while (true) {
            pathPosition++;
            if (pathPosition >= translationPath.length) {
                if (useDefaultLang) {
                    return MISSING_TRANSLATION;
                } else {
                    return getMessage(translationKey, true);
                }
            }
            
            if (searchData.has(translationPath[pathPosition])) {
                JsonElement nextNbtTag = searchData.get(translationPath[pathPosition]);
                
                if (TranslationComponentHelper.isTranslationComponent(nextNbtTag)) {
                    return TranslationComponentHelper.parseTextComponent(nextNbtTag);
                } else if (nextNbtTag.isJsonObject()) {
                    searchData = (JsonObject) nextNbtTag;
                } else {
                    return INVALID_TRANSLATION;
                }
                
            } else {
                if (useDefaultLang) {
                    return MISSING_TRANSLATION;
                } else {
                    return getMessage(translationKey, true);
                }
            }
        }
    }

    public static JsonArray getArray(String translationKey) {
        //If the currently selected language does not equal the loaded language, reload the language file.
        //Also checks if the currently selected language is not suppressed.
        if (!loadedLanguageName.equals(minecraft.getLanguageManager().getCurrentLanguage().getLanguageCode()) && !suppressLanguage.equals(minecraft.getLanguageManager().getCurrentLanguage().getLanguageCode())) {
            instance.loadLanguageFile(minecraft.getLanguageManager().getCurrentLanguage().getLanguageCode());
        }

        String[] translationPath = translationKey.split("[.]");
        int pathPosition = -1;
        JsonObject searchData = languageData;

        while (true) {
            pathPosition++;
            if (pathPosition >= translationPath.length) {
                return null;
            }

            if (searchData.has(translationPath[pathPosition])) {
                JsonElement nextNbtTag = searchData.get(translationPath[pathPosition]);

                if (nextNbtTag.isJsonArray()) {
                    return nextNbtTag.getAsJsonArray();
                } else if (nextNbtTag.isJsonObject()) {
                    searchData = nextNbtTag.getAsJsonObject();
                } else {
                    return null;
                }

            } else {
                return null;
            }
        }
    }
    
    /**
     * This method gets the specifies translation component,
     * however this method also parses %VAR% variable codes.
     */
    public static ITextComponent getMessage(String translationKey, @Nonnull String... variables) {
        String translationComponent = ITextComponent.Serializer.componentToJson(getMessage(translationKey));
        
        if (translationComponent.contains(VARIABLE_CODE)) {
            for (int i = 0; i < variables.length && translationComponent.contains(VARIABLE_CODE); i++) {
                translationComponent = translationComponent.replaceFirst(REGEX_VARIABLE_CODE, variables[i]);
            }
        }
    
        return ITextComponent.Serializer.jsonToComponent(translationComponent);
    }
    
    public static String getString(String translationKey) {
        return getMessage(translationKey).getUnformattedText();
    }
    
    public static String getString(String translationKey, @Nonnull String... variables) {
        return getMessage(translationKey, variables).getUnformattedText();
    }
    
    
    private void loadDefaultLanguageFile() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/assets/dfutils/lang/en_us.json")) {
            defaultLanguageData = new JsonParser().parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
        } catch (Exception exception) {
            //If an Exception has occurred, it most likely means that the en_us language file has an incorrect JSON format.
            disableDefaultLanguage = true;
        }
    }
    
    private void loadLanguageFile(String languageName) {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/assets/dfutils/lang/" + languageName + ".json")) {
            languageData = new JsonParser().parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
            loadedLanguageName = languageName;
        } catch (Exception exception) {
            //If an Exception has occurred, it most likely means that the specified language file does not exist.
            languageData = null;
            suppressLanguage = languageName;
    
            if (!languageName.equals("en_us")) {
                loadLanguageFile("en_us");
            }
        }
    }
}
