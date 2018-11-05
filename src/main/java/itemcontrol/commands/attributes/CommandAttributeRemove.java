package itemcontrol.commands.attributes;

import diamondcore.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

class CommandAttributeRemove {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	static void executeRemoveAttribute(ICommandSender sender, String[] commandArgs) {
		
		//Checks if command format is valid.
		if (!checkFormat(sender, commandArgs)) return;
		
		ItemStack itemStack = minecraft.player.getHeldItemMainhand();
		
		//Checks if item stack is not air.
		if (itemStack.isEmpty()) {
			errorMessage("Invalid item!");
			return;
		}
		
		//Checks if item has attributes.
		if (!itemStack.hasTagCompound()) {
			errorMessage("This item does not contain any attributes!");
			return;
		}
		
		//Checks if item has attributes.
		if (!itemStack.getTagCompound().hasKey("AttributeModifiers", 9)) {
			errorMessage("This item does not contain any attributes!");
			return;
		}
		
		//If the attribute name does not already have a "generic." prefix, add it on.
		if (!commandArgs[1].contains("."))
			commandArgs[1] = "generic." + commandArgs[1];
		
		NBTTagList nbtTagList = itemStack.getTagCompound().getTagList("AttributeModifiers", 10);
		
		//Iterates through item attributes.
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			
			NBTTagCompound checkAttribute = nbtTagList.getCompoundTagAt(i);
			
			//Checks if attribute is the correct attribute, and if so, removes it.
			if (checkAttribute.getTag("Name").toString().equals("\"" + commandArgs[1] + "\"")) {
				if (commandArgs.length == 3) {
					try {
						if (checkAttribute.getTag("Slot").toString().equals("\"" + TextUtils.parseSlotText(commandArgs[2]).getName() + "\"")) {
							
							nbtTagList.removeTag(i);
							
							//Sends updated item to the server.
							minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
							
							actionMessage("Removed attribute from item.");
							return;
						}
					} catch (NullPointerException exception) {
						//If attribute has no name, just move on.
					}
				} else {
					
					nbtTagList.removeTag(i);
					
					//Sends updated item to the server.
					minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
					
					actionMessage("Removed attribute from item.");
					return;
				}
			}
		}
		
		errorMessage("Could not find specified attribute.");
	}
	
	private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
		if (commandArgs.length >= 2 && commandArgs.length <= 3) {
			
			if (commandArgs.length == 3) {
				if (TextUtils.parseSlotText(commandArgs[2]) == null) {
					errorMessage("Invalid slot name! Valid slot names: main_hand, off_hand, helmet, chest, leggings, or boots.");
					return false;
				}
			}
			
			return true;
			
		} else {
			infoMessage("Usage:\n" + new CommandAttributeBase().getUsage(sender));
			return false;
		}
	}
}
