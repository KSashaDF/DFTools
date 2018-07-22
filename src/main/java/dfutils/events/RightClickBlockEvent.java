package dfutils.events;

import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import dfutils.codetools.selection.SelectionEventHandler;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RightClickBlockEvent {

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        SelectionEventHandler.selectionEventHandlerRightClickBlockSelectionStick(event);
        PrintEventHandler.printEventHandlerRightClickBlock(event);
        CodeQuickSelection.codeQuickSelectionRightClickBlock(event);
    }

}
