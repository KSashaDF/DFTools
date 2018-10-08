package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.config.ConfigHandler;
import dfutils.utils.rpc.PresenceHandler;
import diamondcore.utils.playerdata.PlayerStateHandler;
import diamondcore.utils.playerdata.SupportSessionRole;
import diamondcore.utils.scheduledtasks.IScheduledTask;
import diamondcore.utils.scheduledtasks.ScheduledTaskManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class DiamondFireEvent {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onDiamondFireEvent(diamondcore.eventhandler.customevents.DiamondFireEvent event) {
		
		if (event instanceof diamondcore.eventhandler.customevents.DiamondFireEvent.EnterSessionEvent) {
			if (PlayerStateHandler.supportSessionRole == SupportSessionRole.SUPPORTER && !ConfigHandler.SUPPORT_START_MESSAGE.equals("")) {
				ScheduledTaskManager.scheduleTask(40, new CommandWait(ConfigHandler.SUPPORT_START_MESSAGE.replace("%player%", PlayerStateHandler.supportPartner)));
			}
		}
		
		if (event instanceof diamondcore.eventhandler.customevents.DiamondFireEvent.ExitSessionEvent) {
			if (PlayerStateHandler.supportSessionRole == SupportSessionRole.SUPPORTER && ConfigHandler.SUPPORT_END_AUTOMATIC_LEAVE) {
				minecraft.player.sendChatMessage("/spawn");
			}
			
			if (PlayerStateHandler.supportSessionRole == SupportSessionRole.SUPPORTER && !ConfigHandler.SUPPORT_END_MESSAGE.equals("")) {
				ScheduledTaskManager.scheduleTask(40, new CommandWait(ConfigHandler.SUPPORT_END_MESSAGE.replace("%player%", PlayerStateHandler.supportPartner)));
			}
		}
		
		//Clear all potion effect particles, this is to fix the irremovable potion particle glitch.
		minecraft.player.clearActivePotions();
		
		// Update Discord Presence
		PresenceHandler.updatePresence(PlayerStateHandler.discordRPCForceReload);
		PlayerStateHandler.discordRPCForceReload = false;
	}
	
	static class CommandWait implements IScheduledTask {
		
		String supportMessage;
		
		CommandWait(String supportMessage) {
			this.supportMessage = supportMessage;
		}
		
		@Override
		public void runTask() {
			minecraft.player.sendChatMessage(supportMessage);
		}
	}
}
