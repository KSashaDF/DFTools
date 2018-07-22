package dfutils.codetools.printing;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PrintEventHandler {
    
    private static boolean skipGuiEvent = false;
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    public static void printEventHandlerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        
        if (minecraft.player.isCreative()) {
            if (minecraft.player.getHeldItemMainhand().hasTagCompound()) {
                if (minecraft.player.getHeldItemMainhand().getTagCompound().hasKey("CodeData")) {
                    event.setCanceled(true);
                    PrintController.initializePrint(minecraft.player.getHeldItemMainhand().getTagCompound().getTagList("CodeData", 10));
                }
            }
        }
    }
    
    public static void printEventHandlerRenderWorldLast(RenderWorldLastEvent event) {
        if (PrintController.isPrinting) {
            PrintRenderer.renderPrintSelection(event.getPartialTicks());
        }
    }
    
    public static void printEventHandlerLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (PrintController.isPrinting) {
            if (minecraft.player.isCreative()) {
                PrintController.updatePrint();
            } else {
                PrintController.resetPrint();
            }
        }
    }
    
    public static void printEventHandlerPlayerContainerEvent(GuiContainerEvent event) {
        if (PrintController.isPrinting && PrintController.printSubState == PrintSubState.EVENT_WAIT) {
            
            if (skipGuiEvent) {
                skipGuiEvent = false;
                return;
            } else {
                skipGuiEvent = true;
            }
            
            switch (PrintController.printState) {
                case CHEST:
                    PrintController.openedCodeChest(event.getGuiContainer().inventorySlots);
                    break;
                    
                case SIGN:
                    PrintController.openedCodeGui(event.getGuiContainer().inventorySlots);
            }
        }
    }
}
