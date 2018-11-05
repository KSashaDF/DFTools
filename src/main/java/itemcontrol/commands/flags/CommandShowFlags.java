package itemcontrol.commands.flags;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondcore.utils.MessageUtils.actionMessage;
import static diamondcore.utils.MessageUtils.errorMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandShowFlags extends CommandBase implements IClientCommand {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public String getName() {
		return "showflags";
	}
	
	public String getUsage(ICommandSender sender) {
		return "§b/showflags";
	}
	
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}
	
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return false;
	}
	
	public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
		
		//Checks if player should be able to execute command.
		if (!minecraft.player.isCreative()) {
			errorMessage("You need to be in build mode or dev mode to do this!");
			return;
		}
		
		ItemStack itemStack = minecraft.player.getHeldItemMainhand();
		
		//Checks if item is not air.
		if (itemStack.isEmpty()) {
			errorMessage("Invalid item!");
			return;
		}
		
		//Checks if item has NBT tag.
		if (itemStack.getTagCompound() == null) {
			errorMessage("Flags are already shown for this item!");
			return;
		}
		
		//Checks if item has HideFlags tag.
		if (!itemStack.getTagCompound().hasKey("HideFlags")) {
			errorMessage("Flags are already shown for this item!");
			return;
		}
		
		//Removes HideFlags tag.
		itemStack.getTagCompound().removeTag("HideFlags");
		
		//Sends updated item to the server.
		minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
		
		actionMessage("Flags are now shown for this item.");
	}
}
