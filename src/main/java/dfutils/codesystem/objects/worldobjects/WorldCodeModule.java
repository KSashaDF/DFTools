package dfutils.codesystem.objects.worldobjects;

import dfutils.codesystem.objects.CodeLine;
import dfutils.codesystem.objects.CodeModule;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class WorldCodeModule extends CodeModule {

	private BlockPos baseLocation;
	
	public WorldCodeModule(BlockPos baseLocation) {
		this.baseLocation = baseLocation;
	}
	
	public WorldCodeModule(ArrayList<CodeLine> codeLines, BlockPos baseLocation) {
		super(codeLines);
		this.baseLocation = baseLocation;
	}
	
	public BlockPos getBaseLocation() {
		return baseLocation;
	}
}
