package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.codetools.copying.CopyEventHandler.copyEventHandlerRenderWorldLast;
import static dfutils.codetools.misctools.LocationHighlighting.locationHighlightingRenderWorldLast;
import static dfutils.codetools.misctools.PistonHighlighting.pistonHighlightingRenderWorldLast;
import static dfutils.codetools.printing.PrintEventHandler.printEventHandlerRenderWorldLast;
import static dfutils.codetools.selection.SelectionEventHandler.selectionEventHandlerRenderWorldLastEvent;

@Mod.EventBusSubscriber
public class RenderWorldLastEvent {

    @SubscribeEvent
    public void onRenderWorldLast(net.minecraftforge.client.event.RenderWorldLastEvent event) {
        selectionEventHandlerRenderWorldLastEvent(event);
        printEventHandlerRenderWorldLast(event);
        copyEventHandlerRenderWorldLast(event);
        pistonHighlightingRenderWorldLast(event);
        locationHighlightingRenderWorldLast(event);
    }
}
