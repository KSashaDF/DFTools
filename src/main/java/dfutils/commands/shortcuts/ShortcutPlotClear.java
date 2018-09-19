package dfutils.commands.shortcuts;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;

import static diamondcore.utils.MessageUtils.actionMessage;

public class ShortcutPlotClear {

    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public static void shortcutPlotClearClientSendMessage(ClientChatEvent event) {

        if (minecraft.player.isCreative() && event.getMessage().equalsIgnoreCase("/plot clear")) {
            actionMessage("Clearing plot...");
            new Thread(new CommandWait()).start();
        }
    }

    static class CommandWait implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                minecraft.player.sendChatMessage("/plot clear confirm");
            } catch (InterruptedException exception) {
                //Uh oh! Thread wait interrupted, continue on.
            }
        }
    }
}
