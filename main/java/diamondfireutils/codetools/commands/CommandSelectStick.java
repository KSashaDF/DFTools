package diamondfireutils.codetools.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static diamondfireutils.commands.MessageUtils.commandError;

class CommandSelectStick {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeSelectStick(ICommandSender sender, String[] commandArgs) {
        if (!checkFormat(sender, commandArgs)) {
            return;
        }
    
        ItemStack itemStack = new ItemStack(Item.getItemById(280));
        itemStack.setStackDisplayName("§eCode Selection Stick");
        
        //Sends item to server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length != 1) {
            commandError("Usage: \n" + new CommandCodeBase().getUsage(sender));
            return false;
        }
        
        return true;
    }
}
