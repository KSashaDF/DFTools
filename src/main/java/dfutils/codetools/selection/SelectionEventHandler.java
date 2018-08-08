package dfutils.codetools.selection;

import dfutils.codehandler.utils.CodeBlockUtils;
import dfutils.utils.CodeFormatException;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class SelectionEventHandler {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private static int rightClickCooldown = 0;
    
    public static void selectionEventRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

        if (rightClickCooldown - 3 > minecraft.player.ticksExisted) rightClickCooldown = 0;
        
        if (minecraft.player.isCreative() && minecraft.player.ticksExisted > rightClickCooldown) {
            if (minecraft.player.getHeldItemMainhand().getDisplayName().equals("§6* §eCode Selection Stick §6*")) {
                if (CodeBlockUtils.isCodeBlock(minecraft.objectMouseOver.getBlockPos())) {
                    
                    rightClickCooldown = minecraft.player.ticksExisted + 2;
                    event.setCanceled(true);
                    BlockPos codeBlockPos = CodeBlockUtils.getBlockCore(minecraft.objectMouseOver.getBlockPos());

                    try {
                        minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1, 2);

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
    
    public static void selectionEventHandlerRenderWorldLastEvent(RenderWorldLastEvent event) {
        if (SelectionController.selectionActive) {
            SelectionController.renderSelection(event.getPartialTicks());
            
            if (!minecraft.player.isCreative()) {
                SelectionController.resetSelection();
            }
        }
    }
    
    private static void incrementState() {
        SelectionState nextSelectionState = SelectionState.NULL;
        
        switch (SelectionController.selectionState) {
            case NULL:
                nextSelectionState= SelectionState.CODEBLOCK;
                break;
            
            case CODEBLOCK:
                nextSelectionState = SelectionState.LOCAL_SCOPE;
                break;
                
            case LOCAL_SCOPE:
                nextSelectionState = SelectionState.CODE_LINE;
                break;
                
            case CODE_LINE:
                SelectionController.resetSelection();
                return;
        }
        
        try {
            BlockPos[] selectionEdges = SelectionController.getSelectionEdges();
            SelectionController.selectionState = nextSelectionState;
            BlockPos[] newSelectionEdges = SelectionController.getSelectionEdges();
            
            if (SelectionController.selectionState != SelectionState.NULL) {
                if (selectionEdges[0].equals(newSelectionEdges[0]) && selectionEdges[1].equals(newSelectionEdges[0])) {
                    incrementState();
                }
            }
            
        } catch (CodeFormatException exception) {
            SelectionController.resetSelection();
            exception.printError();
        }
    }
}
