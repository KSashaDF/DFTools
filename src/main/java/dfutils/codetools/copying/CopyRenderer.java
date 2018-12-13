package dfutils.codetools.copying;

import diamondcore.utils.ColorReference;
import diamondcore.utils.BlockUtils;
import dfutils.codetools.utils.CodeBlockUtils;
import diamondcore.utils.GraphicsUtils;
import diamondcore.utils.chunk.ChunkCache;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraft.util.math.BlockPos;

class CopyRenderer {
	
	static void renderCopySelection(float partialTicks) {
		
		BlockPos renderPos = CopyController.copySelection[0];
		ColorReference drawColor;
		ChunkCache devSpaceCache = PlayerStateHandler.devSpaceCache;
		
		do {
			//Highlights the codeblock currently being copied.
			if (renderPos.equals(CopyController.copyPos)) {
				drawColor = ColorReference.BRIGHT_COPY_CODE;
			} else {
				drawColor = ColorReference.DULL_COPY_CODE;
			}
			
			if (BlockUtils.getName(renderPos, devSpaceCache).equals("minecraft:air"))
				renderPos = renderPos.south();
			
			GraphicsUtils.drawBlock(partialTicks, renderPos, drawColor);
			
			if (BlockUtils.getName(renderPos.west(), devSpaceCache).equals("minecraft:wall_sign"))
				GraphicsUtils.drawSign(partialTicks, renderPos.west(), drawColor);
			
			if (BlockUtils.getName(renderPos.up(), devSpaceCache).equals("minecraft:chest"))
				GraphicsUtils.drawChest(partialTicks, renderPos.up(), drawColor);
			
			renderPos = renderPos.south();
			
		}
		while (BlockUtils.isWithinRegion(CodeBlockUtils.getBlockCore(renderPos), CopyController.copySelection[0], CopyController.copyPos));
	}
}
