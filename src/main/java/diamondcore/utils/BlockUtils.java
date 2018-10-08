package diamondcore.utils;

import diamondcore.utils.chunk.ChunkCache;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

public class BlockUtils {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	public static String getName(BlockPos blockPos) {
		return Block.REGISTRY.getNameForObject(minecraft.world.getBlockState(blockPos).getBlock()).toString();
	}
	
	public static String getName(BlockPos blockPos, ChunkCache chunkCache) {
		return Block.REGISTRY.getNameForObject(chunkCache.getBlockState(blockPos).getBlock()).toString();
	}
	
	public static EnumFacing getFacing(BlockPos blockPos) {
		return minecraft.world.getBlockState(blockPos).getValue(PropertyDirection.create("facing"));
	}
	
	public static EnumFacing getFacing(BlockPos blockPos, ChunkCache chunkCache) {
		return chunkCache.getBlockState(blockPos).getValue(PropertyDirection.create("facing"));
	}
	
	public static String[] getSignText(BlockPos signPos) {
		String[] signText = new String[4];
		TileEntity tileEntity = minecraft.world.getTileEntity(signPos);
		
		if (!(tileEntity instanceof TileEntitySign)) {
			return null;
		}
		
		ITextComponent[] signTextComponents = ((TileEntitySign) tileEntity).signText;
		
		signText[0] = signTextComponents[0].getUnformattedText();
		if (signText[0].equals(""))
			signText[0] = null;
		
		signText[1] = signTextComponents[1].getUnformattedText();
		if (signText[1].equals(""))
			signText[1] = null;
		
		signText[2] = signTextComponents[2].getUnformattedText();
		if (signText[2].equals(""))
			signText[2] = null;
		
		signText[3] = signTextComponents[3].getUnformattedText();
		if (signText[3].equals(""))
			signText[3] = null;
		
		return signText;
	}
	
	public static String[] getSignText(BlockPos signPos, ChunkCache chunkCache) {
		String[] signText = new String[4];
		TileEntity tileEntity = chunkCache.getTileEntity(signPos);
		
		if (!(tileEntity instanceof TileEntitySign)) {
			return null;
		}
		
		ITextComponent[] signTextComponents = ((TileEntitySign) tileEntity).signText;
		
		signText[0] = signTextComponents[0].getUnformattedText();
		if (signText[0].equals(""))
			signText[0] = null;
		
		signText[1] = signTextComponents[1].getUnformattedText();
		if (signText[1].equals(""))
			signText[1] = null;
		
		signText[2] = signTextComponents[2].getUnformattedText();
		if (signText[2].equals(""))
			signText[2] = null;
		
		signText[3] = signTextComponents[3].getUnformattedText();
		if (signText[3].equals(""))
			signText[3] = null;
		
		return signText;
	}
	
	public static boolean isWithinRegion(BlockPos checkLoc, BlockPos corner1, BlockPos corner2) {
		return checkLoc.getX() >= corner1.getX() &&
				checkLoc.getY() >= corner1.getY() &&
				checkLoc.getZ() >= corner1.getZ() &&
				
				checkLoc.getX() <= corner2.getX() &&
				checkLoc.getY() <= corner2.getY() &&
				checkLoc.getZ() <= corner2.getZ();
	}
}
