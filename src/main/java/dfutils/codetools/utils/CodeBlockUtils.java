package dfutils.codetools.utils;

import dfutils.codetools.classification.CodeBlockName;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CodeBlockUtils {
    
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    public static CodeBlockName getBlockName(BlockPos corePos) {
        String blockName = minecraft.world.getBlockState(corePos).getBlock().getLocalizedName();
        
        switch (blockName) {
            case "Cobblestone": return CodeBlockName.PLAYER_ACTION;
            case "Netherrack": return CodeBlockName.GAME_ACTION;
            case "Moss Stone": return CodeBlockName.ENTITY_ACTION;
            case "Block of Iron": return CodeBlockName.SET_VARIABLE;
            case "Purpur Block": return CodeBlockName.SELECT_OBJECT;
            case "Lapis Lazuli Ore": return CodeBlockName.CALL_FUNCTION;
            case "Block of Coal": return CodeBlockName.CONTROL;
    
            case "Wooden Planks": return CodeBlockName.IF_PLAYER;
            case "Red Nether Brick": return CodeBlockName.IF_GAME;
            case "Bricks": return CodeBlockName.IF_ENTITY;
            case "Obsidian": return CodeBlockName.IF_VARIABLE;
            case "End Stone": return CodeBlockName.ELSE;
            case "Prismarine": return CodeBlockName.REPEAT;
    
            case "Block of Diamond": return CodeBlockName.PLAYER_EVENT;
            case "Block of Gold": return CodeBlockName.ENTITY_EVENT;
            case "Lapis Lazuli Block": return CodeBlockName.FUNCTION;
            case "Block of Emerald": return CodeBlockName.LOOP;
            
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
        
        String blockName = minecraft.world.getBlockState(corePos).getBlock().getLocalizedName();
        
        return blockName.equals("Cobblestone") ||
                blockName.equals("Netherrack") ||
                blockName.equals("Moss Stone") ||
                blockName.equals("Block of Iron") ||
                blockName.equals("Purpur Block") ||
                blockName.equals("Lapis Lazuli Ore") ||
                blockName.equals("Block of Coal") ||
                
                blockName.equals("Wooden Planks") ||
                blockName.equals("Red Nether Brick") ||
                blockName.equals("Bricks") ||
                blockName.equals("Obsidian") ||
                blockName.equals("End Stone") ||
                blockName.equals("Prismarine") ||
                
                blockName.equals("Block of Diamond") ||
                blockName.equals("Block of Gold") ||
                blockName.equals("Lapis Lazuli Block") ||
                blockName.equals("Block of Emerald");
    }
    
    public static boolean isCodeBlock(BlockPos blockPos) {
        blockPos = getBlockCore(blockPos);
        CodeBlockName codeBlockName = getBlockName(blockPos);
        
        //Checks if the code block has a valid core.
        if (!isValidCore(blockPos)) return false;
        
        //Checks if the code block has a valid connector.
        if (!minecraft.world.getBlockState(blockPos.south()).getBlock().getLocalizedName().equals(codeBlockName.connectorBlockName)) return false;
        
        //Checks if the code block has a sign.
        if (codeBlockName.hasCodeSign) {
            if (!minecraft.world.getBlockState(blockPos.west()).getBlock().getLocalizedName().equals("Sign"))
                return false;
        }
        
        //Checks if the code block has a chest.
        if (codeBlockName.hasCodeChest) {
            if (!minecraft.world.getBlockState(blockPos.up()).getBlock().getLocalizedName().equals("Chest"))
                return false;
        }
        
        return true;
    }
    
    public static BlockPos getBlockCore(BlockPos blockPos) {
    
        String blockName = minecraft.world.getBlockState(blockPos).getBlock().getLocalizedName();
        
        if (blockName.equals("Stone")) blockPos = blockPos.north();
        if (blockName.equals("Sign")) blockPos = blockPos.east();
        if (blockName.equals("Chest")) blockPos = blockPos.down();
    
        if (blockName.equals("Piston") || blockName.equals("Sticky Piston")) {
            
            EnumFacing pistonDirection = minecraft.world.getBlockState(blockPos).getValue(PropertyDirection.create("facing"));
            
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
        EnumFacing pistonDirection = minecraft.world.getBlockState(pistonPos).getValue(PropertyDirection.create("facing"));
        BlockPos oppositePistonPos = pistonPos;
        
        if (pistonDirection == EnumFacing.SOUTH) {
            oppositePistonPos = getClosingPiston(pistonPos);
        } else if (pistonDirection == EnumFacing.NORTH) {
            oppositePistonPos =  getOpeningPiston(pistonPos);
        }
        
        return !oppositePistonPos.equals(pistonPos);
    }
    
    public static BlockPos getOppositePiston(BlockPos pistonPos) {
        EnumFacing pistonDirection = minecraft.world.getBlockState(pistonPos).getValue(PropertyDirection.create("facing"));
        
        if (pistonDirection == EnumFacing.SOUTH) {
            pistonPos = getClosingPiston(pistonPos);
        } else if (pistonDirection == EnumFacing.NORTH) {
            pistonPos =  getOpeningPiston(pistonPos);
        }
        
        return pistonPos;
    }
    
    private static BlockPos getOpeningPiston(BlockPos pistonPos) {
        int scopeLevel = 0;
        int iterations = 0;
        BlockPos checkPos = pistonPos;
        
        String pistonVariant = minecraft.world.getBlockState(pistonPos).getBlock().getLocalizedName();
        
        do {
            iterations++;
            if (iterations > 300) return pistonPos;
            
            checkPos = checkPos.north();
            
            if (minecraft.world.getBlockState(checkPos).getBlock().getLocalizedName().equals(pistonVariant)) {
                if (minecraft.world.getBlockState(checkPos).getValue(PropertyDirection.create("facing")) == EnumFacing.SOUTH) {
                    scopeLevel--;
                } else if (minecraft.world.getBlockState(checkPos).getValue(PropertyDirection.create("facing")) == EnumFacing.NORTH) {
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
    
        String pistonVariant = minecraft.world.getBlockState(pistonPos).getBlock().getLocalizedName();
    
        do {
            iterations++;
            if (iterations > 300) return pistonPos;
        
            checkPos = checkPos.south();
        
            if (minecraft.world.getBlockState(checkPos).getBlock().getLocalizedName().equals(pistonVariant)) {
                if (minecraft.world.getBlockState(checkPos).getValue(PropertyDirection.create("facing")) == EnumFacing.NORTH) {
                    scopeLevel--;
                } else if (minecraft.world.getBlockState(checkPos).getValue(PropertyDirection.create("facing")) == EnumFacing.SOUTH) {
                    scopeLevel++;
                }
            }
        
        } while (scopeLevel >= 0);
    
        return checkPos;
    }
}
