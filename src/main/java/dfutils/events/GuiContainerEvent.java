package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.codetools.copying.CopyEventHandler.copyEventHandlerPlayerContainerEvent;
import static dfutils.codetools.misctools.CodeQuickSelection.codeQuickSelectionPlayerContainerEvent;
import static dfutils.codetools.printing.PrintEventHandler.printEventHandlerPlayerContainerEvent;

@Mod.EventBusSubscriber
public class GuiContainerEvent {
    @SubscribeEvent
    public void onPlayerContainerEvent(net.minecraftforge.client.event.GuiContainerEvent event) {
        printEventHandlerPlayerContainerEvent(event);
        copyEventHandlerPlayerContainerEvent(event);
        codeQuickSelectionPlayerContainerEvent(event);
    }
}
