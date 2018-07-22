package dfutils.commands.shortcuts;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ShortcutLastMsg {
    
    private static String lastPersonMessaged = "";
    
    @SubscribeEvent
    public void onClientSendMessage(ClientChatEvent event) {
    
        //Stores the last person messaged.
        if (event.getMessage().startsWith("/msg ")) {
            String message = event.getMessage();
            message = message.replace("/msg ", "");
        
            //Separates /msg name argument.
            for (int i = 0; i < message.length(); i++) {
                if (message.charAt(i) == ' ') {
                    char[] charArray = new char[i];
                    message.getChars(0, i, charArray, 0);
                    lastPersonMessaged = String.valueOf(charArray);
                    break;
                }
            }
        }

        //Last person messaged shortcut. (/l <message>)
        if (event.getMessage().startsWith("/l ")) {
            char[] charArray = new char[event.getMessage().length() - 3];
            event.getMessage().getChars(3, event.getMessage().length(), charArray, 0);
            event.setMessage("/msg " + lastPersonMessaged + " " + String.valueOf(charArray));
        }
    }
}
