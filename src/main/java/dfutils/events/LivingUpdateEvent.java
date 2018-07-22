package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.codetools.copying.CopyController.copyControllerLivingUpdate;
import static dfutils.codetools.misctools.CodeQuickSelection.codeQuickSelectionLivingUpdate;
import static dfutils.codetools.printing.PrintEventHandler.printEventHandlerLivingUpdate;

@Mod.EventBusSubscriber
public class LivingUpdateEvent {
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        printEventHandlerLivingUpdate(event);
        copyControllerLivingUpdate(event);
        codeQuickSelectionLivingUpdate(event);
    }
}