package dfutils.utils;

import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;

public class TextUtils {
    
    public static String parseColorCodes(String text) {
        
        text = text.replace("&", "§");
        
        return text;
    }
    
    public static String clearColorCodes(String text) {
        
        while (text.contains("§")) {
            int charPos = text.indexOf('§');
            
            if (charPos == text.length() - 1) {
                text = text.replaceFirst("§", "");
            } else {
                text = text.replaceFirst(text.substring(charPos, charPos + 1), "");
            }
        }
        
        return text;
    }
    
    @Nullable
    public static EntityEquipmentSlot parseSlotText(String slotName) {
        switch(slotName) {
            case "main_hand":
                return EntityEquipmentSlot.MAINHAND;
            
            case "off_hand":
                return EntityEquipmentSlot.OFFHAND;
            
            case "helmet":
                return EntityEquipmentSlot.HEAD;
            
            case "chest":
                return EntityEquipmentSlot.CHEST;
            
            case "leggings":
                return EntityEquipmentSlot.LEGS;
            
            case "boots":
                return EntityEquipmentSlot.FEET;
            
            default:
                return null;
        }
    }
    
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
