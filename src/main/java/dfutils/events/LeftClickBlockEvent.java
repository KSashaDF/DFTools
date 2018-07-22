package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.codetools.misctools.LocationSetter.locationSetterLeftClickBlock;
import static dfutils.codetools.misctools.PistonHighlighting.pistonHighlightingLeftClickBlock;

@Mod.EventBusSubscriber
public class LeftClickBlockEvent {
    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        pistonHighlightingLeftClickBlock(event);
        locationSetterLeftClickBlock(event);
    }
}