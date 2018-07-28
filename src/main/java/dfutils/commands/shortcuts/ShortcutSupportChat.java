package dfutils.commands.shortcuts;

import net.minecraftforge.client.event.ClientChatEvent;

public class ShortcutSupportChat {
    
    public static void shortcutSupportChatClientSendMessage(ClientChatEvent event) {
        
        //Support chat shortcut (/sb <message>)
        if (event.getMessage().startsWith("/sb ")) {
            event.setMessage("/support b " + event.getMessage().substring(4));
        }
    }
}

