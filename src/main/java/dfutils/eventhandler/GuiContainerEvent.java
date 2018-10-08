package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.codetools.copying.CopyEventHandler;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class GuiContainerEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onPlayerContainerEvent(net.minecraftforge.client.event.GuiContainerEvent event) {
		PrintEventHandler.printEventHandlerPlayerContainerEvent(event);
		CopyEventHandler.copyEventHandlerPlayerContainerEvent(event);
		CodeQuickSelection.codeQuickSelectionPlayerContainerEvent(event);
	}
}
