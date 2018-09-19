package diamondcore.eventhandler;

import diamondcore.eventhandler.customevents.CustomLeftClickBlockEvent;
import diamondcore.eventhandler.customevents.CustomRightClickBlockEvent;
import diamondcore.utils.EventUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class InputEvent {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final GameSettings gameSettings = minecraft.gameSettings;
    
    @SubscribeEvent
    public static void onKeyInput(net.minecraftforge.fml.common.gameevent.InputEvent event) {
        
        try {
            //Determines whether the leftClickBlock event should be fired.
            if (gameSettings.keyBindAttack.isKeyDown() && minecraft.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                
                //Creates the event object.
                CustomLeftClickBlockEvent leftClickBlockEvent = new CustomLeftClickBlockEvent(minecraft.objectMouseOver.sideHit, minecraft.objectMouseOver.getBlockPos());
                
                //Sends the event to the event handler class.
                MinecraftForge.EVENT_BUS.post(leftClickBlockEvent);
                
                //If the event cancel flag is set to true, cancel the event.
                if (leftClickBlockEvent.isCanceled()) {
                    EventUtils.cancelLeftClick(true);
                }
            }
            
            //Determines whether the rightClickBlock event should be fired.
            if (gameSettings.keyBindUseItem.isKeyDown() && minecraft.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                
                //Creates the event object.
                CustomRightClickBlockEvent rightClickBlockEvent = new CustomRightClickBlockEvent(minecraft.objectMouseOver.sideHit, minecraft.objectMouseOver.getBlockPos());
                
                //Sends the event to the event handler class.
                MinecraftForge.EVENT_BUS.post(rightClickBlockEvent);
                
                //If the event cancel flag is set to true, cancel the event.
                if (rightClickBlockEvent.isCanceled()) {
                    EventUtils.cancelRightClick();
                }
            }
        } catch (NullPointerException exception) {
            //Hmm... an NPE occurred for whatever reason, continue on.
        }
    }
}
