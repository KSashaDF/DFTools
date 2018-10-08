package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.utils.rpc.PresenceHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PlayerLeaveEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onClientDisconnectedFromServerEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		//Clear Discord RPC
		PresenceHandler.destroyPresence();
	}
}
