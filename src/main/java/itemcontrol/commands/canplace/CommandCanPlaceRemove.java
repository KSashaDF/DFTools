package itemcontrol.commands.canplace;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

class CommandCanPlaceRemove {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	static void executeRemoveCanPlace(ICommandSender sender, String[] commandArgs) {
		
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
			errorMessage("This item does not contain any CanPlaceOn tags!");
			return;
		}
		
		//Checks if item has a CanPlaceOn tag.
		if (!itemStack.getTagCompound().hasKey("CanPlaceOn", 9)) {
			errorMessage("This item does not contain any CanPlaceOn tags!");
			return;
		}
		
		if (itemStack.getTagCompound().getTagList("CanPlaceOn", 8).tagCount() == 0) {
			itemStack.getTagCompound().removeTag("CanPlaceOn");
			errorMessage("This item does not contain any CanPlaceOn tags!");
			return;
		}
		
		NBTTagList nbtTagList = itemStack.getTagCompound().getTagList("CanPlaceOn", 8);
		
		//Iterates through CanPlaceOn tags.
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			
			//Checks if CanPlaceOn tag is the specified block, if so, removes it.
			if (nbtTagList.get(i).toString().equalsIgnoreCase("\"" + commandArgs[1] + "\"")) {
				nbtTagList.removeTag(i);
				
				//Sends updated item to the server.
				minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
				
				actionMessage("Removed CanPlaceOn tag.");
				return;
			}
		}
		
		errorMessage("Could not find specified CanPlaceOn tag.");
	}
	
	private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
		
		if (commandArgs.length != 2) {
			infoMessage("Usage:\n" + new CommandCanPlaceBase().getUsage(sender));
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
