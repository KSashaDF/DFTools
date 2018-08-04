package dfutils.eventhandler;

import dfutils.codetools.copying.CopyController;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class ClientTickEvent {

    private static boolean isFirstTick = true;
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        
        if (isFirstTick && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.ticksExisted > 0) {
            isFirstTick = false;
    
            SendPacketEvent.initializeEvent();
        }
        
        PrintEventHandler.printEventHandlerTickEvent(event);
        CopyController.copyControllerTickEvent(event);
        CodeQuickSelection.codeQuickSelectionTickEvent(event);
    }
}