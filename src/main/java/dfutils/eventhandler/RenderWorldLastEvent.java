package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.codetools.copying.CopyEventHandler;
import dfutils.codetools.misctools.LocationHighlighting;
import dfutils.codetools.misctools.PistonHighlighting;
import dfutils.codetools.printing.PrintEventHandler;
import dfutils.codetools.selection.SelectionEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class RenderWorldLastEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onRenderWorldLast(net.minecraftforge.client.event.RenderWorldLastEvent event) {
		SelectionEventHandler.selectionEventHandlerRenderWorldLastEvent(event);
		PrintEventHandler.printEventHandlerRenderWorldLast(event);
		CopyEventHandler.copyEventHandlerRenderWorldLast(event);
		PistonHighlighting.pistonHighlightingRenderWorldLast(event);
		LocationHighlighting.locationHighlightingRenderWorldLast(event);
	}
}
