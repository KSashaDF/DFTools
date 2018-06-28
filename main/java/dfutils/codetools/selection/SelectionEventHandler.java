package dfutils.codetools.selection;

import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.codetools.utils.CodeFormatException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SelectionEventHandler {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    private static int rightClickCooldown = 0;
    
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        
        if (rightClickCooldown - 3 > minecraft.player.ticksExisted) rightClickCooldown = 0;
        
        if (minecraft.player.isCreative() && minecraft.player.ticksExisted > rightClickCooldown) {
            if (minecraft.player.getHeldItemMainhand().getDisplayName().equals("§eCode Selection Stick")) {
                if (CodeBlockUtils.isCodeBlock(minecraft.objectMouseOver.getBlockPos())) {
                    
                    rightClickCooldown = minecraft.player.ticksExisted + 2;
                    event.setCanceled(true);
                    BlockPos codeBlockPos = CodeBlockUtils.getBlockCore(minecraft.objectMouseOver.getBlockPos());
                    
                    try {
                        if (!SelectionController.selectionActive || !SelectionController.isWithinSelection(codeBlockPos)) {
    
                            SelectionController.selectionActive = true;
                            SelectionController.selectionPos = codeBlockPos;
                            SelectionController.selectionState = SelectionState.CODEBLOCK;
                            
                        } else {
                            
                            SelectionController.selectionPos = codeBlockPos;
                            incrementState();
                        }
                    } catch (CodeFormatException exception) {}
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (SelectionController.selectionActive) {
            SelectionController.renderSelection(Tessellator.getInstance(), event.getPartialTicks());
            
            if (!minecraft.player.isCreative()) {
                SelectionController.resetSelection();
            }
        }
    }
    
    private void incrementState() {
        switch (SelectionController.selectionState) {
            case NULL:
                SelectionController.selectionState = SelectionState.CODEBLOCK;
                return;
            
            case CODEBLOCK:
                SelectionController.selectionState = SelectionState.LOCAL_SCOPE;
                return;
                
            case LOCAL_SCOPE:
                SelectionController.selectionState = SelectionState.CODE_LINE;
                return;
                
            case CODE_LINE:
                SelectionController.resetSelection();
        }
    }
}
