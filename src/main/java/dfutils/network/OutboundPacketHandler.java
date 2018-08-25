package dfutils.network;

import dfutils.eventhandler.SendPacketEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;

public class OutboundPacketHandler extends ChannelOutboundHandlerAdapter {
    
    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
    
        dfutils.customevents.SendPacketEvent packetEvent = new dfutils.customevents.SendPacketEvent((Packet<?>) packet);
        SendPacketEvent.onSendPacketEvent(packetEvent);
    
        if (!packetEvent.isCancelled()) {
            super.write(ctx, packet, promise);
        }
    }
}
