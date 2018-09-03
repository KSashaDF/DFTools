package dfutils.eventhandler.customevents;

import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;

public class ClickItemEvent {
    
    public ItemStack clickedItem;
    public ClickType clickType;
    public int clickedSlot;
    public int windowId;
    private boolean isCancelled = false;

    public ClickItemEvent(CPacketClickWindow clickItemPacket) {
        clickedItem = clickItemPacket.getClickedItem();
        clickType = clickItemPacket.getClickType();
        clickedSlot = clickItemPacket.getSlotId();
        windowId = clickItemPacket.getWindowId();
    }
    
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
    
    public boolean isCancelled() {
        return isCancelled;
    }
}
