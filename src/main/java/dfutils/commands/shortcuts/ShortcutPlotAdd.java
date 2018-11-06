package dfutils.commands.shortcuts;

import diamondcore.utils.scheduledtasks.IScheduledTask;
import diamondcore.utils.scheduledtasks.ScheduledTaskManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;

import static diamondcore.utils.MessageUtils.actionMessage;

public class ShortcutPlotAdd implements IScheduledTask {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	private static String player;
	
	public static void shortcutPlotAddSendMessage(ClientChatEvent event) {
		
		if (minecraft.player.isCreative() && event.getMessage().startsWith("/plot add ")) {
			event.setCanceled(true);
			player = event.getMessage().replace("/plot add ", "");
			
			actionMessage("Adding player to plot permissions...");
			minecraft.player.sendChatMessage("/plot dev add " + player);
			ScheduledTaskManager.scheduleTask(40, new ShortcutPlotAdd());
		}
	}
	
	@Override
	public void runTask() {
		minecraft.player.sendChatMessage("/plot builder add " + player);
	}
}
