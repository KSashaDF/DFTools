package diamondfireutils.commands.shortcuts;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ShortcutSupportChat {
    
    @SubscribeEvent
    public void onClientSendMessage(ClientChatEvent event) {
        
        //Support chat shortcut (/sb <message>)
        if (event.getMessage().startsWith("/sb ")) {
            char[] charArray = new char[event.getMessage().length() - 4];
            event.getMessage().getChars(4, event.getMessage().length(), charArray, 0);
            event.setMessage("/support b " + String.valueOf(charArray));
        }
    }
}

