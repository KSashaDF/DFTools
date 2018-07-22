package dfutils.events;

import dfutils.commands.itemcontrol.CommandGive;
import dfutils.commands.shortcuts.ShortcutLastMsg;
import dfutils.commands.shortcuts.ShortcutSupportChat;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientChatEvent {

    @SubscribeEvent
    public void onClientSendMessage(net.minecraftforge.client.event.ClientChatEvent event) {
        CommandGive.commandGiveClientSendMessage(event);
        ShortcutLastMsg.shortcutLastMsgClientSendMessage(event);
        ShortcutSupportChat.shortcutSupportChatClientSendMessage(event);
    }
}