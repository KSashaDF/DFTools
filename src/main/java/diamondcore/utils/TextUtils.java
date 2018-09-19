package diamondcore.utils;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.awt.*;

public class TextUtils {
    
    public static String parseColorCodes(String text) {
        
        while (text.contains("&")) {
            int colorCodeIndex = text.indexOf('&');
            
            if (colorCodeIndex != text.length() - 1) {
                
                //Multi-char color codes.
                //Examples: &{}, &()
                if (colorCodeIndex != text.length() - 2) {
                    
                    //Color code: Color code group.
                    if (text.charAt(colorCodeIndex + 1) == '{') {
        
                        int endBracketIndex = text.indexOf('}', colorCodeIndex);
                        String multiColorString = text.substring(colorCodeIndex + 2, endBracketIndex);
                        
                        text = text.replace(text.substring(colorCodeIndex, endBracketIndex + 1), textToColorCodeText(multiColorString));
                        
                        //Color code: Custom color.
                    } else if (text.charAt(colorCodeIndex + 1) == '(') {
                        
                        //If an error is encountered while trying to parse colors, this will be set to true.
                        boolean invalidColorFormat = false;
                        
                        int endParenthesiseIndex = text.indexOf(')', colorCodeIndex);
                        String customColorString = text.substring(colorCodeIndex + 2, endParenthesiseIndex);
                        
                        //Removes all whitespace.
                        customColorString = customColorString.replaceAll("\\s", "");
                        String colorString = "";
                        
                        try {
                            //Checks if the given color is a hexadecimal color.
                            if (customColorString.startsWith("#")) {
                                Color hexColor = hexadecimalToRgb(customColorString);
                                colorString = hexColor.getRed() + "\uAB49" + hexColor.getGreen() + "\uAB49" + hexColor.getBlue();
                            } else {
                                String[] colorChannels = customColorString.split("[,]");
                                
                                //Checks if the given color format is either RGB, RGBA, or A (opacity)
                                if (colorChannels.length == 3) {
                                    colorString =
                                            (colorChannels[0] != null ? MathHelper.clamp(Integer.parseInt(colorChannels[0]), 0, 255) : "") + "|" +
                                            (colorChannels[1] != null ? MathHelper.clamp(Integer.parseInt(colorChannels[1]), 0, 255) : "") + "|" +
                                            (colorChannels[2] != null ? MathHelper.clamp(Integer.parseInt(colorChannels[2]), 0, 255) : "");
                                } else if (colorChannels.length == 4) {
                                    colorString =
                                            (colorChannels[0] != null ? MathHelper.clamp(Integer.parseInt(colorChannels[0]), 0, 255) : "") + "|" +
                                            (colorChannels[1] != null ? MathHelper.clamp(Integer.parseInt(colorChannels[1]), 0, 255) : "") + "|" +
                                            (colorChannels[2] != null ? MathHelper.clamp(Integer.parseInt(colorChannels[2]), 0, 255) : "") + "|" +
                                            (colorChannels[3] != null ? MathHelper.clamp(Integer.parseInt(colorChannels[3]), 0, 100) : "");
                                } else if (colorChannels.length == 1) {
                                    colorString = Integer.toString(MathHelper.clamp(Integer.parseInt(colorChannels[0]), 0, 100));
                                } else {
                                    invalidColorFormat = true;
                                }
                            }
                        } catch (Exception exception) {
                            invalidColorFormat = true;
                        }
                        
                        if (invalidColorFormat) {
                            text = text.replace(text.substring(colorCodeIndex, endParenthesiseIndex + 1), "");
                        } else {
                            text = text.replace(text.substring(colorCodeIndex, endParenthesiseIndex + 1), "\uAB60\uAB48" + textToInvisColorText(colorString) + "\uAB48");
                        }
    
                        //Single char color codes.
                        //Examples: &a, &v, &*
                    } else {
                        text = text.replaceFirst("[&]", "\u00A7");
                    }
                    
                    //Also used for handling single color code characters.
                    //(this is only needed if the character is at the very end of the string)
                } else {
                    text = text.replace('&', '\u00A7');
                }
                
                //If the end of the string has been reached, just replace the rest of the & chars.
            } else {
                text = text.replace('&', '\u00A7');
            }
        }
        
        return text;
    }
    
    public static String clearColorCodes(String text) {
        
        text = text.replaceAll("\\u00A7.", "");
        text = text.replaceAll("\\u00A7", "");
        
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
    
    public static String buildString(String[] strings, int startPos) {
        return buildString(strings, startPos, strings.length - 1);
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
    
    private static Color hexadecimalToRgb(String hexadecimalColor) {
        hexadecimalColor = hexadecimalColor.toLowerCase();
        hexadecimalColor = hexadecimalColor.replaceAll("[^0123456789abcdef]", "");
        
        int redChannel = Integer.parseInt(hexadecimalColor.substring(0, 2), 16);
        int greenChannel = Integer.parseInt(hexadecimalColor.substring(2, 4), 16);
        int blueChannel = Integer.parseInt(hexadecimalColor.substring(4, 6), 16);
        
        return new Color(redChannel, greenChannel, blueChannel);
    }
    
    private static String textToColorCodeText(String text) {
        StringBuilder stringBuilder = new StringBuilder(text.length() * 2);
        
        for (int index = 0; index < text.length(); index++) {
            char colorChar = text.charAt(index);
            
            if (colorChar == '*' || colorChar == 'v') {
                if (colorChar == '*') {
                    stringBuilder.append("\uAB60\uAB46");
                }
                if (colorChar == 'v') {
                    stringBuilder.append("\uAB60\uAB47");
                }
            } else {
                stringBuilder.append('\u00A7');
                stringBuilder.append(colorChar);
            }
        }
        
        return stringBuilder.toString();
    }
    
    private static String textToInvisColorText(String text) {
        StringBuilder stringBuilder = new StringBuilder(text.length() * 2);
    
        for (int index = 0; index < text.length(); index++) {
            char colorChar = text.charAt(index);
            
            if (colorChar >= '0' && colorChar <= 'f') {
                stringBuilder.append((char) (colorChar + 43776));
            } else if (colorChar == 'k') {
                stringBuilder.append('\uAB40');
            } else if (colorChar == 'l') {
                stringBuilder.append('\uAB41');
            } else if (colorChar == 'm') {
                stringBuilder.append('\uAB42');
            } else if (colorChar == 'n') {
                stringBuilder.append('\uAB43');
            } else if (colorChar == 'o') {
                stringBuilder.append('\uAB44');
            } else if (colorChar == 'r') {
                stringBuilder.append('\uAB45');
            } else if (colorChar == '|') {
                stringBuilder.append('\uAB49');
            }
        }
    
        return stringBuilder.toString();
    }
}
