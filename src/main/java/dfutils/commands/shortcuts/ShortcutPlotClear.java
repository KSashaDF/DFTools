package dfutils.commands.shortcuts;

import diamondcore.utils.scheduledtasks.IScheduledTask;
import diamondcore.utils.scheduledtasks.ScheduledTaskManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;

import static diamondcore.utils.MessageUtils.actionMessage;

public class ShortcutPlotClear {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public static void shortcutPlotClearClientSendMessage(ClientChatEvent event) {
		
		if (minecraft.player.isCreative() && event.getMessage().equalsIgnoreCase("/plot clear")) {
			actionMessage("Clearing plot...");
			ScheduledTaskManager.scheduleTask(40, new CommandWait());
		}
	}
	
	static class CommandWait implements IScheduledTask {
		
		@Override
		public void runTask() {
			minecraft.player.sendChatMessage("/plot clear confirm");
		}
	}
}
