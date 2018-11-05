package dfutils.commands.codetools;

import diamondcore.utils.MessageUtils;
import diamondcore.utils.playerdata.PlayerMode;
import diamondcore.utils.playerdata.PlayerStateHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandRejoin extends CommandBase implements IClientCommand {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public String getName() {
		return "rejoin";
	}
	
	public String getUsage(ICommandSender sender) {
		return "§b/rejoin";
	}
	
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}
	
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return false;
	}
	
	public void execute(MinecraftServer server, ICommandSender sender, String[] commandArgs) {
		
		if (PlayerStateHandler.isOnDiamondFire && PlayerStateHandler.playerMode != PlayerMode.SPAWN) {
			if (PlayerStateHandler.isInSupportSession) {
				MessageUtils.errorMessage("This command is disabled while inside a session!");
			} else {
				MessageUtils.actionMessage("Rejoining...");
				
				switch (PlayerStateHandler.playerMode) {
					case PLAY:
						minecraft.player.sendChatMessage("/spawn");
						break;
					
					case BUILD:
						minecraft.player.sendChatMessage("/play");
						break;
					
					case DEV:
						minecraft.player.sendChatMessage("/play");
						break;
				}
				
				new Thread(new CommandWait(PlayerStateHandler.playerMode)).start();
			}
		}
	}
	
	static class CommandWait implements Runnable {
		
		PlayerMode previousPlayerMode;
		
		CommandWait(PlayerMode previousPlayerMode) {
			this.previousPlayerMode = previousPlayerMode;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
				
				switch (previousPlayerMode) {
					case PLAY:
						minecraft.player.sendChatMessage("/join " + PlayerStateHandler.plotId);
						break;
					
					case BUILD:
						minecraft.player.sendChatMessage("/build");
						break;
					
					case DEV:
						minecraft.player.sendChatMessage("/dev");
						break;
				}
			} catch (InterruptedException exception) {
				//Uh oh! Thread wait interrupted, continue on.
			}
		}
	}
}
