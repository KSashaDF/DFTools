package dfutils.eventhandler;

import dfutils.utils.rpc.PresenceHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber
public class PlayerLeaveEvent {
    
    @SubscribeEvent
    public void onClientDisconnectedFromServerEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        //* Clear Discord RPC
        PresenceHandler.destroyPresence();
    }
}
