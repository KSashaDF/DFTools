package itemcontrol.commands.candestroy;

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
public class CommandCanDestroyBase extends CommandBase implements IClientCommand {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public String getName() {
		return "candestroy";
	}
	
	public String getUsage(ICommandSender sender) {
		return "§b/candestroy add <block>\n" +
				"§b/candestroy remove <block>\n" +
				"§b/candestroy clear";
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
				CommandCanDestroyAdd.executeAddCanDestroy(sender, commandArgs);
				return;
			
			case "remove":
				CommandCanDestroyRemove.executeRemoveCanDestroy(sender, commandArgs);
				return;
			
			case "clear":
				CommandCanDestroyClear.executeClearCanDestroy(sender, commandArgs);
				return;
			
			default:
				infoMessage("Usage:\n" + getUsage(sender));
		}
	}
}
