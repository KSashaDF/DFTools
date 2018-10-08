package dfutils.eventhandler;

import diamondcore.Reference;
import dfutils.utils.rpc.PresenceHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber(modid = dfutils.Reference.MOD_ID)
public class PlayerJoinEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		
		//Checks if the player is connecting to DiamondFire.
		if (event.getManager().getRemoteAddress().toString().endsWith(Reference.DIAMONDFIRE_IP)) {
			
			//Set Discord Presence
			PresenceHandler.updatePresence(false);
		}
	}
}
