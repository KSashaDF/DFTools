package diamondfireutils.codetools.classification;

public enum CodeFunction {
    GiveItems(CodeBlockName.PLAYER_ACTION),
    SetItems(CodeBlockName.PLAYER_ACTION),
    SetArmor(CodeBlockName.PLAYER_ACTION),
    SetOffHand(CodeBlockName.PLAYER_ACTION),
    RemoveItem(CodeBlockName.PLAYER_ACTION),
    ClearInv(CodeBlockName.PLAYER_ACTION),
    ShowInv(CodeBlockName.PLAYER_ACTION),
    CloseInv(CodeBlockName.PLAYER_ACTION),
    ExpandInv(CodeBlockName.PLAYER_ACTION),
    SaveInv(CodeBlockName.PLAYER_ACTION),
    LoadInv(CodeBlockName.PLAYER_ACTION),
    SetSlot(CodeBlockName.PLAYER_ACTION),
    GiveRngItem(CodeBlockName.PLAYER_ACTION),
    
    
    Join(CodeBlockName.PLAYER_EVENT),
    Quit(CodeBlockName.PLAYER_EVENT);
    
    public final CodeBlockName codeBlockName;
    
    CodeFunction(CodeBlockName blockName) {
        codeBlockName = blockName;
    }
}
