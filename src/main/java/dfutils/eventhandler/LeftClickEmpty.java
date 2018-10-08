package dfutils.eventhandler;

import dfutils.Reference;
import dfutils.codetools.misctools.QuickItemRename;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class LeftClickEmpty {
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
		QuickItemRename.quickItemRenameLeftClickEmpty(event);
	}
}