package dfutils.events;

import dfutils.utils.playerdata.PlayerDataUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ChatReceivedEvent {

    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        PlayerDataUtils.playerDataChatReceived(event);
    }
}
