package diamondcore.eventhandler.network;

import diamondcore.utils.MessageUtils;
import diamondcore.utils.network.PacketUtils;
import diamondcore.utils.playerdata.PlayerStateHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.*;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;

public class InboundDecompressionHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelRead(ChannelHandlerContext handlerContext, Object rawPacket) throws Exception {
        
        if (PlayerStateHandler.isOnDiamondFire) {
            ByteBuf compressedPacket = ((ByteBuf) rawPacket).copy();
    
            PacketBuffer packetBuffer = new PacketBuffer(compressedPacket);
    
            if (packetBuffer.readVarInt() > 2097152) {
    
                EnumConnectionState connectionState = handlerContext.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
                Packet<?> decompressedPacket = PacketUtils.decompressPacket(connectionState, (ByteBuf) rawPacket, EnumPacketDirection.CLIENTBOUND);
                
                if (decompressedPacket instanceof SPacketChunkData) {
                    MessageUtils.warnMessage("WARNING: Found invalid chunk at X: " + ((SPacketChunkData) decompressedPacket).getChunkX() + ", Z: " + ((SPacketChunkData) decompressedPacket).getChunkZ() + "!");
    
                    try {
                        Minecraft.getMinecraft().player.connection.handleChunkData((SPacketChunkData) decompressedPacket);
                    } catch (ThreadQuickExitException ignored) {}
                }
                
                return;
            }
        }
    
        super.channelRead(handlerContext, rawPacket);
    }
}