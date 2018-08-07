package dfutils.eventhandler;

import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import dfutils.codetools.selection.SelectionEventHandler;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

//NOTE: This event is called from the InputEvent, this is so the event can be cancelled.
class RightClickBlockEvent {

    void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        SelectionEventHandler.selectionEventRightClickBlock(event);
        PrintEventHandler.printEventHandlerRightClickBlock(event);
        CodeQuickSelection.codeQuickSelectionRightClickBlock(event);
    }

}
