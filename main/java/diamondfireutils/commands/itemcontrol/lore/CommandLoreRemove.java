package diamondfireutils.commands.itemcontrol.lore;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import static diamondfireutils.commands.MessageUtils.*;

class CommandLoreRemove {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeRemoveLore(ICommandSender sender, String[] commandArgs) {
        
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
        
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        
        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            commandError("Invalid item!");
            return;
        }
        
        //Checks if item has NBT tag.
        if (itemStack.getTagCompound() == null) {
            commandError("Invalid item! Item does not contain any lore.");
            return;
        }
        
        //Checks if item has display tag.
        if (!itemStack.getTagCompound().hasKey("display", 10)) {
            commandError("Invalid item! Item does not contain any lore.");
            return;
        }
        
        NBTTagCompound nbtTag = itemStack.getSubCompound("display");
        
        //Checks if item has Lore tag.
        if (!nbtTag.hasKey("Lore", 9)) {
            commandError("Invalid item! Item does not contain any lore.");
            return;
        }
        
        NBTTagList loreList = nbtTag.getTagList("Lore", 8);
        int lineNumber;
        
        //Parses line number from command.
        try {
            lineNumber = CommandBase.parseInt(commandArgs[1]);
            
        } catch (NumberInvalidException exception) {
            commandError("Invalid line number.");
            return;
        }
        
        //Checks if specified lore line is valid.
        if (lineNumber > loreList.tagCount()) {
            commandError("Invalid line number! This item only contains " + loreList.tagCount() + " lines of lore.");
            return;
        }
    
        //Shifts all lore lines above removed lore line down.
        for (int i = lineNumber - 1; i < loreList.tagCount() - 1; i++) {
            loreList.set(i, loreList.get(i + 1));
        }
        
        //Removes last line of lore.
        loreList.removeTag(loreList.tagCount() - 1);
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        commandAction("Removed lore line.");
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length == 2) {
            try {
                
                if (CommandBase.parseInt(commandArgs[1]) > 0) {
                    return true;
                    
                } else {
                    commandError("Line number must be greater than 0.");
                    return false;
                }
                
            } catch (NumberInvalidException exception) {
                commandError("Invalid line number.");
                return false;
            }
            
        } else {
            commandError("Usage:\n" + new CommandLoreBase().getUsage(sender));
            return false;
        }
    }
}
