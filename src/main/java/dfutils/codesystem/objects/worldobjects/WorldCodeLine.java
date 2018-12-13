package dfutils.codesystem.objects.worldobjects;

import dfutils.codesystem.objects.CodeBlock;
import dfutils.codesystem.objects.CodeLine;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class WorldCodeLine extends CodeLine {
	
	private BlockPos location;
	
	public WorldCodeLine(BlockPos location) {
		this.location = location;
	}
	
	public WorldCodeLine(ArrayList<CodeBlock> codeBlocks, BlockPos location) {
		super(codeBlocks);
		this.location = location;
	}
	
	public BlockPos getLocation() {
		return location;
	}
}
