package dfutils.eventhandler;

import dfutils.Reference;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ChatReceivedEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
		PlayerStateHandler.playerStateHandlerChatReceived(event);
	}
}
