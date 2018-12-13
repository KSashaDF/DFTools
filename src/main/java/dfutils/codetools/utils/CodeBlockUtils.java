package dfutils.codetools.utils;

import dfutils.codesystem.objects.CodeBlockType;
import diamondcore.utils.BlockUtils;
import diamondcore.utils.chunk.ChunkCache;
import diamondcore.utils.playerdata.PlayerStateHandler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CodeBlockUtils {
	
	public static CodeBlockType getBlockName(BlockPos corePos) {
		String blockName = BlockUtils.getName(corePos, PlayerStateHandler.devSpaceCache);
		
		switch (blockName) {
			case "minecraft:cobblestone": return CodeBlockType.PLAYER_ACTION;
			case "minecraft:netherrack": return CodeBlockType.GAME_ACTION;
			case "minecraft:mossy_cobblestone": return CodeBlockType.ENTITY_ACTION;
			case "minecraft:iron_block": return CodeBlockType.SET_VARIABLE;
			case "minecraft:purpur_block": return CodeBlockType.SELECT_OBJECT;
			case "minecraft:lapis_ore": return CodeBlockType.CALL_FUNCTION;
			case "minecraft:coal_block": return CodeBlockType.CONTROL;
			
			case "minecraft:planks": return CodeBlockType.IF_PLAYER;
			case "minecraft:red_nether_brick": return CodeBlockType.IF_GAME;
			case "minecraft:brick_block": return CodeBlockType.IF_ENTITY;
			case "minecraft:obsidian": return CodeBlockType.IF_VARIABLE;
			case "minecraft:end_stone": return CodeBlockType.ELSE;
			case "minecraft:prismarine": return CodeBlockType.REPEAT;
			
			case "minecraft:diamond_block": return CodeBlockType.PLAYER_EVENT;
			case "minecraft:gold_block": return CodeBlockType.ENTITY_EVENT;
			case "minecraft:lapis_block": return CodeBlockType.FUNCTION;
			case "minecraft:emerald_block": return CodeBlockType.LOOP;
			
			default: return CodeBlockType.PLAYER_ACTION;
		}
	}
	
	public static CodeBlockType stringToBlock(String blockName) {
		switch (blockName) {
			case "PLAYER_ACTION": return CodeBlockType.PLAYER_ACTION;
			case "GAME_ACTION": return CodeBlockType.GAME_ACTION;
			case "ENTITY_ACTION": return CodeBlockType.ENTITY_ACTION;
			case "SET_VARIABLE": return CodeBlockType.SET_VARIABLE;
			case "SELECT_OBJECT": return CodeBlockType.SELECT_OBJECT;
			case "CALL_FUNCTION": return CodeBlockType.CALL_FUNCTION;
			case "CONTROL": return CodeBlockType.CONTROL;
			
			case "IF_PLAYER": return CodeBlockType.IF_PLAYER;
			case "IF_GAME": return CodeBlockType.IF_GAME;
			case "IF_ENTITY": return CodeBlockType.IF_ENTITY;
			case "IF_VARIABLE": return CodeBlockType.IF_VARIABLE;
			case "ELSE": return CodeBlockType.ELSE;
			case "REPEAT": return CodeBlockType.REPEAT;
			
			case "PLAYER_EVENT": return CodeBlockType.PLAYER_EVENT;
			case "ENTITY_EVENT": return CodeBlockType.ENTITY_EVENT;
			case "FUNCTION": return CodeBlockType.FUNCTION;
			case "LOOP": return CodeBlockType.LOOP;
			
			default: return CodeBlockType.PLAYER_ACTION;
		}
	}
	
	public static boolean isInvalidCore(BlockPos corePos) {
		
		String blockName = BlockUtils.getName(corePos, PlayerStateHandler.devSpaceCache);
		
		return !blockName.equals("minecraft:cobblestone") &&
				!blockName.equals("minecraft:netherrack") &&
				!blockName.equals("minecraft:mossy_cobblestone") &&
				!blockName.equals("minecraft:iron_block") &&
				!blockName.equals("minecraft:purpur_block") &&
				!blockName.equals("minecraft:lapis_ore") &&
				!blockName.equals("minecraft:coal_block") &&
				
				!blockName.equals("minecraft:planks") &&
				!blockName.equals("minecraft:red_nether_brick") &&
				!blockName.equals("minecraft:brick_block") &&
				!blockName.equals("minecraft:obsidian") &&
				!blockName.equals("minecraft:end_stone") &&
				!blockName.equals("minecraft:prismarine") &&
				
				!blockName.equals("minecraft:diamond_block") &&
				!blockName.equals("minecraft:gold_block") &&
				!blockName.equals("minecraft:lapis_block") &&
				!blockName.equals("minecraft:emerald_block");
	}
	
	@SuppressWarnings("RedundantIfStatement")
	public static boolean isCodeBlock(BlockPos blockPos) {
		blockPos = getBlockCore(blockPos);
		CodeBlockType codeBlockType = getBlockName(blockPos);
		ChunkCache devSpaceCache = PlayerStateHandler.devSpaceCache;
		
		//Checks if the code block has a valid core.
		if (isInvalidCore(blockPos))
			return false;
		
		//Checks if the code block has a valid connector.
		if (!BlockUtils.getName(blockPos.south(), devSpaceCache).equals(codeBlockType.connectorBlockName))
			return false;
		
		//Checks if the code block has a sign.
		if (codeBlockType.hasSign) {
			if (!BlockUtils.getName(blockPos.west(), devSpaceCache).equals("minecraft:wall_sign"))
				return false;
		}
		
		//Checks if the code block has a chest.
		if (codeBlockType.hasChest) {
			if (!BlockUtils.getName(blockPos.up(), devSpaceCache).equals("minecraft:chest")) {
				return false;
			}
		}
		
		return true;
	}
	
	public static BlockPos getBlockCore(BlockPos blockPos) {
		
		ChunkCache devSpaceCache = PlayerStateHandler.devSpaceCache;
		String blockName = BlockUtils.getName(blockPos, devSpaceCache);
		
		if (blockName.equals("minecraft:stone")) blockPos = blockPos.north();
		if (blockName.equals("minecraft:wall_sign")) blockPos = blockPos.east();
		if (blockName.equals("minecraft:chest")) blockPos = blockPos.down();
		
		if (blockName.equals("minecraft:piston") || blockName.equals("minecraft:sticky_piston")) {
			EnumFacing pistonDirection = BlockUtils.getFacing(blockPos, devSpaceCache);
			
			//Checks if piston is a closing or an opening piston.
			if (pistonDirection == EnumFacing.NORTH) {
				
				if (hasOppositePiston(blockPos)) {
					return getBlockCore(getOppositePiston(blockPos));
				} else {
					return blockPos;
				}
				
			} else {
				blockPos = blockPos.north();
			}
		}
		
		return blockPos;
	}
	
	public static boolean hasOppositePiston(BlockPos pistonPos) {
		try {
			EnumFacing pistonDirection = BlockUtils.getFacing(pistonPos, PlayerStateHandler.devSpaceCache);
			BlockPos oppositePistonPos = pistonPos;
			
			if (pistonDirection == EnumFacing.SOUTH) {
				oppositePistonPos = getClosingPiston(pistonPos);
			} else if (pistonDirection == EnumFacing.NORTH) {
				oppositePistonPos = getOpeningPiston(pistonPos);
			}
			
			return !oppositePistonPos.equals(pistonPos);
			
			//If an Exception is thrown, it means there is probably a missing piston.
		} catch (IllegalArgumentException exception) {
			return false;
		}
	}
	
	public static BlockPos getOppositePiston(BlockPos pistonPos) {
		try {
			EnumFacing pistonDirection = BlockUtils.getFacing(pistonPos, PlayerStateHandler.devSpaceCache);
			
			if (pistonDirection == EnumFacing.SOUTH) {
				pistonPos = getClosingPiston(pistonPos);
			} else if (pistonDirection == EnumFacing.NORTH) {
				pistonPos = getOpeningPiston(pistonPos);
			}
		} catch (IllegalArgumentException exception) {
			//If an Exception is thrown, it means there is probably a missing piston.
		}
		
		return pistonPos;
	}
	
	private static BlockPos getOpeningPiston(BlockPos pistonPos) {
		int scopeLevel = 0;
		int iterations = 0;
		BlockPos checkPos = pistonPos;
		ChunkCache devSpaceCache = PlayerStateHandler.devSpaceCache;
		
		String pistonVariant = BlockUtils.getName(pistonPos, devSpaceCache);
		
		do {
			iterations++;
			if (iterations > 300)
				return pistonPos;
			
			checkPos = checkPos.north();
			
			if (BlockUtils.getName(checkPos, devSpaceCache).equals(pistonVariant)) {
				if (BlockUtils.getFacing(checkPos, devSpaceCache) == EnumFacing.SOUTH) {
					scopeLevel--;
				} else if (BlockUtils.getFacing(checkPos, devSpaceCache) == EnumFacing.NORTH) {
					scopeLevel++;
				}
			}
			
		} while (scopeLevel >= 0);
		
		return checkPos;
	}
	
	private static BlockPos getClosingPiston(BlockPos pistonPos) {
		int scopeLevel = 0;
		int iterations = 0;
		BlockPos checkPos = pistonPos;
		ChunkCache devSpaceCache = PlayerStateHandler.devSpaceCache;
		
		String pistonVariant = BlockUtils.getName(pistonPos, devSpaceCache);
		
		do {
			iterations++;
			if (iterations > 300)
				return pistonPos;
			
			checkPos = checkPos.south();
			
			if (BlockUtils.getName(checkPos, devSpaceCache).equals(pistonVariant)) {
				if (BlockUtils.getFacing(checkPos, devSpaceCache) == EnumFacing.NORTH) {
					scopeLevel--;
				} else if (BlockUtils.getFacing(checkPos, devSpaceCache) == EnumFacing.SOUTH) {
					scopeLevel++;
				}
			}
			
		} while (scopeLevel >= 0);
		
		return checkPos;
	}
}
