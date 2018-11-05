package itemcontrol.commands.candestroy;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

class CommandCanDestroyClear {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	static void executeClearCanDestroy(ICommandSender sender, String[] commandArgs) {
		
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
			errorMessage("This item does not contain any CanDestroy tags!");
			return;
		}
		
		//Checks if item has a CanDestroy tag.
		if (!itemStack.getTagCompound().hasKey("CanDestroy", 9)) {
			errorMessage("This item does not contain any CanDestroy tags!");
			return;
		}
		
		itemStack.getTagCompound().removeTag("CanDestroy");
		
		//Sends updated item to the server.
		minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
		
		actionMessage("Cleared all CanDestroy tags.");
	}
	
	private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
		if (commandArgs.length == 1) {
			return true;
			
		} else {
			infoMessage("Usage:\n" + new CommandCanDestroyBase().getUsage(sender));
			return false;
		}
	}
}
