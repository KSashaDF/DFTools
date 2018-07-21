package dfutils.codetools.copying;

import dfutils.ColorReference;
import dfutils.codetools.utils.BlockUtils;
import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.codetools.utils.GraphicsUtils;
import net.minecraft.util.math.BlockPos;

class CopyRenderer {
    
    static void renderCopySelection(float partialTicks) {
    
        BlockPos renderPos = CopyController.copySelection[0];
        ColorReference drawColor;
    
        do {
            //Highlights the codeblock currently being copied.
            if (renderPos.equals(CopyController.copyPos)) {
                drawColor = ColorReference.BRIGHT_COPY_CODE;
            } else {
                drawColor = ColorReference.DULL_COPY_CODE;
            }
            
            if (BlockUtils.getName(renderPos).equals("Air"))
                renderPos = renderPos.south();
        
            GraphicsUtils.drawBlock(partialTicks, renderPos, drawColor);
        
            if (BlockUtils.getName(renderPos.west()).equals("Sign"))
                GraphicsUtils.drawSign(partialTicks, renderPos.west(), drawColor);
        
            if (BlockUtils.getName(renderPos.up()).equals("Chest"))
                GraphicsUtils.drawChest(partialTicks, renderPos.up(), drawColor);
        
            renderPos = renderPos.south();
        
        } while (BlockUtils.isWithinRegion(CodeBlockUtils.getBlockCore(renderPos), CopyController.copySelection[0], CopyController.copyPos));
    }
}
