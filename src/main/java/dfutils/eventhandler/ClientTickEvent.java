package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.codetools.copying.CopyController;
import dfutils.codetools.misctools.CodeQuickSelection;
import dfutils.codetools.printing.PrintEventHandler;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ClientTickEvent {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			if (PlayerStateHandler.isOnDiamondFire && minecraft.player != null) {
				PrintEventHandler.printEventHandlerTickEvent(event);
				CopyController.copyControllerTickEvent(event);
				CodeQuickSelection.codeQuickSelectionTickEvent(event);
				PlayerStateHandler.playerStateHandlerTickEvent(event);
			}
		}
	}
}