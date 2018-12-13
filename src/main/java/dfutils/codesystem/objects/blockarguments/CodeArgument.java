package dfutils.codesystem.objects.blockarguments;

import diamondcore.utils.ExtArrayList;
import net.minecraft.item.ItemStack;

public interface CodeArgument {
	
	ExtArrayList<ItemStack> toItems();
}
