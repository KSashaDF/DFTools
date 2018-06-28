package diamondfireutils.codetools.classification;

public enum CodeBlockName {
    PLAYER_ACTION(true, "Stone", CodeBlockType.REGULAR),
    GAME_ACTION(true, "Stone", CodeBlockType.REGULAR),
    ENTITY_ACTION(true, "Stone", CodeBlockType.REGULAR),
    SET_VARIABLE(true, "Stone", CodeBlockType.REGULAR),
    SELECT_OBJECT(true, "Stone", CodeBlockType.REGULAR),
    CALL_FUNCTION(false, "Stone", CodeBlockType.REGULAR),
    CONTROL(false, "Stone", CodeBlockType.REGULAR),
    
    IF_PLAYER(true, "Piston", CodeBlockType.CONDITIONAL),
    IF_GAME(true, "Piston", CodeBlockType.CONDITIONAL),
    IF_ENTITY(true, "Piston", CodeBlockType.CONDITIONAL),
    IF_VARIABLE(true, "Piston", CodeBlockType.CONDITIONAL),
    ELSE(false, false, "Piston", CodeBlockType.SPECIAL),
    REPEAT(false, "Sticky Piston", CodeBlockType.SPECIAL),
    
    PLAYER_EVENT(false, "Stone", CodeBlockType.EVENT),
    ENTITY_EVENT(false, "Stone", CodeBlockType.EVENT),
    FUNCTION(true, "Stone", CodeBlockType.EVENT),
    LOOP(false, "Stone", CodeBlockType.EVENT);
    
    public final boolean hasCodeChest;
    public final boolean hasCodeSign;
    public final String connectorBlockName;
    public final CodeBlockType codeBlockType;
    
    CodeBlockName(boolean hasChest, String connectorName, CodeBlockType blockType) {
        hasCodeChest = hasChest;
        hasCodeSign = true;
        connectorBlockName = connectorName;
        codeBlockType = blockType;
    }
    
    CodeBlockName(boolean hasChest, boolean hasSign, String connectorName, CodeBlockType blockType) {
        hasCodeChest = hasChest;
        hasCodeSign = hasSign;
        connectorBlockName = connectorName;
        codeBlockType = blockType;
    }
}
