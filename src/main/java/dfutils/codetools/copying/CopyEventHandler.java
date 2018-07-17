package dfutils.codetools.copying;

import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CopyEventHandler {
    
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (CopyController.isCopying) {
            CopyRenderer.renderCopySelection(event.getPartialTicks());
        }
    }
    
    @SubscribeEvent
    public void onPlayerContainerEvent(GuiContainerEvent event) {
        if (CopyController.isCopying) {
            if (CopyController.copyState == CopyState.OPEN_CHEST_WAIT) {
                CopyController.writeChestData(event.getGuiContainer().inventorySlots);
            }
        }
    }
}
