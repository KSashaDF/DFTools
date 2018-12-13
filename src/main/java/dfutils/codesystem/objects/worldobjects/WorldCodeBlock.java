package dfutils.codesystem.objects.worldobjects;

import dfutils.codesystem.objects.CodeBlock;
import dfutils.codesystem.objects.CodeBlockType;
import net.minecraft.util.math.BlockPos;

public class WorldCodeBlock extends CodeBlock {
	
	private BlockPos location;
	
	public WorldCodeBlock(CodeBlockType blockType, BlockPos location) {
		super(blockType);
		this.location = location;
	}
	
	public WorldCodeBlock(CodeBlock codeBlock, BlockPos location) {
		super(codeBlock.getBlockType(), codeBlock.function, codeBlock.subFunction, codeBlock.dynamicFunction, codeBlock.target, codeBlock.conditionalNot, codeBlock.arguments);
		this.location = location;
	}
	
	public BlockPos getLocation() {
		return location;
	}
}
