package dfutils.codetools.copying;

import dfutils.codehandler.utils.CodeBlockName;
import dfutils.codehandler.utils.CodeItemUtils;
import dfutils.utils.ItemUtils;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

class CopyNbtHandler {
    
    static NBTTagList copyData;
    private static NBTTagCompound selectedCodeBlock;
    private static int scopeLevel;
    
    static void initializeCopyData() {
        copyData = new NBTTagList();
        scopeLevel = 0;
    }
    
    static void resetCopyData() {
        copyData = new NBTTagList();
        selectedCodeBlock = null;
        scopeLevel = 0;
    }
    
    //Used for when you exit out of an if block, else block, or a repeat block.
    static void exitCodeScope() {
        if (scopeLevel > 0) {
            scopeLevel--;
            selectedCodeBlock = null;
        }
    }
    
    static void nextCodeBlock(CodeBlockName blockName) {
        
        NBTTagList searchList = copyData;
        if (scopeLevel > 0) {
            for (int searchLevel = 0; searchLevel < scopeLevel; searchLevel++) {
                searchList = searchList.getCompoundTagAt(searchList.tagCount() - 1).getTagList("CodeData", 10);
            }
        }
        
        searchList.appendTag(new NBTTagCompound());
        selectedCodeBlock = searchList.getCompoundTagAt(searchList.tagCount() - 1);
        
        selectedCodeBlock.setTag("Name", new NBTTagString(blockName.name()));
    }
    
    static void addCodeScope() {
        selectedCodeBlock.setTag("CodeData", new NBTTagList());
        scopeLevel++;
    }
    
    static void setCodeFunction(String codeFunction) {
        selectedCodeBlock.setTag("Function", new NBTTagString(codeFunction));
    }
    
    //Mainly used for the select object sub-function.
    static void setCodeSubFunction(String codeSubFunction) {
        selectedCodeBlock.setTag("SubFunction", new NBTTagString(codeSubFunction));
    }
    
    //Used for things like function names or loop delays.
    static void setDynamicCodeFunction(String dynamicFunction) {
        selectedCodeBlock.setTag("DynamicFunction", new NBTTagString(dynamicFunction));
    }
    
    static void setCodeTarget(String codeTarget) {
        selectedCodeBlock.setTag("Target", new NBTTagString(codeTarget));
    }
    
    static void setConditionalNot() {
        selectedCodeBlock.setTag("ConditionalNot", new NBTTagByte((byte) 1));
    }
    
    static void setChestItems(Container chestItems) {
        NBTTagList chestItemNbt = new NBTTagList();
        selectedCodeBlock.setTag("ChestItems", chestItemNbt);
        
        for (int slot = 0; slot < 27; slot++) {
            NBTTagCompound itemNbt = new NBTTagCompound();
            chestItemNbt.appendTag(itemNbt);
            ItemStack itemStack = chestItems.getSlot(slot).getStack();
            
            if (!itemStack.isEmpty()) {
                if (CodeItemUtils.isLocation(itemStack)) {
                    itemNbt = CodeItemUtils.writeLocationNbt(itemStack);
                } else {
                    itemNbt = ItemUtils.toNbt(itemStack);
                }
                
                chestItemNbt.set(slot, itemNbt);
            }
        }
    }
}
