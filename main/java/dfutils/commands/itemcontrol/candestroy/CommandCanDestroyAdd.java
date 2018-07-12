package dfutils.commands.itemcontrol.candestroy;

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

class CommandCanDestroyAdd {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeAddCanDestroy(ICommandSender sender, String[] commandArgs) {
        
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
        
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        
        //Checks if item stack is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }
        
        //Checks if item has an NBT tag.
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        
        //Checks if item has a CanDestroy tag.
        if (!itemStack.getTagCompound().hasKey("CanDestroy", 9)) {
            itemStack.getTagCompound().setTag("CanDestroy", new NBTTagList());
        }
        
        itemStack.getTagCompound().getTagList("CanDestroy", 8).appendTag(new NBTTagString(commandArgs[1]));
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
        
        actionMessage("Added CanDestroy tag.");
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        
        if (commandArgs.length != 2) {
            infoMessage("Usage:\n" + new CommandCanDestroyBase().getUsage(sender));
            return false;
        }
        
        try {
            CommandBase.getBlockByText(sender, commandArgs[1]);
        } catch (NumberInvalidException exception) {
            errorMessage("Invalid block name.");
            return false;
        }
        
        return true;
    }
}
