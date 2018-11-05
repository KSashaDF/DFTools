package diamondcore.utils.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;

import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

class CompressionUtils {
	
	private static final byte[] buffer = new byte[8192];
	private static final Inflater inflater = new Inflater();
	private static final Deflater deflater = new Deflater();
	
	static void decompress(ByteBuf input, List<Object> output) {
		
		try {
			PacketBuffer packetbuffer = new PacketBuffer(input);
			int inputBufferSize = packetbuffer.readVarInt();
			
			byte[] inputCopy = new byte[packetbuffer.readableBytes()];
			packetbuffer.readBytes(inputCopy);
			inflater.setInput(inputCopy);
			
			byte[] outputBuffer = new byte[inputBufferSize];
			inflater.inflate(outputBuffer);
			output.add(Unpooled.wrappedBuffer(outputBuffer));
			inflater.reset();
		} catch (DataFormatException ignored) {
		}
	}
	
	static void compress(ByteBuf input, ByteBuf output) {
		
		int bufferSize = input.readableBytes();
		PacketBuffer packetbuffer = new PacketBuffer(output);
		
		byte[] inputCopy = new byte[bufferSize];
		input.readBytes(inputCopy);
		packetbuffer.writeVarInt(inputCopy.length);
		deflater.setInput(inputCopy, 0, bufferSize);
		deflater.finish();
		
		while (!deflater.finished()) {
			int bufferLength = deflater.deflate(buffer);
			packetbuffer.writeBytes(buffer, 0, bufferLength);
		}
		
		deflater.reset();
	}
}
