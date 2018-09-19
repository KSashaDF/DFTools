package diamondcore.eventhandler;

import diamondcore.eventhandler.customevents.ClickItemEvent;
import diamondcore.eventhandler.network.InboundPacketHandler;
import diamondcore.eventhandler.network.OutboundPacketHandler;
import io.netty.channel.Channel;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber
public class SendPacketEvent {
    
    //Adds the inbound and outbound packet handlers.
    static void initializeEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Channel channel = event.getManager().channel();
        channel.pipeline().addBefore("packet_handler", "dfTools_inbound_handler", new InboundPacketHandler());
        channel.pipeline().addBefore("packet_handler", "dfTools_outbound_handler", new OutboundPacketHandler());
    }
    
    //This method is called whenever a packet is about to be sent.
    @SubscribeEvent
    public static void onSendPacketEvent(diamondcore.eventhandler.customevents.SendPacketEvent event) {
        
        //If the packet is a ClickWindow packet, call the ClickItem event.
        if (event.packet instanceof CPacketClickWindow) {
            ClickItemEvent clickItemEvent = new ClickItemEvent((CPacketClickWindow) event.packet);
            MinecraftForge.EVENT_BUS.post(clickItemEvent);
            
            event.setCanceled(clickItemEvent.isCanceled());
        }
    }
    
}
