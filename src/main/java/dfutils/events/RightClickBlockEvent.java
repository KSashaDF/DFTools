package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.codetools.misctools.CodeQuickSelection.codeQuickSelectionRightClickBlock;
import static dfutils.codetools.printing.PrintEventHandler.printEventHandlerRightClickBlock;
import static dfutils.codetools.selection.SelectionEventHandler.selectionEventHandlerRightClickBlockSelectionStick;

@Mod.EventBusSubscriber
public class RightClickBlockEvent {

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        selectionEventHandlerRightClickBlockSelectionStick(event);
        printEventHandlerRightClickBlock(event);
        codeQuickSelectionRightClickBlock(event);
    }

}
