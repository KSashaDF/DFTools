package dfutils.events;

import dfutils.commands.itemcontrol.CommandGive;
import dfutils.commands.shortcuts.ShortcutLastMsg;
import dfutils.commands.shortcuts.ShortcutPlotClear;
import dfutils.commands.shortcuts.ShortcutSupportChat;
import dfutils.commands.shortcuts.ShortcutVarpurge;
import dfutils.config.ConfigHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientChatEvent {

    @SubscribeEvent
    public void onClientSendMessage(final net.minecraftforge.client.event.ClientChatEvent event) {
        CommandGive.commandGiveClientSendMessage(event);
        ShortcutLastMsg.shortcutLastMsgClientSendMessage(event);
        ShortcutSupportChat.shortcutSupportChatClientSendMessage(event);

        if (ConfigHandler.DO_VARPURGE_CONFIRM) {
            ShortcutVarpurge.shortcutVarpurgeClientSendMessage(event);
        }
        if (!ConfigHandler.DO_PLOTCLEAR_CONFIRM) {
            ShortcutPlotClear.shortcutPlotClearClientSendMessage(event);
        }
    }
}