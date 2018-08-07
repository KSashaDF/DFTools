package dfutils.eventhandler;

import dfutils.customevents.ClickItemEvent;
import dfutils.network.NetworkEncoderOverride;
import io.netty.channel.Channel;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class SendPacketEvent {
    
    //Overrides the default packet encoder. (NettyPacketEncoder)
    static void initializeEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Channel channel = event.getManager().channel();
        channel.pipeline().replace(channel.pipeline().get("encoder"), "encoder", new NetworkEncoderOverride(EnumPacketDirection.SERVERBOUND));
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
