package dfutils.eventhandler.customevents;

import net.minecraft.network.Packet;

public class SendPacketEvent {
    
    public Packet<?> packet;
    private boolean isCancelled = false;
    
    public SendPacketEvent(Packet<?> packet) {
        this.packet = packet;
    }
    
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
    
    public boolean isCancelled() {
        return isCancelled;
    }
}
