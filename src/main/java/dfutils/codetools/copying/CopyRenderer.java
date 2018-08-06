package dfutils.codetools.copying;

import dfutils.utils.ColorReference;
import dfutils.utils.BlockUtils;
import dfutils.codehandler.utils.CodeBlockUtils;
import dfutils.utils.GraphicsUtils;
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
            
            if (BlockUtils.getName(renderPos).equals("minecraft:air"))
                renderPos = renderPos.south();
        
            GraphicsUtils.drawBlock(partialTicks, renderPos, drawColor);
        
            if (BlockUtils.getName(renderPos.west()).equals("minecraft:wall_sign"))
                GraphicsUtils.drawSign(partialTicks, renderPos.west(), drawColor);
        
            if (BlockUtils.getName(renderPos.up()).equals("minecraft:chest"))
                GraphicsUtils.drawChest(partialTicks, renderPos.up(), drawColor);
        
            renderPos = renderPos.south();
        
        } while (BlockUtils.isWithinRegion(CodeBlockUtils.getBlockCore(renderPos), CopyController.copySelection[0], CopyController.copyPos));
    }
}
