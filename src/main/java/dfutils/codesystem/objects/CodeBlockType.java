package dfutils.codesystem.objects;

public enum CodeBlockType {
	PLAYER_ACTION(true, false, "minecraft:stone", CodeBlockGroup.REGULAR),
	GAME_ACTION(true, false, "minecraft:stone", CodeBlockGroup.REGULAR),
	ENTITY_ACTION(true, false, "minecraft:stone", CodeBlockGroup.REGULAR),
	SET_VARIABLE(true, false, "minecraft:stone", CodeBlockGroup.REGULAR),
	SELECT_OBJECT(true, false, "minecraft:stone", CodeBlockGroup.REGULAR),
	CALL_FUNCTION(false, false, "minecraft:stone", CodeBlockGroup.REGULAR),
	CONTROL(false, false, "minecraft:stone", CodeBlockGroup.REGULAR),
	
	IF_PLAYER(true, true, "minecraft:piston", CodeBlockGroup.CONDITIONAL),
	IF_GAME(true, true, "minecraft:piston", CodeBlockGroup.CONDITIONAL),
	IF_ENTITY(true, true, "minecraft:piston", CodeBlockGroup.CONDITIONAL),
	IF_VARIABLE(true, true, "minecraft:piston", CodeBlockGroup.CONDITIONAL),
	ELSE(false, false, true, "minecraft:piston", CodeBlockGroup.SPECIAL),
	REPEAT(true, true, "minecraft:sticky_piston", CodeBlockGroup.SPECIAL),
	
	END_BRACKET(),
	
	PLAYER_EVENT(false, false, "minecraft:stone", CodeBlockGroup.EVENT),
	ENTITY_EVENT(false, false, "minecraft:stone", CodeBlockGroup.EVENT),
	FUNCTION(true, false, "minecraft:stone", CodeBlockGroup.EVENT),
	LOOP(false, false, "minecraft:stone", CodeBlockGroup.EVENT);
	
	public final boolean hasChest;
	public final boolean hasSign;
	public final boolean hasBrackets;
	public final String connectorBlockName;
	public final CodeBlockGroup blockGroup;
	
	CodeBlockType() {
		hasChest = false;
		hasSign = false;
		hasBrackets = false;
		connectorBlockName = "";
		blockGroup = CodeBlockGroup.SPECIAL;
	}
	
	CodeBlockType(boolean hasChest, boolean hasBrackets, String connectorName, CodeBlockGroup blockGroup) {
		this.hasChest = hasChest;
		hasSign = true;
		this.hasBrackets = hasBrackets;
		connectorBlockName = connectorName;
		this.blockGroup = blockGroup;
	}
	
	CodeBlockType(boolean hasChest, boolean hasSign, boolean hasBrackets, String connectorName, CodeBlockGroup blockGroup) {
		this.hasChest = hasChest;
		this.hasSign = hasSign;
		this.hasBrackets = hasBrackets;
		connectorBlockName = connectorName;
		this.blockGroup = blockGroup;
	}
}
