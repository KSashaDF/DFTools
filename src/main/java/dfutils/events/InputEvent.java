package dfutils.events;

// -------------------------
// Created by: Timeraa
// Created at: 22.07.18
// -------------------------

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static dfutils.InputHandler.inputHandlerKeyInput;

@Mod.EventBusSubscriber
public class InputEvent {
    @SubscribeEvent
    public void onKeyInput(net.minecraftforge.fml.common.gameevent.InputEvent event) {
        inputHandlerKeyInput(event);
    }
}