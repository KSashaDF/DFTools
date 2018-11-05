package diamondcore.utils;

import net.minecraft.util.math.BlockPos;

public class MathUtils {
	
	public static double distance(BlockPos pos1, BlockPos pos2) {
		return Math.hypot(Math.hypot(pos1.getX() - pos2.getX(), pos1.getZ() - pos2.getZ()), pos1.getY() - pos2.getY());
	}
	
	public static BlockPos incrementPosition(BlockPos position1, BlockPos position2) {
		return new BlockPos(position1.getX() + position2.getX(), position1.getY() + position2.getY(), position1.getZ() + position2.getZ());
	}
	
	public static boolean withinRange(int number, int min, int max) {
		
		// If min is larger than max, swap the values.
		if (min > max) {
			int tempMin = min;
			min = max;
			max = tempMin;
		}
		
		return number >= min && number <= max;
	}
	
	public static double clamp(double number, double min, double max) {
		
		// If min is larger than max, swap the values.
		if (min > max) {
			double tempMin = min;
			min = max;
			max = tempMin;
		}
		
		if (number < min)
			number = min;
		
		if (number > max)
			number = max;
		
		return number;
	}
	
	public static BlockPos[] getCorners(BlockPos corner1, BlockPos corner2) {
		BlockPos[] cornerPositions = new BlockPos[2];
		
		cornerPositions[0] = new BlockPos(
				corner1.getX() < corner2.getX() ? corner1.getX() : corner2.getX(),
				corner1.getY() < corner2.getY() ? corner1.getY() : corner2.getY(),
				corner1.getZ() < corner2.getZ() ? corner1.getZ() : corner2.getZ());
		
		cornerPositions[1] = new BlockPos(
				corner1.getX() > corner2.getX() ? corner1.getX() : corner2.getX(),
				corner1.getY() > corner2.getY() ? corner1.getY() : corner2.getY(),
				corner1.getZ() > corner2.getZ() ? corner1.getZ() : corner2.getZ());
		
		return cornerPositions;
		
	}
	
	public static BlockPos[] getAllHorizontalCorners(BlockPos corner1, BlockPos corner2) {
		BlockPos[] cornerPositions = new BlockPos[4];
		
		cornerPositions[0] = new BlockPos(
				corner1.getX() < corner2.getX() ? corner1.getX() : corner2.getX(), 0,
				corner1.getZ() < corner2.getZ() ? corner1.getZ() : corner2.getZ());
		
		cornerPositions[1] = new BlockPos(
				corner1.getX() > corner2.getX() ? corner1.getX() : corner2.getX(), 0,
				corner1.getZ() > corner2.getZ() ? corner1.getZ() : corner2.getZ());
		
		cornerPositions[0] = new BlockPos(
				corner1.getX() > corner2.getX() ? corner1.getX() : corner2.getX(), 0,
				corner1.getZ() < corner2.getZ() ? corner1.getZ() : corner2.getZ());
		
		cornerPositions[1] = new BlockPos(
				corner1.getX() < corner2.getX() ? corner1.getX() : corner2.getX(), 0,
				corner1.getZ() > corner2.getZ() ? corner1.getZ() : corner2.getZ());
		
		return cornerPositions;
	}
	
	/**
	 * Also known as linearInterpolate, or linear interpolation.
	 */
	public static float lerp(float number, float oldRangeMin, float oldRangeMax, float newRangeMin, float newRangeMax) {
		
		number = (number - oldRangeMin) / (oldRangeMax - oldRangeMin);
		number = (newRangeMax - newRangeMin) * number;
		
		return number + newRangeMin;
	}
	
	public static int ceilingDivide(int number, int divisor) {
		if (number % divisor != 0) {
			number += divisor;
		}
		
		return number / divisor;
	}
}
