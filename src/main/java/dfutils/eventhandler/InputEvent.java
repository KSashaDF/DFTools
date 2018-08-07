package dfutils.eventhandler;

import dfutils.InputHandler;
import dfutils.utils.EventUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class InputEvent {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static final GameSettings gameSettings = minecraft.gameSettings;

    @SubscribeEvent
    public void onKeyInput(net.minecraftforge.fml.common.gameevent.InputEvent event) {
    
        try {
            //Determines whether the leftClickBlock event should be fired.
            if (gameSettings.keyBindAttack.isKeyDown() && minecraft.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
            
                //Creates the event object.
                PlayerInteractEvent.LeftClickBlock leftClickBlockEvent = new PlayerInteractEvent.LeftClickBlock(minecraft.player, minecraft.objectMouseOver.getBlockPos(), minecraft.objectMouseOver.sideHit,
                        ForgeHooks.rayTraceEyeHitVec(minecraft.player, minecraft.playerController.getBlockReachDistance() + 1));
            
                //Sends the event to the event handler class.
                new LeftClickBlockEvent().onLeftClickBlock(leftClickBlockEvent);
            
                //If the event cancel flag is set to true, cancel the event.
                if (leftClickBlockEvent.isCanceled()) {
                    EventUtils.cancelLeftClick(true);
                }
            }
        
            //Determines whether the rightClickBlock event should be fired.
            if (gameSettings.keyBindUseItem.isKeyDown() && minecraft.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
            
                //Creates the event object.
                PlayerInteractEvent.RightClickBlock rightClickBlockEvent = new PlayerInteractEvent.RightClickBlock(minecraft.player, EnumHand.MAIN_HAND, minecraft.objectMouseOver.getBlockPos(), minecraft.objectMouseOver.sideHit,
                        ForgeHooks.rayTraceEyeHitVec(minecraft.player, minecraft.playerController.getBlockReachDistance() + 1));
            
                //Sends the event to the event handler class.
                new RightClickBlockEvent().onRightClickBlock(rightClickBlockEvent);
            
                //If the event cancel flag is set to true, cancel the event.
                if (rightClickBlockEvent.isCanceled()) {
                    EventUtils.cancelRightClick();
                }
            }
        
            InputHandler.inputHandlerKeyInput(event);
        } catch (NullPointerException exception) {
            //Hmm... an NPE occurred for whatever reason, continue on.
        }
    }
}