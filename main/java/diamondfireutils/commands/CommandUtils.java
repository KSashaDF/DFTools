package diamondfireutils.commands;

import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;

public class CommandUtils {
    
    public static String parseColorCodes(String text) {
        
        text = text.replace("&r", "§r");
        text = text.replace("&0", "§0");
        text = text.replace("&8", "§8");
        text = text.replace("&7", "§7");
        text = text.replace("&f", "§f");
    
        text = text.replace("&1", "§1");
        text = text.replace("&9", "§9");
        text = text.replace("&3", "§3");
        text = text.replace("&b", "§b");
    
        text = text.replace("&2", "§2");
        text = text.replace("&a", "§a");
    
        text = text.replace("&4", "§4");
        text = text.replace("&c", "§c");
        text = text.replace("&6", "§6");
        text = text.replace("&e", "§e");
    
        text = text.replace("&5", "§5");
        text = text.replace("&d", "§d");
    
        text = text.replace("&l", "§l");
        text = text.replace("&o", "§o");
        text = text.replace("&m", "§m");
        text = text.replace("&n", "§n");
        text = text.replace("&k", "§k");
        
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
