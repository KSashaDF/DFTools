package dfutils.codesystem.objects.blockarguments;

import diamondcore.utils.BlockUtils;
import diamondcore.utils.ExtArrayList;
import net.minecraft.item.ItemStack;

public class BlockArgument implements CodeArgument {
	
	private String blockName;
	private NumberArgument metadata;
	
	public BlockArgument(String blockName) {
		this.blockName = blockName;
	}
	
	public BlockArgument(String blockName, NumberArgument metadata) {
		this.blockName = blockName;
		this.metadata = metadata;
	}
	
	@Override
	public ExtArrayList<ItemStack> toItems() {
		if (metadata == null) {
			return new ExtArrayList<>(new ItemStack(BlockUtils.getBlock(blockName)));
		} else {
			return new ExtArrayList<>(new ItemStack(BlockUtils.getBlock(blockName))).factoryAddAll((metadata.toItems()));
		}
	}
}
