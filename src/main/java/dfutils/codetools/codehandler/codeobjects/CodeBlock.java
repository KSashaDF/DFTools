package dfutils.codetools.codehandler.codeobjects;

import dfutils.codetools.codehandler.utils.CodeBlockData;
import dfutils.codetools.codehandler.utils.CodeBlockName;
import dfutils.codetools.codehandler.utils.CodeBlockUtils;
import diamondcore.utils.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class CodeBlock {
	
	private CodeBlockName codeBlockName;
	public String function;
	public String subFunction; //Mainly used for the SELECT_OBJECT block and the REPEAT block.
	public String dynamicFunction; //Used for blocks like the CALL_FUNCTION or the FUNCTION block.
	public String target;
	public boolean conditionalNot = false;
	
	public ItemStack[] chestItems;
	
	public CodeBlock(CodeBlockName codeBlockName) {
		this.codeBlockName = codeBlockName;
	}
	
	public CodeBlock(NBTTagCompound codeBlockNbt) {
		fromNbt(codeBlockNbt);
	}
	
	//Converts this CodeBlock object into NBT.
	public NBTTagCompound toNbt() {
		NBTTagCompound codeBlockNbt = new NBTTagCompound();
		
		codeBlockNbt.setString("Name", codeBlockName.name());
		if (function != null)
			codeBlockNbt.setString("Function", function);
		if (subFunction != null)
			codeBlockNbt.setString("SubFunction", subFunction);
		if (dynamicFunction != null)
			codeBlockNbt.setString("DynamicFunction", dynamicFunction);
		if (target != null)
			codeBlockNbt.setString("Target", target);
		if (conditionalNot)
			codeBlockNbt.setBoolean("ConditionalNot", true);
		
		if (chestItems != null) {
			NBTTagList chestItemNbt = new NBTTagList();
			codeBlockNbt.setTag("ChestItems", chestItemNbt);
			
			for (int i = 0; i < (chestItems.length <= 27 ? chestItems.length : 27); i++) {
				chestItemNbt.appendTag(ItemUtils.toNbt(chestItems[i]));
			}
		}
		
		return codeBlockNbt;
	}
	
	public void fromNbt(NBTTagCompound codeBlockNbt) {
		
		//Resets all the code block's values.
		function = null;
		subFunction = null;
		dynamicFunction = null;
		target = null;
		conditionalNot = false;
		
		chestItems = null;
		
		//Reads the code block NBT data and inputs it.
		codeBlockName = CodeBlockUtils.stringToBlock(codeBlockNbt.getString("Name"));
		
		if (codeBlockNbt.hasKey("Function"))
			function = codeBlockNbt.getString("Function");
		if (codeBlockNbt.hasKey("SubFunction"))
			subFunction = codeBlockNbt.getString("SubFunction");
		if (codeBlockNbt.hasKey("DynamicFunction"))
			dynamicFunction = codeBlockNbt.getString("DynamicFunction");
		if (codeBlockNbt.hasKey("Target"))
			target = codeBlockNbt.getString("Target");
		if (codeBlockNbt.hasKey("ConditionalNot"))
			conditionalNot = codeBlockNbt.getBoolean("ConditionalNot");
		
		if (codeBlockNbt.hasKey("ChestItems")) {
			NBTTagList chestItemNbt = codeBlockNbt.getTagList("ChestItems", 10);
			chestItems = new ItemStack[chestItemNbt.tagCount()];
			
			for (int i = 0; i < chestItemNbt.tagCount(); i++) {
				chestItems[i] = new ItemStack(chestItemNbt.getCompoundTagAt(i));
			}
		}
	}
	
	public CodeBlock copy() {
		return new CodeBlock(toNbt());
	}
	
	public boolean hasSignData() {
		return hasFunction() || hasSubFunction() || hasDynamicFunction() || hasTarget() || !conditionalNot;
	}
	
	public CodeBlockName getCodeBlockName() {
		return codeBlockName;
	}
	
	public boolean hasFunction() {
		return function != null;
	}
	
	public boolean isValidFunction() {
		if (hasFunction()) {
			if (CodeBlockData.codeReferenceData.hasKey(codeBlockName.name())) {
				return CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).hasKey(function);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public NBTTagCompound getFunctionData() {
		if (isValidFunction()) {
			return CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag(function);
		} else {
			return null;
		}
	}
	
	public String[] getFunctionPath() {
		NBTTagList pathNbt = getFunctionData().getTagList("path", 8);
		String[] functionPath = new String[pathNbt.tagCount()];
		
		for (int i = 0; i < pathNbt.tagCount(); i++) {
			functionPath[i] = pathNbt.getStringTagAt(i);
		}
		
		return functionPath;
	}
	
	public boolean hasSubFunction() {
		return subFunction != null;
	}
	
	public boolean isValidSubFunction() {
		if (hasFunction() && hasSubFunction()) {
			if (CodeBlockData.codeReferenceData.hasKey(codeBlockName.name())) {
				if (CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).hasKey(function)) {
					return CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag(function).hasKey(subFunction);
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean isAmbiguousSubFunction() {
		if (isValidSubFunction()) {
			NBTTagCompound subFunctionData = CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag(function).getCompoundTag(subFunction);
			
			if (subFunctionData.hasKey("IsAmbiguous")) {
				return subFunctionData.getBoolean("IsAmbiguous");
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public NBTTagCompound[] getAmbiguousSubFunctions() {
		if (isAmbiguousSubFunction()) {
			NBTTagCompound subFunctionData = CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag(function).getCompoundTag(subFunction);
			
			if (subFunctionData.hasKey("AmbiguousFunctions")) {
				NBTTagList ambiguousFuncList = subFunctionData.getTagList("AmbiguousFunctions", 8);
				NBTTagCompound[] ambiguousFuncData = new NBTTagCompound[ambiguousFuncList.tagCount()];
				
				for (int i = 0; i < ambiguousFuncList.tagCount(); i++) {
					ambiguousFuncData[i] = CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag(function).getCompoundTag(ambiguousFuncList.getStringTagAt(i));
				}
				
				return ambiguousFuncData;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public NBTTagCompound getSubFunctionData() {
		if (!isAmbiguousSubFunction()) {
			return CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag(function).getCompoundTag(subFunction);
		} else {
			return null;
		}
	}
	
	public String[] getSubFunctionPath() {
		NBTTagList subFunctionPathNbt = getSubFunctionData().getTagList("path", 8);
		String[] subFunctionPath = new String[subFunctionPathNbt.tagCount()];
		
		for (int i = 0; i < subFunctionPathNbt.tagCount(); i++) {
			subFunctionPath[i] = subFunctionPathNbt.getStringTagAt(i);
		}
		
		return subFunctionPath;
	}
	
	public boolean hasDynamicFunction() {
		return dynamicFunction != null;
	}
	
	public boolean hasTarget() {
		return target != null;
	}
	
	public boolean isValidTarget() {
		if (hasTarget()) {
			if (CodeBlockData.codeReferenceData.hasKey(codeBlockName.name())) {
				if (CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).hasKey("CodeTarget")) {
					return CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag("CodeTarget").hasKey(target);
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String getTargetPath() {
		if (isValidTarget()) {
			return CodeBlockData.codeReferenceData.getCompoundTag(codeBlockName.name()).getCompoundTag(function).getCompoundTag("CodeTarget").getString(target);
		} else {
			return null;
		}
	}
	
	public boolean hasChestItems() {
		return chestItems != null;
	}
	
	public void appendChestItem(ItemStack chestItem) {
		ItemStack[] newChestItems = new ItemStack[chestItems.length + 1];
		System.arraycopy(chestItems, 0, newChestItems, 0, chestItems.length);
		
		newChestItems[newChestItems.length - 1] = chestItem;
	}
}
