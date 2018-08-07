package dfutils.commands.shortcuts;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;

public class ShortcutSupportChat {
    
    public static void shortcutSupportChatClientSendMessage(ClientChatEvent event) {
        
        //Support chat shortcut (/sb <message>)
        if (event.getMessage().startsWith("/sb ")) {
            event.setCanceled(true);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            Minecraft.getMinecraft().player.sendChatMessage("/support b " + event.getMessage().substring(4));
        }
    }
}

