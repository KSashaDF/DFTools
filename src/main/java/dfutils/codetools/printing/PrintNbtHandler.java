package dfutils.codetools.printing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

class PrintNbtHandler {
    boolean reachedCodeEnd;
    NBTTagList printData;
    int[] printPositions;
    NBTTagList[] printScopeStack;
    int printScope;
    NBTTagCompound selectedBlock;
    
    void initializePrintData(NBTTagList codeData) {
        reachedCodeEnd = false;
        printData = codeData;
        printPositions = new int[50];
        printScopeStack = new NBTTagList[50];
        
        printPositions[0] = 0;
        printScopeStack[0] = printData;
        printScope = 0;
        
        selectedBlock = printData.getCompoundTagAt(0);
    }
    
    void nextCodeBlock() {
        
        //If a code block has brackets, it should go inside the brackets instead of continuing on.
        if (selectedBlock.hasKey("CodeData")) {
            //If there is code inside the brackets, read that code, else, continue on.
            if (selectedBlock.getTagList("CodeData", 10).tagCount() > 0) {
    
                printScope++;
                printPositions[printScope] = 0;
                printScopeStack[printScope] = selectedBlock.getTagList("CodeData", 10);
                selectedBlock = printScopeStack[printScope].getCompoundTagAt(0);
                
            } else {
                //If there are more code blocks in the scope, read them, otherwise, exit the scope.
                if (printPositions[printScope] < printScopeStack[printScope].tagCount() - 1) {
    
                    printPositions[printScope]++;
                    selectedBlock = printScopeStack[printScope].getCompoundTagAt(printPositions[printScope]);
                    
                } else {
                    if (printScope > 0) {
    
                        printScope--;
                        printPositions[printScope]++;
                        selectedBlock = printScopeStack[printScope].getCompoundTagAt(printPositions[printScope]);
                        
                    } else {
                        reachedCodeEnd = true;
                    }
                }
            }
        } else {
            //If there are more code blocks in the scope, read them, otherwise, exit the scope.
            if (printPositions[printScope] < printScopeStack[printScope].tagCount() - 1) {
        
                printPositions[printScope]++;
                selectedBlock = printScopeStack[printScope].getCompoundTagAt(printPositions[printScope]);
        
            } else {
                if (printScope > 0) {
            
                    printScope--;
                    printPositions[printScope]++;
                    selectedBlock = printScopeStack[printScope].getCompoundTagAt(printPositions[printScope]);
            
                } else {
                    reachedCodeEnd = true;
                }
            }
        }
    }
    
    boolean shouldExitScope() {
        if (printScope == 0) {
            return false;
        } else {
            if (selectedBlock.hasKey("CodeData") && selectedBlock.getTagList("CodeData", 10).tagCount() != 0) {
                return false;
            } else {
                return printPositions[printScope] >= printScopeStack[printScope].tagCount() - 1;
            }
        }
    }
    
    ItemStack getChestItem(int slot) {
        NBTTagCompound itemNbt = selectedBlock.getTagList("ChestItems", 10).getCompoundTagAt(slot);
        
        if (itemNbt.hasKey("id")) {
            ItemStack itemStack = new ItemStack(
                    Item.getItemById(itemNbt.getInteger("id")),
                    itemNbt.getByte("Count"),
                    itemNbt.getShort("Damage"));
            itemStack.setTagCompound(itemNbt.getCompoundTag("tag"));
            
            return itemStack;
        } else {
            return new ItemStack(Item.getItemById(0));
        }
    }
    
    boolean isCodeChestEmpty(int checkStartPos) {
        for (int slot = checkStartPos; slot < 27; slot++) {
            if (!getChestItem(slot).isEmpty())
                return false;
        }
        
        return true;
    }
}
