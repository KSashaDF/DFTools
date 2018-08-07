package dfutils.codetools.selection;

import dfutils.utils.ColorReference;
import dfutils.codehandler.utils.CodeBlockType;
import dfutils.utils.BlockUtils;
import dfutils.codehandler.utils.CodeBlockUtils;
import dfutils.utils.CodeFormatException;
import dfutils.utils.GraphicsUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class SelectionController {
    
    public static boolean selectionActive = false;
    static BlockPos selectionPos;
    public static SelectionState selectionState = SelectionState.NULL;
    
    static void renderSelection(float partialTicks) {
    
        BlockPos[] selectionEdges;
        
        try {
            selectionEdges = getSelectionEdges();
            
        } catch (CodeFormatException exception) {
            resetSelection();
            exception.printError();
            
            return;
        }
        
        BlockPos renderPos = selectionEdges[0];
        ColorReference drawColor = ColorReference.DULL_COPY_CODE;
        
        do {
            
            if (BlockUtils.getName(renderPos).equals("minecraft:air"))
                renderPos = renderPos.south();
    
            GraphicsUtils.drawBlock(partialTicks, renderPos, drawColor);
            
            if (BlockUtils.getName(renderPos.west()).equals("minecraft:wall_sign"))
                GraphicsUtils.drawSign(partialTicks, renderPos.west(), drawColor);
            
            if (BlockUtils.getName(renderPos.up()).equals("minecraft:chest"))
                GraphicsUtils.drawChest(partialTicks, renderPos.up(), drawColor);
            
            renderPos = renderPos.south();
            
        } while (BlockUtils.isWithinRegion(CodeBlockUtils.getBlockCore(renderPos), selectionEdges[0], selectionEdges[1]));
    }
    
    static boolean isWithinSelection(BlockPos checkPos) throws CodeFormatException {
        if (selectionPos == null) return false;
        
        checkPos = CodeBlockUtils.getBlockCore(checkPos);
        BlockPos[] selectionEdges = getSelectionEdges();
        
        return BlockUtils.isWithinRegion(checkPos, selectionEdges[0], selectionEdges[1]);
    }
    
    public static void resetSelection() {
        selectionActive = false;
        selectionPos = null;
        selectionState = SelectionState.NULL;
    }
    
    public static BlockPos[] getSelectionEdges() throws CodeFormatException {
        BlockPos[] edges = new BlockPos[2];
        
        switch (selectionState) {
            case CODEBLOCK:
                edges[0] = selectionPos;
                
                if (CodeBlockUtils.getBlockName(selectionPos).hasPistonBrackets) {
                    edges[1] = CodeBlockUtils.getOppositePiston(selectionPos.south());
                } else {
                    edges[1] = selectionPos;
                }
                
                break;
                
            case LOCAL_SCOPE:
                edges = getLocalScopeSelection();
                break;
                
            case CODE_LINE:
                edges = getCodeLineSelection();
        }
        
        return edges;
    }
    
    private static BlockPos[] getLocalScopeSelection() throws CodeFormatException {
        BlockPos[] edges = new BlockPos[2];
        BlockPos checkPos = selectionPos;
        
        while (true) {
            
            checkPos = checkPos.north();
            if (CodeBlockUtils.isCodeBlock(checkPos)) {
                
                //Checks if piston is an opening piston, if so, the end of the local scope has been found.
                if (BlockUtils.getName(checkPos).equals("minecraft:piston") || BlockUtils.getName(checkPos).equals("minecraft:sticky_piston")) {
                    if (BlockUtils.getFacing(checkPos).equals(EnumFacing.SOUTH))
                        break;
                }
                
                checkPos = CodeBlockUtils.getBlockCore(checkPos);
                
            } else if (BlockUtils.getName(checkPos).equals("minecraft:air")) {
                break;
            } else {
                throw new CodeFormatException(checkPos);
            }
        }
        
        edges[0] = checkPos.south();
        
        //Resets the check position.
        checkPos = selectionPos;
    
        while (true) {
            
            checkPos = checkPos.south();
            if (CodeBlockUtils.isCodeBlock(checkPos)) {
            
                //Checks if piston is an opening piston, if so, jump to closing piston.
                if (BlockUtils.getName(checkPos).equals("minecraft:piston") || BlockUtils.getName(checkPos).equals("minecraft:sticky_piston")) {
                    if (BlockUtils.getFacing(checkPos).equals(EnumFacing.SOUTH))
                        checkPos = CodeBlockUtils.getOppositePiston(checkPos);
                }
                
                //If there is air, it means either that it is the air block before a closing piston
                //or it is the end of the code line.
            } else if (BlockUtils.getName(checkPos).equals("minecraft:air")) {
                break;
            } else {
                throw new CodeFormatException(checkPos);
            }
        }
        
        if (BlockUtils.getName(checkPos.north()).equals("minecraft:piston") || BlockUtils.getName(checkPos.north()).equals("minecraft:sticky_piston")) {
            edges[1] = checkPos.north();
        } else {
            edges[1] = CodeBlockUtils.getBlockCore(checkPos.north());
        }
        
        return edges;
    }
    
    private static BlockPos[] getCodeLineSelection() throws CodeFormatException {
        BlockPos[] edges = new BlockPos[2];
        BlockPos checkPos = selectionPos;
        
        while (true) {
            
            checkPos = checkPos.north();
            if (CodeBlockUtils.isCodeBlock(checkPos)) {
                
                if (CodeBlockUtils.getBlockName(CodeBlockUtils.getBlockCore(checkPos)).codeBlockType.equals(CodeBlockType.EVENT)) {
                    checkPos = CodeBlockUtils.getBlockCore(checkPos);
                    break;
                }
                
                if (BlockUtils.getName(checkPos).equals("minecraft:piston") || BlockUtils.getName(checkPos).equals("minecraft:sticky_piston")) {
                    if (BlockUtils.getFacing(checkPos) == EnumFacing.NORTH && CodeBlockUtils.hasOppositePiston(checkPos))
                        checkPos = CodeBlockUtils.getOppositePiston(checkPos);
                }
                
            } else if (BlockUtils.getName(checkPos).equals("minecraft:air")) {
                checkPos = checkPos.south();
                break;
            } else {
                throw new CodeFormatException(checkPos);
            }
        }
        
        edges[0] = checkPos;
        
        //Resets the check position.
        checkPos = selectionPos;
        
        while (true) {
            
            checkPos = checkPos.south();
            if (!CodeBlockUtils.isCodeBlock(checkPos)) {
                
                if (BlockUtils.getName(checkPos).equals("minecraft:air")) {
                    if (!(BlockUtils.getName(checkPos.south()).equals("minecraft:piston") || BlockUtils.getName(checkPos.south()).equals("minecraft:sticky_piston")))
                        break;
                } else {
                    throw new CodeFormatException(checkPos);
                }
            }
        }
        
        if (BlockUtils.getName(checkPos.north()).equals("minecraft:piston") || BlockUtils.getName(checkPos.north()).equals("minecraft:sticky_piston")) {
            edges[1] = checkPos.north();
        } else {
            edges[1] = CodeBlockUtils.getBlockCore(checkPos.north());
        }
        
        return edges;
    }
}
