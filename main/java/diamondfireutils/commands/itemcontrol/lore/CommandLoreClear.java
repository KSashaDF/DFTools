package diamondfireutils.commands.itemcontrol.lore;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static diamondfireutils.commands.MessageUtils.*;

class CommandLoreClear {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeClearLore(ICommandSender sender, String[] commandArgs) {
        
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
    
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        NBTTagCompound nbtTag = itemStack.getSubCompound("display");
    
        //Checks if item has display NBT tag.
        if (nbtTag != null) {
        
            //Removes lore tag from item.
            nbtTag.removeTag("Lore");
        
            //Sends updated item to the server.
            minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
            commandAction("Cleared lore from item.");
        
        } else {
            commandError("Invalid item!");
        }
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length == 1) {
            return true;
            
        } else {
            commandError("Usage:\n" + new CommandLoreBase().getUsage(sender));
            return false;
        }
    }
}
