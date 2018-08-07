package dfutils.eventhandler;

import dfutils.codetools.misctools.QuickItemRename;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LeftClickEmpty {

    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        QuickItemRename.quickItemRenameLeftClickEmpty(event);
    }
}