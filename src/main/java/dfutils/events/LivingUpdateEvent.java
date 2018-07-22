package dfutils.events;

import dfutils.codetools.copying.CopyController;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LivingUpdateEvent {

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        PrintEventHandler.printEventHandlerLivingUpdate(event);
        CopyController.copyControllerLivingUpdate(event);
        CodeQuickSelection.codeQuickSelectionLivingUpdate(event);
    }
}