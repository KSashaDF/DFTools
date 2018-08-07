package dfutils.codehandler.utils;

import dfutils.utils.BlockUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CodeBlockUtils {

    public static CodeBlockName getBlockName(BlockPos corePos) {
        String blockName = BlockUtils.getName(corePos);
        
        switch (blockName) {
            case "minecraft:cobblestone": return CodeBlockName.PLAYER_ACTION;
            case "minecraft:netherrack": return CodeBlockName.GAME_ACTION;
            case "minecraft:mossy_cobblestone": return CodeBlockName.ENTITY_ACTION;
            case "minecraft:iron_block": return CodeBlockName.SET_VARIABLE;
            case "minecraft:purpur_block": return CodeBlockName.SELECT_OBJECT;
            case "minecraft:lapis_ore": return CodeBlockName.CALL_FUNCTION;
            case "minecraft:coal_block": return CodeBlockName.CONTROL;
    
            case "minecraft:planks": return CodeBlockName.IF_PLAYER;
            case "minecraft:red_nether_brick": return CodeBlockName.IF_GAME;
            case "minecraft:brick_block": return CodeBlockName.IF_ENTITY;
            case "minecraft:obsidian": return CodeBlockName.IF_VARIABLE;
            case "minecraft:end_stone": return CodeBlockName.ELSE;
            case "minecraft:prismarine": return CodeBlockName.REPEAT;
    
            case "minecraft:diamond_block": return CodeBlockName.PLAYER_EVENT;
            case "minecraft:gold_block": return CodeBlockName.ENTITY_EVENT;
            case "minecraft:lapis_block": return CodeBlockName.FUNCTION;
            case "minecraft:emerald_block": return CodeBlockName.LOOP;
            
            default: return CodeBlockName.PLAYER_ACTION;
        }
    }
    
    public static CodeBlockName stringToBlock(String blockName) {
        switch (blockName) {
            case "PLAYER_ACTION": return CodeBlockName.PLAYER_ACTION;
            case "GAME_ACTION": return CodeBlockName.GAME_ACTION;
            case "ENTITY_ACTION": return CodeBlockName.ENTITY_ACTION;
            case "SET_VARIABLE": return CodeBlockName.SET_VARIABLE;
            case "SELECT_OBJECT": return CodeBlockName.SELECT_OBJECT;
            case "CALL_FUNCTION": return CodeBlockName.CALL_FUNCTION;
            case "CONTROL": return CodeBlockName.CONTROL;
        
            case "IF_PLAYER": return CodeBlockName.IF_PLAYER;
            case "IF_GAME": return CodeBlockName.IF_GAME;
            case "IF_ENTITY": return CodeBlockName.IF_ENTITY;
            case "IF_VARIABLE": return CodeBlockName.IF_VARIABLE;
            case "ELSE": return CodeBlockName.ELSE;
            case "REPEAT": return CodeBlockName.REPEAT;
        
            case "PLAYER_EVENT": return CodeBlockName.PLAYER_EVENT;
            case "ENTITY_EVENT": return CodeBlockName.ENTITY_EVENT;
            case "FUNCTION": return CodeBlockName.FUNCTION;
            case "LOOP": return CodeBlockName.LOOP;
        
            default: return CodeBlockName.PLAYER_ACTION;
        }
    }
    
    public static boolean isValidCore(BlockPos corePos) {
        
        String blockName = BlockUtils.getName(corePos);
        
        return blockName.equals("minecraft:cobblestone") ||
                blockName.equals("minecraft:netherrack") ||
                blockName.equals("minecraft:mossy_cobblestone") ||
                blockName.equals("minecraft:iron_block") ||
                blockName.equals("minecraft:purpur_block") ||
                blockName.equals("minecraft:lapis_ore") ||
                blockName.equals("minecraft:coal_block") ||
                
                blockName.equals("minecraft:planks") ||
                blockName.equals("minecraft:red_nether_brick") ||
                blockName.equals("minecraft:brick_block") ||
                blockName.equals("minecraft:obsidian") ||
                blockName.equals("minecraft:end_stone") ||
                blockName.equals("minecraft:prismarine") ||
                
                blockName.equals("minecraft:diamond_block") ||
                blockName.equals("minecraft:gold_block") ||
                blockName.equals("minecraft:lapis_block") ||
                blockName.equals("minecraft:emerald_block");
    }
    
    public static boolean isCodeBlock(BlockPos blockPos) {
        blockPos = getBlockCore(blockPos);
        CodeBlockName codeBlockName = getBlockName(blockPos);
        
        //Checks if the code block has a valid core.
        if (!isValidCore(blockPos)) return false;
        
        //Checks if the code block has a valid connector.
        if (!BlockUtils.getName(blockPos.south()).equals(codeBlockName.connectorBlockName)) return false;
        
        //Checks if the code block has a sign.
        if (codeBlockName.hasCodeSign) {
            if (!BlockUtils.getName(blockPos.west()).equals("minecraft:wall_sign"))
                return false;
        }
        
        //Checks if the code block has a chest.
        if (codeBlockName.hasCodeChest) {
            if (!BlockUtils.getName(blockPos.up()).equals("minecraft:chest"))
                return false;
        }
        
        return true;
    }
    
    public static BlockPos getBlockCore(BlockPos blockPos) {
    
        String blockName = BlockUtils.getName(blockPos);
        
        if (blockName.equals("minecraft:stone")) blockPos = blockPos.north();
        if (blockName.equals("minecraft:wall_sign")) blockPos = blockPos.east();
        if (blockName.equals("minecraft:chest")) blockPos = blockPos.down();
    
        if (blockName.equals("minecraft:piston") || blockName.equals("minecraft:sticky_piston")) {
            
            EnumFacing pistonDirection = BlockUtils.getFacing(blockPos);
            
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
            EnumFacing pistonDirection = BlockUtils.getFacing(pistonPos);
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
            EnumFacing pistonDirection = BlockUtils.getFacing(pistonPos);

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
        
        String pistonVariant = BlockUtils.getName(pistonPos);
        
        do {
            iterations++;
            if (iterations > 300) return pistonPos;
            
            checkPos = checkPos.north();
            
            if (BlockUtils.getName(checkPos).equals(pistonVariant)) {
                if (BlockUtils.getFacing(checkPos) == EnumFacing.SOUTH) {
                    scopeLevel--;
                } else if (BlockUtils.getFacing(checkPos) == EnumFacing.NORTH) {
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
    
        String pistonVariant = BlockUtils.getName(pistonPos);
    
        do {
            iterations++;
            if (iterations > 300) return pistonPos;
        
            checkPos = checkPos.south();
        
            if (BlockUtils.getName(checkPos).equals(pistonVariant)) {
                if (BlockUtils.getFacing(checkPos) == EnumFacing.NORTH) {
                    scopeLevel--;
                } else if (BlockUtils.getFacing(checkPos) == EnumFacing.SOUTH) {
                    scopeLevel++;
                }
            }
        
        } while (scopeLevel >= 0);
    
        return checkPos;
    }
}
