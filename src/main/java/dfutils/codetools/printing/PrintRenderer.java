package dfutils.codetools.printing;

import dfutils.utils.ColorReference;
import dfutils.codehandler.utils.CodeBlockUtils;
import dfutils.utils.GraphicsUtils;
import net.minecraft.util.math.BlockPos;

class PrintRenderer {
    
    static void renderPrintSelection(float partialTicks) {
        
        PrintNbtHandler renderNbtReader = new PrintNbtHandler();
        renderNbtReader.initializePrintData(PrintController.printNbtHandler.printData);
        
        BlockPos renderPos = PrintController.printSelection[0];
        ColorReference drawColor;
        
        do {
            //Makes it so the printer head code block is highlighted.
            if (renderPos.equals(PrintController.printPos)) {
                drawColor = ColorReference.BRIGHT_PASTE_CODE;
            } else {
                drawColor = ColorReference.DULL_PASTE_CODE;
            }
            
            //Draws the code block core and the code block connector.
            GraphicsUtils.drawBlock(partialTicks, renderPos, drawColor);
            GraphicsUtils.drawBlock(partialTicks, renderPos.south(), drawColor);
            
            //Draws code block sign.
            if (CodeBlockUtils.stringToBlock(renderNbtReader.selectedBlock.getString("Name")).hasCodeSign)
                GraphicsUtils.drawSign(partialTicks, renderPos.west(), drawColor);
            
            //Draws code chest.
            if (CodeBlockUtils.stringToBlock(renderNbtReader.selectedBlock.getString("Name")).hasCodeChest)
                GraphicsUtils.drawChest(partialTicks, renderPos.up(), drawColor);
            
            //Draws opposite piston. (the closing piston)
            if (CodeBlockUtils.stringToBlock(renderNbtReader.selectedBlock.getString("Name")).hasPistonBrackets)
                GraphicsUtils.drawBlock(partialTicks,
                        renderPos.south(PrintController.getCodeLength(renderNbtReader.selectedBlock.getTagList("CodeData", 10)) + 3), drawColor);
            
            //Checks if the selected code block is before a closing piston, if so, skip ahead 2 blocks.
            if (renderNbtReader.shouldExitScope())
                renderPos = renderPos.south(2);
            
            //Checks if code brackets are empty, if so, skip ahead 2 blocks.
            if (renderNbtReader.selectedBlock.hasKey("CodeData") && renderNbtReader.selectedBlock.getTagList("CodeData", 10).tagCount() == 0)
                renderPos = renderPos.south(2);
            
            //In case there are multiple closing pistons grouped together, the next code block finder is looped.
            do {
                //Gets the next code block and goes forward 2 blocks.
                renderNbtReader.nextCodeBlock();
                renderPos = renderPos.south(2);
            } while (!renderNbtReader.selectedBlock.hasKey("Name") && !renderNbtReader.reachedCodeEnd);
            
        } while (!renderNbtReader.reachedCodeEnd);
    }
}
