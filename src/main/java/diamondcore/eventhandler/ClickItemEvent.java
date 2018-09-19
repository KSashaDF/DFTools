package diamondcore.eventhandler;

import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClickItemEvent {
    
    //This method is called whenever the player clicks an item inside a container.
    @SubscribeEvent
    public static void onClickItemEvent(diamondcore.eventhandler.customevents.ClickItemEvent event) {
        if (PlayerStateHandler.isOnDiamondFire) {
            PlayerStateHandler.playerStateHandlerClickItemEvent(event);
        }
    }
}
