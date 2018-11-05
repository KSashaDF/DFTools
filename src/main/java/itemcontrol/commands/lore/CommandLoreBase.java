package itemcontrol.commands.lore;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandLoreBase extends CommandBase implements IClientCommand {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public String getName() {
		return "lore";
	}
	
	public String getUsage(ICommandSender sender) {
		return "§b/lore add <lore> \n" +
				"§b/lore set <line> <lore> \n" +
				"§b/lore edit <line> \n" +
				"§b/lore insert <line> <lore> \n" +
				"§b/lore copy \n" +
				"§b/lore paste [history index]" +
				"§b/lore remove <line> \n" +
				"§b/lore clear";
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
		
		if (commandArgs.length == 0) {
			infoMessage("Usage:\n" + getUsage(sender));
			return;
		}
		
		switch (commandArgs[0]) {
			case "add":
				CommandLoreAdd.executeAddLore(sender, commandArgs);
				return;
			
			case "set":
				CommandLoreSet.executeSetLore(sender, commandArgs);
				return;
			
			case "edit":
				CommandLoreEdit.executeEditLore(sender, commandArgs);
				return;
			
			case "insert":
				CommandLoreInsert.executeInsertLore(sender, commandArgs);
				return;
			
			case "copy":
				CommandLoreCopy.executeCopyLore(sender, commandArgs);
				return;
			
			case "paste":
				CommandLorePaste.executePasteLore(sender, commandArgs);
				return;
			
			case "remove":
				CommandLoreRemove.executeRemoveLore(sender, commandArgs);
				return;
			
			case "clear":
				CommandLoreClear.executeClearLore(sender, commandArgs);
				return;
			
			default:
				infoMessage("Usage:\n" + getUsage(sender));
		}
	}
}
