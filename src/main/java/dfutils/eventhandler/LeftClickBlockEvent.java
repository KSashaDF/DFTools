package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.codetools.misctools.LocationSetter;
import dfutils.codetools.misctools.PistonHighlighting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class LeftClickBlockEvent {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		PistonHighlighting.pistonHighlightingLeftClickBlock(event);
		
		//Ensures that the currently held location is NOT set if the piston highlighting has been toggled.
		if (!event.isCanceled()) {
			LocationSetter.locationSetterLeftClickBlock(event);
		}
	}
}