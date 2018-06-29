package dfutils.commands.itemcontrol.canplace;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;

import static dfutils.commands.MessageUtils.commandAction;
import static dfutils.commands.MessageUtils.commandError;
import static dfutils.commands.MessageUtils.commandInfo;

public class CommandCanPlaceClear {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeClearCanPlace(ICommandSender sender, String[] commandArgs) {
        
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
        
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        
        //Checks if item stack is not air.
        if (itemStack.isEmpty()) {
            commandError("Invalid item!");
            return;
        }
        
        //Checks if item has an NBT tag.
        if (!itemStack.hasTagCompound()) {
            commandError("This item does not contain any CanPlaceOn tags!");
            return;
        }
        
        //Checks if item has a CanPlaceOn tag.
        if (!itemStack.getTagCompound().hasKey("CanPlaceOn", 9)) {
            commandError("This item does not contain any CanPlaceOn tags!");
            return;
        }
        
        itemStack.getTagCompound().removeTag("CanPlaceOn");
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        commandAction("Cleared all CanPlaceOn tags.");
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length == 1) {
            return true;
            
        } else {
            commandInfo("Usage:\n" + new CommandCanPlaceBase().getUsage(sender));
            return false;
        }
    }
}
