package diamondcore.eventhandler;

import diamondcore.Reference;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PlayerJoinEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onClientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		
		//Checks if the player is connecting to DiamondFire.
		if (event.getManager().getRemoteAddress().toString().endsWith(Reference.DIAMONDFIRE_IP)) {
			PlayerStateHandler.playerStateHandlerJoinEvent(event);
		} else {
			PlayerStateHandler.isOnDiamondFire = false;
		}
		
		SendPacketEvent.initializeEvent(event);
	}
}
