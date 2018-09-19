package dfutils.eventhandler;

import dfutils.codetools.copying.CopyEventHandler;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class GuiContainerEvent {

    @SubscribeEvent
    public static void onPlayerContainerEvent(net.minecraftforge.client.event.GuiContainerEvent event) {
        PrintEventHandler.printEventHandlerPlayerContainerEvent(event);
        CopyEventHandler.copyEventHandlerPlayerContainerEvent(event);
        CodeQuickSelection.codeQuickSelectionPlayerContainerEvent(event);
    }
}
