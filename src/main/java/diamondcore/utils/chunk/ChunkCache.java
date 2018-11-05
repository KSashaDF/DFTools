package diamondcore.utils.chunk;

import diamondcore.utils.MathUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChunkCache {
	
	private Chunk[][] chunkCache;
	
	private int startX;
	private int startZ;
	private int endX;
	private int endZ;
	
	public ChunkCache(int startChunkX, int startChunkZ, int endChunkX, int endChunkZ) {
		
		startChunkX = startChunkX / 16;
		startChunkZ = startChunkZ / 16;
		endChunkX = endChunkX / 16;
		endChunkZ = endChunkZ / 16;
		
		// Registers this cache object with Forge's event bus.
		MinecraftForge.EVENT_BUS.register(this);
		
		startX = Math.min(startChunkX, endChunkX);
		startZ = Math.min(startChunkZ, endChunkZ);
		endX = Math.max(startChunkX, endChunkX);
		endZ = Math.max(startChunkZ, endChunkZ);
		
		if (startX < 0) startX -= 1;
		if (startZ < 0) startZ -= 1;
		if (endX < 0) endX -= 1;
		if (endZ < 0) endZ -= 1;
		
		chunkCache = new Chunk[endChunkX - startChunkX + 1][endChunkZ - startChunkZ + 1];
		
		ChunkProviderClient chunkProvider = Minecraft.getMinecraft().world.getChunkProvider();
		
		// Gets all the currently loaded chunks and caches them.
		for (int x = startX; x <= endX; x++) {
			for (int z = startZ; z <= endZ; z++) {
				Chunk chunk = chunkProvider.getLoadedChunk(x, z);
				
				if (chunk != null) {
					chunkCache[x - startX][z - startZ] = chunk;
				}
			}
		}
	}
	
	/**
	 * <strong>This method should be called when the ChunkCache
	 * is no longer needed and should be deleted!</strong>
	 */
	public void unregister() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void chunkLoadEvent(ChunkEvent.Load event) {
		Chunk chunk = event.getChunk();
		
		// Tests if the chunk is within the assigned cache region.
		if (MathUtils.withinRange(chunk.x, startX, endX) && MathUtils.withinRange(chunk.z, startZ, endZ)) {
			chunkCache[chunk.x - startX][chunk.z - startZ] = chunk;
		}
	}
	
	/**
	 * @param blockPos The location of the queried block.
	 * @return Whether the given block is loaded by one of the cached chunks.
	 */
	public boolean isBlockLoaded(BlockPos blockPos) {
		try {
			return chunkCache[(blockPos.getX() >> 4) - startX][(blockPos.getZ() >> 4) - startZ] != null;
		} catch (ArrayIndexOutOfBoundsException exception) {
			return false;
		}
	}
	
	/**
	 * Checks if the given area is completely cached.
	 *
	 * @param corner1 One corner of the area to check.
	 * @param corner2 Another corner of the area to check.
	 * @return If the given area is cached.
	 */
	public boolean isAreaLoaded(BlockPos corner1, BlockPos corner2) {
		BlockPos[] areaCorners = MathUtils.getCorners(corner1, corner2);
		BlockPos[] allAreaCorners = MathUtils.getAllHorizontalCorners(corner1, corner2);
		
		corner1 = areaCorners[0];
		corner2 = areaCorners[1];
		
		// Checks if the corners of the specified area are loaded.
		for (BlockPos blockPos : allAreaCorners) {
			if (!isBlockLoaded(blockPos)) {
				return false;
			}
		}
		
		// Checks if all the chunks within the (center area of the) given area are loaded.
		for (int x = corner1.getX(); x <= corner2.getX(); x += 16) {
			for (int z = corner1.getZ(); z <= corner2.getZ(); z += 16) {
				if (!isBlockLoaded(new BlockPos(x, 0, z))) {
					return false;
				}
			}
		}
		
		// If no unloaded blocks are found, return true.
		return true;
	}
	
	/**
	 * @return Whether all the chunks within the assigned cache area
	 * are loaded.
	 */
	public boolean areAllChunksLoaded() {
		for (Chunk[] chunks : chunkCache) {
			for (Chunk chunk : chunks) {
				if (chunk == null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public IBlockState getBlockState(BlockPos blockPos) {
		try {
			return chunkCache[(blockPos.getX() >> 4) - startX][(blockPos.getZ() >> 4) - startZ].getBlockState(blockPos);
		} catch (Throwable exception) {
			return Blocks.AIR.getDefaultState();
		}
	}
	
	public TileEntity getTileEntity(BlockPos blockPos) {
		return chunkCache[(blockPos.getX() >> 4) - startX][(blockPos.getZ() >> 4) - startZ].getTileEntity(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
	}
}
