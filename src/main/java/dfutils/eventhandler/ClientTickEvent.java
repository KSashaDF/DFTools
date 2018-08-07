package dfutils.eventhandler;

import dfutils.codetools.copying.CopyController;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import dfutils.utils.MessageUtils;
import dfutils.utils.playerdata.PlayerStateHandler;
import dfutils.utils.rpc.PresenceHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class ClientTickEvent {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (PlayerStateHandler.isOnDiamondFire && minecraft.player != null) {
                PrintEventHandler.printEventHandlerTickEvent(event);
                CopyController.copyControllerTickEvent(event);
                CodeQuickSelection.codeQuickSelectionTickEvent(event);
                PlayerStateHandler.playerStateHandlerTickEvent(event);
        
            }
    
            //Makes it so the Discord rich presence updates every 15 seconds.
            if (minecraft.player != null && minecraft.player.ticksExisted % 300 == 0) {
                PresenceHandler.updatePresence();
            }
        }
    }
}