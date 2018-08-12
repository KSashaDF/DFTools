package dfutils.commands.itemcontrol.lore;

import dfutils.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import static dfutils.utils.MessageUtils.actionMessage;
import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandLoreSet {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeSetLore(ICommandSender sender, String[] commandArgs) {
    
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
            itemStack.setTagCompound(new NBTTagCompound());
        }
    
        //Checks if item has display tag, if not, adds display tag.
        if (!itemStack.getTagCompound().hasKey("display", 10)) {
            itemStack.getTagCompound().setTag("display", new NBTTagCompound());
        }
    
        NBTTagCompound nbtTag = itemStack.getSubCompound("display");
    
        //Checks if item has Lore tag, if not, adds Lore tag.
        if (!nbtTag.hasKey("Lore", 9)) {
            nbtTag.setTag("Lore", new NBTTagList());
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
    
        //Checks if extra lore lines need to be made, if so, creates extra lore lines.
        if (loreList.tagCount() < lineNumber) {
            for (int i = loreList.tagCount(); i < lineNumber; i++) {
                loreList.appendTag(new NBTTagString());
            }
        }
    
        commandArgs[2] = TextUtils.parseColorCodes(commandArgs[2]);
        
        //Sets lore at specified line.
        loreList.set(lineNumber - 1, new NBTTagString(commandArgs[2]));
    
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    
        actionMessage("Set item lore.");
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
