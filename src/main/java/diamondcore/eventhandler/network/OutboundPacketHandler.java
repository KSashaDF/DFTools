package diamondcore.eventhandler.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;

public class OutboundPacketHandler extends ChannelOutboundHandlerAdapter {
	
	@Override
	public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
		
		diamondcore.eventhandler.customevents.SendPacketEvent packetEvent = new diamondcore.eventhandler.customevents.SendPacketEvent((Packet<?>) packet);
		MinecraftForge.EVENT_BUS.post(packetEvent);
		
		if (!packetEvent.isCanceled()) {
			super.write(ctx, packet, promise);
		}
	}
}
