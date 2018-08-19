package dfutils.network;

import dfutils.eventhandler.SendPacketEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

//NOTE: This class is an override for Minecraft's NettyPacketEncoder class!
//The NettyPacketEncoder is overridden so a SendPacketEvent can be sent to the
//mod's event handler class.

@ParametersAreNonnullByDefault
@ChannelHandler.Sharable //Added Sharable annotation so that Minecraft can reset the PacketEncoder when the player disconnects.
public class NetworkEncoderOverride extends MessageToByteEncoder <Packet<?>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.NETWORK_PACKETS_MARKER);
    private final EnumPacketDirection direction;
    
    public NetworkEncoderOverride(EnumPacketDirection direction) {
        this.direction = direction;
    }
    
    protected void encode(ChannelHandlerContext handlerContext, Packet<?> packet, ByteBuf byteBuf) throws Exception {
    
        // ----- DFUtils code start. -----
        
        dfutils.customevents.SendPacketEvent packetEvent = new dfutils.customevents.SendPacketEvent(packet);
        SendPacketEvent.onSendPacketEvent(packetEvent);
        
        if (packetEvent.isCancelled()) {
            return;
        }
    
        // ----- DFUtils code end. -----
        
        EnumConnectionState connectionState = handlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
        
        if (connectionState == null) {
            throw new RuntimeException("ConnectionProtocol unknown: " + packet.toString());
        } else {
            Integer integer = connectionState.getPacketId(direction, packet);
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", handlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), integer, packet.getClass().getName());
            }
            
            if (integer == null) {
                throw new IOException("Can't serialize unregistered packet");
            } else {
                PacketBuffer packetbuffer = new PacketBuffer(byteBuf);
                packetbuffer.writeVarInt(integer);
                
                try {
                    packet.writePacketData(packetbuffer);
                } catch (Throwable throwable) {
                    throw throwable; // Forge: throw this instead of logging it, to prevent corrupt packets from being sent to the client where they are much harder to debug.
                }
            }
        }
    }
}
