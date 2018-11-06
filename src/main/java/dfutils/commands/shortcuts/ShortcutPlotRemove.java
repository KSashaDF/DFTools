package dfutils.commands.shortcuts;

import diamondcore.utils.scheduledtasks.IScheduledTask;
import diamondcore.utils.scheduledtasks.ScheduledTaskManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;

import static diamondcore.utils.MessageUtils.actionMessage;

public class ShortcutPlotRemove implements IScheduledTask {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	private static String player;
	
	public static void shortcutPlotRemoveSendMessage(ClientChatEvent event) {
		
		if (minecraft.player.isCreative() && event.getMessage().startsWith("/plot remove ")) {
			event.setCanceled(true);
			player = event.getMessage().replace("/plot add ", "");
			
			actionMessage("Removing player from plot permissions...");
			minecraft.player.sendChatMessage("/plot dev remove " + player);
			ScheduledTaskManager.scheduleTask(40, new ShortcutPlotRemove());
		}
	}
	
	@Override
	public void runTask() {
		minecraft.player.sendChatMessage("/plot builder remove " + player);
	}
}
