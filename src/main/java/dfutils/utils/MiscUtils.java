package dfutils.utils;

public class MiscUtils {
    
    public static String[] splitString(String string) {
        return string.split(" ");
    }
    
    public static String buildString(String[] strings) {
        return buildString(strings, 0, strings.length - 1);
    }
    
    public static String buildString(String[] strings, int startPos, int endPos) {
        if (startPos == endPos) {
            return strings[startPos];
        }
        
        StringBuilder stringBuilder = new StringBuilder(strings[startPos]);
        
        for (int i = startPos + 1; i <= endPos; i++) {
            stringBuilder.append(" ");
            stringBuilder.append(strings[i]);
        }
        
        return stringBuilder.toString();
    }
}
