package dfutils.utils.playerdata;

import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class PlayerDataUtils {

    public static int plotId;
    public static String plotName;
    public static String plotOwner;
    public static PlotSize plotSize;

    public static PlayerMode playerMode;

    public static void playerDataChatReceived(ClientChatReceivedEvent event) {
        String[] messageWords = event.getMessage().getUnformattedComponentText().split(" ");


    }
}
