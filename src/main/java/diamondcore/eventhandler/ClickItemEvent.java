package diamondcore.eventhandler;

import diamondcore.Reference;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ClickItemEvent {
	
	//This method is called whenever the player clicks an item inside a container.
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onClickItemEvent(diamondcore.eventhandler.customevents.ClickItemEvent event) {
		if (PlayerStateHandler.isOnDiamondFire) {
			PlayerStateHandler.playerStateHandlerClickItemEvent(event);
		}
	}
}
