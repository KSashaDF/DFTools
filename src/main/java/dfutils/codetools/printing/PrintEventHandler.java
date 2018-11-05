package dfutils.codetools.printing;

import diamondcore.eventhandler.customevents.CustomRightClickBlockEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PrintEventHandler {
	
	private static boolean skipGuiEvent = false;
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public static void printEventHandlerRightClickBlock(CustomRightClickBlockEvent event) {
		
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
	
	public static void printEventHandlerTickEvent(TickEvent.ClientTickEvent event) {
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
