package diamondcore.utils.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.*;

import java.util.ArrayList;

public class PacketUtils {
	
	public static Packet<?> decompressPacket(EnumConnectionState connectionState, ByteBuf byteBuf, EnumPacketDirection packetDirection) {
		try {
			ArrayList<Object> tempPacketList = new ArrayList<>();
			CompressionUtils.decompress(byteBuf, tempPacketList);
			
			ByteBuf encodedPacket = (ByteBuf) tempPacketList.get(0);
			PacketBuffer packetBuffer = new PacketBuffer(encodedPacket);
			int packetId = packetBuffer.readVarInt();
			
			Packet<?> packet = connectionState.getPacket(packetDirection, packetId);
			packet.readPacketData(packetBuffer);
			
			return packet;
		} catch (Exception exception) {
			return null;
		}
	}
	
	public static void compressPacket(EnumConnectionState connectionState, EnumPacketDirection packetDirection, Packet<?> input, ByteBuf output) {
		
		try {
			int packetId = connectionState.getPacketId(packetDirection, input);
			
			PacketBuffer packetbuffer = new PacketBuffer(output);
			packetbuffer.writeVarInt(packetId);
			input.writePacketData(packetbuffer);
			
			CompressionUtils.compress(output, output);
		} catch (Exception ignored) {
		}
	}
}
