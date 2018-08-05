package dfutils.eventhandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber
public class PlayerJoinEvent {
    
    @SubscribeEvent
    public void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (event.getConnectionType().equals("VANILLA")) {
            SendPacketEvent.initializeEvent(event);
        }
    }
}
