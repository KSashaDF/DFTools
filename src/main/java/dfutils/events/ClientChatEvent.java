package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.commands.itemcontrol.CommandGive.commandGiveClientSendMessage;
import static dfutils.commands.shortcuts.ShortcutLastMsg.shortcutLastMsgClientSendMessage;
import static dfutils.commands.shortcuts.ShortcutSupportChat.shortcutSupportChatClientSendMessage;

@Mod.EventBusSubscriber
public class ClientChatEvent {
    @SubscribeEvent
    public void onClientSendMessage(net.minecraftforge.client.event.ClientChatEvent event) {
        commandGiveClientSendMessage(event);
        shortcutLastMsgClientSendMessage(event);
        shortcutSupportChatClientSendMessage(event);
    }
}