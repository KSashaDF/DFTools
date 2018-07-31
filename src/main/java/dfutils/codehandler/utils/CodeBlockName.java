package dfutils.codehandler.utils;

public enum CodeBlockName {
    PLAYER_ACTION(true, false, "Stone", CodeBlockType.REGULAR),
    GAME_ACTION(true, false, "Stone", CodeBlockType.REGULAR),
    ENTITY_ACTION(true, false, "Stone", CodeBlockType.REGULAR),
    SET_VARIABLE(true, false, "Stone", CodeBlockType.REGULAR),
    SELECT_OBJECT(true, false, "Stone", CodeBlockType.REGULAR),
    CALL_FUNCTION(false, false, "Stone", CodeBlockType.REGULAR),
    CONTROL(false, false, "Stone", CodeBlockType.REGULAR),
    
    IF_PLAYER(true, true, "Piston", CodeBlockType.CONDITIONAL),
    IF_GAME(true, true, "Piston", CodeBlockType.CONDITIONAL),
    IF_ENTITY(true, true, "Piston", CodeBlockType.CONDITIONAL),
    IF_VARIABLE(true, true, "Piston", CodeBlockType.CONDITIONAL),
    ELSE(false, false, true, "Piston", CodeBlockType.SPECIAL),
    REPEAT(true, true, "Sticky Piston", CodeBlockType.SPECIAL),

    END_BRACKET(),
    
    PLAYER_EVENT(false, false, "Stone", CodeBlockType.EVENT),
    ENTITY_EVENT(false, false, "Stone", CodeBlockType.EVENT),
    FUNCTION(true, false, "Stone", CodeBlockType.EVENT),
    LOOP(false, false, "Stone", CodeBlockType.EVENT);
    
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
