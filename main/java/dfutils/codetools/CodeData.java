package dfutils.codetools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CodeData {
    
    public static JsonObject codeReferenceData;
    
    public CodeData() {
        InputStreamReader jsonStreamReader = new InputStreamReader(this.getClass().getResourceAsStream("/assets/dfutils/codeData.json"));
        BufferedReader jsonReader = new BufferedReader(jsonStreamReader);
    
        codeReferenceData = new Gson().fromJson(jsonReader, JsonObject.class);
    }
}
