package dfutils.commands.shortcuts;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;

public class ShortcutLastMsg {
	
	private static String lastPersonMessaged = "";
	
	public static void shortcutLastMsgSendMessage(ClientChatEvent event) {
		
		//Stores the last person messaged.
		//Tests if the sent chat message is the /msg command, and tests if the message contains at least 2 spaces.
		//(tests if the command has a text argument and not just a name argument)
		if (event.getMessage().startsWith("/msg ") && (event.getMessage().indexOf(' ') != event.getMessage().lastIndexOf(' '))) {
			lastPersonMessaged = event.getMessage().substring(5, event.getMessage().indexOf(' ', 5));
		}
		
		//Last person messaged shortcut. (/l <message>)
		if (event.getMessage().startsWith("/l ")) {
			event.setCanceled(true);
			Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
			Minecraft.getMinecraft().player.sendChatMessage("/msg " + lastPersonMessaged + " " + event.getMessage().substring(3));
		}
	}
}
