package diamondcore.eventhandler.customevents;

import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ClickItemEvent extends Event {
	
	public ItemStack clickedItem;
	public ClickType clickType;
	public int clickedSlot;
	public int windowId;
	
	public ClickItemEvent(CPacketClickWindow clickItemPacket) {
		clickedItem = clickItemPacket.getClickedItem();
		clickType = clickItemPacket.getClickType();
		clickedSlot = clickItemPacket.getSlotId();
		windowId = clickItemPacket.getWindowId();
	}
}
