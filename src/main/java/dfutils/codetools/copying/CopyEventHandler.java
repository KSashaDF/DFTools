package dfutils.codetools.copying;

import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CopyEventHandler {

    public static void copyEventHandlerRenderWorldLast(RenderWorldLastEvent event) {
        if (CopyController.isCopying) {
            CopyRenderer.renderCopySelection(event.getPartialTicks());
        }
    }
    
    public static void copyEventHandlerPlayerContainerEvent(GuiContainerEvent event) {
        if (CopyController.isCopying) {
            if (CopyController.copyState == CopyState.OPEN_CHEST_WAIT) {
                CopyController.writeChestData(event.getGuiContainer().inventorySlots);
            }
        }
    }
}
