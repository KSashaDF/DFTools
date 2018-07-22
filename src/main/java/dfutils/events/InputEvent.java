package dfutils.events;

import dfutils.InputHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class InputEvent {

    @SubscribeEvent
    public void onKeyInput(net.minecraftforge.fml.common.gameevent.InputEvent event) {
        InputHandler.inputHandlerKeyInput(event);
    }
}