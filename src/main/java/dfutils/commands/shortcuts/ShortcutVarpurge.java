package dfutils.commands.shortcuts;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;

import static diamondcore.utils.MessageUtils.errorMessage;
import static diamondcore.utils.MessageUtils.infoMessage;

public class ShortcutVarpurge {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	private static long commandTypedTime = 0;
	
	public static void shortcutVarpurgeClientSendMessage(ClientChatEvent event) {
		
		//Small fail safe in case the player relogs.
		if (commandTypedTime - 200 > minecraft.player.ticksExisted)
			commandTypedTime = 0;
		
		if (minecraft.player.isCreative()) {
			if (event.getMessage().equalsIgnoreCase("/plot varpurge")) {
				infoMessage("Type /plot varpurge confirm to confirm this action.");
				commandTypedTime = minecraft.player.ticksExisted + 200;
				event.setCanceled(true);
				minecraft.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
			}
			
			if (event.getMessage().equalsIgnoreCase("/plot varpurge confirm")) {
				if (commandTypedTime > minecraft.player.ticksExisted) {
					commandTypedTime = 0;
					event.setCanceled(true);
					minecraft.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
					
					minecraft.player.sendChatMessage("/plot varpurge");
				} else {
					errorMessage("Type /plot varpurge first!");
					event.setCanceled(true);
					minecraft.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
				}
			}
		}
	}
}
