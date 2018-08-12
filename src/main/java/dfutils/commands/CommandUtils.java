package dfutils.commands;

import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;

public class CommandUtils {
    
    public static String parseColorCodes(String text) {
        
        //Vanilla color codes.
        text = text.replace("&r", "§r"); //Color and style reset code.
        text = text.replace("&0", "§0"); //Black color.
        text = text.replace("&8", "§8"); //Gray color.
        text = text.replace("&7", "§7"); //Light gray color.
        text = text.replace("&f", "§f"); //White color.
    
        text = text.replace("&1", "§1"); //Dark blue color.
        text = text.replace("&9", "§9"); //Blue color.
        text = text.replace("&3", "§3"); //Cyan color.
        text = text.replace("&b", "§b"); //Light blue color.
    
        text = text.replace("&2", "§2"); //Green color.
        text = text.replace("&a", "§a"); //Lime color.
    
        text = text.replace("&4", "§4"); //Dark red color.
        text = text.replace("&c", "§c"); //Red color.
        text = text.replace("&6", "§6"); //Orange color.
        text = text.replace("&e", "§e"); //Yellow color.
    
        text = text.replace("&5", "§5"); //Purple color.
        text = text.replace("&d", "§d"); //Magenta color.
    
        text = text.replace("&l", "§l"); //Bold style.
        text = text.replace("&o", "§o"); //Italic style.
        text = text.replace("&m", "§m"); //Strike through style.
        text = text.replace("&n", "§n"); //Underline style.
        text = text.replace("&k", "§k"); //Obfuscated style.
    
        //DFUtils color codes.
        text = text.replace("&*", "§*"); //Color blending toggle.
        text = text.replace("&v", "§v"); //Vanilla color code mode.
        
        //Color code text code.
        text = text.replace("[color_code]", "§");
        
        return text;
    }
    
    public static String clearColorCodes(String text) {
        
        text = text.replace("§r", "");
        text = text.replace("§0", "");
        text = text.replace("§8", "");
        text = text.replace("§7", "");
        text = text.replace("§f", "");
        
        text = text.replace("§1", "");
        text = text.replace("§9", "");
        text = text.replace("§3", "");
        text = text.replace("§b", "");
        
        text = text.replace("§2", "");
        text = text.replace("§a", "");
        
        text = text.replace("§4", "");
        text = text.replace("§c", "");
        text = text.replace("§6", "");
        text = text.replace("§e", "");
        
        text = text.replace("§5", "");
        text = text.replace("§d", "");
        
        text = text.replace("§l", "");
        text = text.replace("§o", "");
        text = text.replace("§m", "");
        text = text.replace("§n", "");
        text = text.replace("§k", "");
        
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
}
