package dfutils.events;

import dfutils.codetools.misctools.LocationSetter;
import dfutils.codetools.misctools.PistonHighlighting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LeftClickBlockEvent {

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        PistonHighlighting.pistonHighlightingLeftClickBlock(event);
        LocationSetter.locationSetterLeftClickBlock(event);
    }
}