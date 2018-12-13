package dfutils.codesystem.objects.blockarguments;

import diamondcore.utils.ExtArrayList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NumberArgument implements CodeArgument {
	
	public int number;
	
	public NumberArgument(int number) {
		this.number = number;
	}
	
	@Override
	public ExtArrayList<ItemStack> toItems() {
		return new ExtArrayList<>(new ItemStack(Items.SLIME_BALL));
	}
}
