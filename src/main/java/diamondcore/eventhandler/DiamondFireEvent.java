package diamondcore.eventhandler;

import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DiamondFireEvent {
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDiamondFireEvent(diamondcore.eventhandler.customevents.DiamondFireEvent event) {
        PlayerStateHandler.diamondFireEventHandler(event);
    }
}
