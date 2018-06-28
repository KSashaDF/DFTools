package diamondfireutils.codetools.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import diamondfireutils.codetools.CodeData;
import diamondfireutils.codetools.classification.CodeFunction;
import net.minecraft.util.JsonUtils;

public class CodeDataUtils {
    
    public static String[] getPath(CodeFunction codeFunction) {
        
        //Gets the Json path Array from the specified code function.
        JsonObject baseCodeBlockObject = JsonUtils.getJsonObject(CodeData.codeReferenceData, codeFunction.codeBlockName.name());
        JsonObject baseFunctionObject = JsonUtils.getJsonObject(baseCodeBlockObject, codeFunction.name());
        JsonArray jsonPathArray = JsonUtils.getJsonArray(baseFunctionObject, "path");
        
        String[] functionPath = new String[jsonPathArray.size()];
        
        //Converts the Json path Array into a String Array.
        for (int i = 0; i < jsonPathArray.size(); i++) {
            functionPath[i] = jsonPathArray.get(i).getAsString();
        }
        
        return functionPath;
    }
}
