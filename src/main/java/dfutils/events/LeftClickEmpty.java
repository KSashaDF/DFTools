package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.codetools.misctools.QuickItemRename.quickItemRenameLeftClickEmpty;

@Mod.EventBusSubscriber
public class LeftClickEmpty {
    @SubscribeEvent
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        quickItemRenameLeftClickEmpty(event);
    }
}