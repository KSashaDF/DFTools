package dfutils.events;

import dfutils.codetools.misctools.LocationSetter;
import dfutils.codetools.misctools.PistonHighlighting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

//NOTE: This event is called from the InputEvent, this is so the event can be cancelled.
class LeftClickBlockEvent {

    void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        PistonHighlighting.pistonHighlightingLeftClickBlock(event);

        //Ensures that the currently held location is NOT set if the piston highlighting has been toggled.
        if (!event.isCanceled()) {
            LocationSetter.locationSetterLeftClickBlock(event);
        }
    }
}