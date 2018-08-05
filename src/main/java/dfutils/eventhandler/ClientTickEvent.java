package dfutils.eventhandler;

import dfutils.codetools.copying.CopyController;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class ClientTickEvent {
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        PrintEventHandler.printEventHandlerTickEvent(event);
        CopyController.copyControllerTickEvent(event);
        CodeQuickSelection.codeQuickSelectionTickEvent(event);
    }
}