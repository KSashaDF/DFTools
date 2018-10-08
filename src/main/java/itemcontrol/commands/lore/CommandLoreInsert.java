package itemcontrol.commands.lore;

import diamondcore.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

class CommandLoreInsert {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeInsertLore(ICommandSender sender, String[] commandArgs) {
        
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
        
        commandArgs[2] = CommandBase.buildString(commandArgs, 2);
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        
        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }
        
        //Checks if item has NBT tag, if not, adds NBT tag.
        if (itemStack.getTagCompound() == null) {
            errorMessage("Invalid item! Item does not contain any lore.");
            return;
        }
        
        //Checks if item has display tag, if not, adds display tag.
        if (!itemStack.getTagCompound().hasKey("display", 10)) {
            errorMessage("Invalid item! Item does not contain any lore.");
            return;
        }
        
        NBTTagCompound nbtTag = itemStack.getSubCompound("display");
        
        //Checks if item has Lore tag, if not, adds Lore tag.
        if (!nbtTag.hasKey("Lore", 9)) {
            errorMessage("Invalid item! Item does not contain any lore.");
            return;
        }
        
        NBTTagList loreList = nbtTag.getTagList("Lore", 8);
        int lineNumber;
        
        //Parses line number from command.
        try {
            lineNumber = CommandBase.parseInt(commandArgs[1]);
            
        } catch (NumberInvalidException exception) {
            errorMessage("Invalid line number.");
            return;
        }
    
        //Checks if specified lore line is valid.
        if (lineNumber > loreList.tagCount()) {
            errorMessage("Invalid line number! This item only contains " + loreList.tagCount() + " lines of lore.");
            return;
        }
    
        //Creates extra line.
        loreList.appendTag(new NBTTagString());
        
        //Shifts all lines above inserted line up.
        for (int i = loreList.tagCount() - 1; i > lineNumber - 1; i--) {
            loreList.set(i, loreList.get(i - 1));
        }
        
        commandArgs[2] = TextUtils.parseColorCodes(commandArgs[2]);
        
        //Sets lore at specified line.
        loreList.set(lineNumber - 1, new NBTTagString(commandArgs[2]));
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        actionMessage("Inserted item lore.");
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length >= 3) {
            try {
                
                if (CommandBase.parseInt(commandArgs[1]) > 0) {
                    return true;
                    
                } else {
                    errorMessage("Line number must be greater than 0.");
                    return false;
                }
                
            } catch (NumberInvalidException exception) {
                errorMessage("Invalid line number.");
                return false;
            }
            
        } else {
            infoMessage("Usage:\n" + new CommandLoreBase().getUsage(sender));
            return false;
        }
    }
}
