package dfutils.eventhandler;

import dfutils.customevents.ClickItemEvent;
import dfutils.network.InboundPacketHandler;
import dfutils.network.OutboundPacketHandler;
import io.netty.channel.Channel;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class SendPacketEvent {
    
    //Adds the inbound and outbound packet handlers.
    static void initializeEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Channel channel = event.getManager().channel();
        channel.pipeline().addBefore("packet_handler", "dfutils_inbound_handler", new InboundPacketHandler());
        channel.pipeline().addBefore("packet_handler", "dfUtils_outbound_handler", new OutboundPacketHandler());
    }
    
    //This method is called whenever a packet is about to be sent.
    public static void onSendPacketEvent(dfutils.customevents.SendPacketEvent event) {
        
        //If the packet is a ClickWindow packet, call the ClickItem event.
        if (event.packet instanceof CPacketClickWindow) {
            ClickItemEvent clickItemEvent = new ClickItemEvent((CPacketClickWindow) event.packet);
            dfutils.eventhandler.ClickItemEvent.onClickItemEvent(clickItemEvent);
            
            event.setCancelled(clickItemEvent.isCancelled());
        }
    }
    
}
