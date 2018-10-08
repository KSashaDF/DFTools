package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import dfutils.codetools.selection.SelectionEventHandler;
import diamondcore.eventhandler.customevents.CustomRightClickBlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
class RightClickBlockEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onRightClickBlock(CustomRightClickBlockEvent event) {
		SelectionEventHandler.selectionEventRightClickBlock(event);
		PrintEventHandler.printEventHandlerRightClickBlock(event);
		CodeQuickSelection.codeQuickSelectionRightClickBlock(event);
	}
	
}
