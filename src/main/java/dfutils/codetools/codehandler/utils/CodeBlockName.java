package dfutils.codetools.codehandler.utils;

public enum CodeBlockName {
	PLAYER_ACTION(true, false, "minecraft:stone", CodeBlockType.REGULAR),
	GAME_ACTION(true, false, "minecraft:stone", CodeBlockType.REGULAR),
	ENTITY_ACTION(true, false, "minecraft:stone", CodeBlockType.REGULAR),
	SET_VARIABLE(true, false, "minecraft:stone", CodeBlockType.REGULAR),
	SELECT_OBJECT(true, false, "minecraft:stone", CodeBlockType.REGULAR),
	CALL_FUNCTION(false, false, "minecraft:stone", CodeBlockType.REGULAR),
	CONTROL(false, false, "minecraft:stone", CodeBlockType.REGULAR),
	
	IF_PLAYER(true, true, "minecraft:piston", CodeBlockType.CONDITIONAL),
	IF_GAME(true, true, "minecraft:piston", CodeBlockType.CONDITIONAL),
	IF_ENTITY(true, true, "minecraft:piston", CodeBlockType.CONDITIONAL),
	IF_VARIABLE(true, true, "minecraft:piston", CodeBlockType.CONDITIONAL),
	ELSE(false, false, true, "minecraft:piston", CodeBlockType.SPECIAL),
	REPEAT(true, true, "minecraft:sticky_piston", CodeBlockType.SPECIAL),
	
	END_BRACKET(),
	
	PLAYER_EVENT(false, false, "minecraft:stone", CodeBlockType.EVENT),
	ENTITY_EVENT(false, false, "minecraft:stone", CodeBlockType.EVENT),
	FUNCTION(true, false, "minecraft:stone", CodeBlockType.EVENT),
	LOOP(false, false, "minecraft:stone", CodeBlockType.EVENT);
	
	public final boolean hasCodeChest;
	public final boolean hasCodeSign;
	public final boolean hasPistonBrackets;
	public final String connectorBlockName;
	public final CodeBlockType codeBlockType;
	
	CodeBlockName() {
		hasCodeChest = false;
		hasCodeSign = false;
		hasPistonBrackets = false;
		connectorBlockName = "";
		codeBlockType = CodeBlockType.SPECIAL;
	}
	
	CodeBlockName(boolean hasChest, boolean hasBrackets, String connectorName, CodeBlockType blockType) {
		hasCodeChest = hasChest;
		hasCodeSign = true;
		hasPistonBrackets = hasBrackets;
		connectorBlockName = connectorName;
		codeBlockType = blockType;
	}
	
	CodeBlockName(boolean hasChest, boolean hasSign, boolean hasBrackets, String connectorName, CodeBlockType blockType) {
		hasCodeChest = hasChest;
		hasCodeSign = hasSign;
		hasPistonBrackets = hasBrackets;
		connectorBlockName = connectorName;
		codeBlockType = blockType;
	}
}
