package dfutils.utils;

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
                                colorString = hexColor.getRed() + "|" + hexColor.getGreen() + "|" + hexColor.getBlue();
                            } else {
                                int[] colorChannels = parseNumberList(customColorString);
                                
                                //Checks if the given color format is either RGB, RGBA, or A (opacity)
                                if (colorChannels.length == 3) {
                                    colorString =
                                            MathHelper.clamp(colorChannels[0], 0, 255) + "|" +
                                            MathHelper.clamp(colorChannels[1], 0, 255) + "|" +
                                            MathHelper.clamp(colorChannels[2], 0, 255);
                                } else if (colorChannels.length == 4) {
                                    colorString =
                                            MathHelper.clamp(colorChannels[0], 0, 255) + "|" +
                                            MathHelper.clamp(colorChannels[1], 0, 255) + "|" +
                                            MathHelper.clamp(colorChannels[2], 0, 255) + "|" +
                                            MathHelper.clamp(colorChannels[3], 0, 100);
                                } else if (colorChannels.length == 1) {
                                    colorString = Integer.toString(MathHelper.clamp(colorChannels[0], 0, 100));
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
                            text = text.replace(text.substring(colorCodeIndex, endParenthesiseIndex + 1), "\u00A7%" + textToColorCodeText(colorString) + "\u00A7%");
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
    
    public static Color hexadecimalToRgb(String hexadecimalColor) {
        hexadecimalColor = hexadecimalColor.toLowerCase();
        hexadecimalColor = hexadecimalColor.replaceAll("[^0123456789abcdef]", "");
        
        int redChannel = Integer.parseInt(hexadecimalColor.substring(0, 2), 16);
        int greenChannel = Integer.parseInt(hexadecimalColor.substring(2, 4), 16);
        int blueChannel = Integer.parseInt(hexadecimalColor.substring(4, 6), 16);
        
        return new Color(redChannel, greenChannel, blueChannel);
    }
    
    private static int[] parseNumberList(String text) {
        String[] numberListText = text.split("[,]");
        int[] numberList = new int[numberListText.length];
        
        for (int index = 0; index < numberList.length; index++) {
            numberList[index] = Integer.parseInt(numberListText[index]);
        }
        
        return numberList;
    }
    
    private static String textToColorCodeText(String text) {
        StringBuilder stringBuilder = new StringBuilder(text.length() * 2);
        
        for (int index = 0; index < text.length(); index++) {
            stringBuilder.append('\u00A7');
            stringBuilder.append(text.charAt(index));
        }
        
        return stringBuilder.toString();
    }
}
