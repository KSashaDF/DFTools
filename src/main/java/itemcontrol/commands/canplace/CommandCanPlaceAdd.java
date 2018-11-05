package itemcontrol.commands.canplace;

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

class CommandCanPlaceAdd {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	static void executeAddCanPlace(ICommandSender sender, String[] commandArgs) {
		
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
		
		//Checks if item has a CanPlaceOn tag.
		if (!itemStack.getTagCompound().hasKey("CanPlaceOn", 9)) {
			itemStack.getTagCompound().setTag("CanPlaceOn", new NBTTagList());
		}
		
		itemStack.getTagCompound().getTagList("CanPlaceOn", 8).appendTag(new NBTTagString(commandArgs[1]));
		
		//Sends updated item to the server.
		minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
		
		actionMessage("Added CanPlaceOn tag.");
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
