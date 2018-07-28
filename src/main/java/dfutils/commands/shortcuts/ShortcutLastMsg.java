package dfutils.commands.shortcuts;

import net.minecraftforge.client.event.ClientChatEvent;

public class ShortcutLastMsg {
    
    private static String lastPersonMessaged = "";
    
    public static void shortcutLastMsgClientSendMessage(ClientChatEvent event) {
    
        //Stores the last person messaged.
        if (event.getMessage().startsWith("/msg ")) {
            lastPersonMessaged = event.getMessage().substring(5, event.getMessage().indexOf(' ', 5));
        }

        //Last person messaged shortcut. (/l <message>)
        if (event.getMessage().startsWith("/l ")) {
            event.setMessage("/msg " + lastPersonMessaged + " " + event.getMessage().substring(3));
        }
    }
}
