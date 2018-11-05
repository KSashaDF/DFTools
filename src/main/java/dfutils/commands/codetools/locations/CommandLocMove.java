package dfutils.commands.codetools.locations;

import diamondcore.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

class CommandLocMove {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	static void executeMoveLoc(ICommandSender sender, String[] commandArgs) {
		
		if (!checkFormat(sender, commandArgs)) {
			return;
		}
		
		ItemStack itemStack = minecraft.player.getHeldItemMainhand();
		
		if (itemStack.isEmpty()) {
			errorMessage("Invalid item!");
			return;
		}
		
		if (!itemStack.getDisplayName().equals("§aLocation")) {
			errorMessage("Invalid item!");
			return;
		}
		
		if (!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("HideFlags") || !(itemStack.getTagCompound().getInteger("HideFlags") == 63)) {
			errorMessage("Invalid item!");
			return;
		}
		
		try {
			//The following code increments and sets the location's X, Y, and Z values.
			NBTTagList itemLore = itemStack.getSubCompound("display").getTagList("Lore", 8);
			double locationX = CommandBase.parseDouble(itemLore.getStringTagAt(0));
			double locationY = CommandBase.parseDouble(itemLore.getStringTagAt(1));
			double locationZ = CommandBase.parseDouble(itemLore.getStringTagAt(2));
			
			itemLore.set(0, new NBTTagString((locationX + CommandBase.parseDouble(commandArgs[1])) + ""));
			itemLore.set(1, new NBTTagString((locationY + CommandBase.parseDouble(commandArgs[2])) + ""));
			itemLore.set(2, new NBTTagString((locationZ + CommandBase.parseDouble(commandArgs[3])) + ""));
			
			//If the command contains a pitch value, increment and set the location pitch.
			if (commandArgs.length >= 5) {
				double locationPitch = CommandBase.parseDouble(itemLore.getStringTagAt(3));
				locationPitch += CommandBase.parseDouble(commandArgs[4]);
				
				locationPitch = MathUtils.clamp(locationPitch, -90, 90);
				
				itemLore.set(3, new NBTTagString(locationPitch + ""));
			}
			
			//If the command contains a yaw value, increment and set the location yaw.
			if (commandArgs.length == 6) {
				double locationYaw = CommandBase.parseDouble(itemLore.getStringTagAt(4));
				locationYaw += CommandBase.parseDouble(commandArgs[5]);
				
				//The following code makes sure the locationYaw value is a valid yaw value.
				//(by valid yaw value I mean its between 180 and -180)
				
				locationYaw = (locationYaw + 180) % 360;
				locationYaw = ((locationYaw - 360) % 360) + 180;
				
				itemLore.set(4, new NBTTagString(locationYaw + ""));
			}
			
			//Sends the updated location item to the server.
			minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
		} catch (NumberInvalidException exception) {
			errorMessage("Invalid number format!");
		}
	}
	
	private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
		if (commandArgs.length < 4 || commandArgs.length > 6) {
			infoMessage("Usage: \n" + new CommandLocBase().getUsage(sender));
			return false;
		}
		
		return true;
	}
}
