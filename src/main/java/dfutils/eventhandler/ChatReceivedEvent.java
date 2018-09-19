package dfutils.eventhandler;

import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ChatReceivedEvent {

    @SubscribeEvent
    public static void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        PlayerStateHandler.playerStateHandlerChatReceived(event);
    }
}
