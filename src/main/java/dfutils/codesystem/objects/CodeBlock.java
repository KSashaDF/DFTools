package dfutils.codesystem.objects;

import dfutils.codetools.utils.CodeBlockData;
import dfutils.codetools.utils.CodeBlockUtils;
import diamondcore.utils.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * This is a generic CodeBlock object that will store and manage all the necessary data for
 * a given code block.
 */
public class CodeBlock {
	
	private CodeBlockType blockType;
	public String function;
	public String subFunction; // Used for the SELECT_OBJECT block and the REPEAT block.
	public String dynamicFunction; // Used for blocks like the CALL_FUNCTION or the FUNCTION block.
	public String target;
	public boolean conditionalNot = false;
	public ItemStack[] arguments = new ItemStack[27];
	
	public CodeBlock(CodeBlockType blockType) {
		this.blockType = blockType;
	}
	
	public CodeBlock(CodeBlockType blockType, String function, String subFunction, String dynamicFunction, String target, boolean conditionalNot, ItemStack[] arguments) {
		this.blockType = blockType;
		this.function = function;
		this.subFunction = subFunction;
		this.dynamicFunction = dynamicFunction;
		this.target = target;
		this.conditionalNot = conditionalNot;
		this.arguments = arguments;
	}
	
	@Deprecated
	public CodeBlock(NBTTagCompound codeBlockNbt) {
		fromNbt(codeBlockNbt);
	}
	
	/**
	 * @return The NBTTagCompound equivalent of this CodeBlock object.
	 */
	@Deprecated
	public NBTTagCompound toNbt() {
		NBTTagCompound codeBlockNbt = new NBTTagCompound();
		
		codeBlockNbt.setString("Name", blockType.name());
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
		
		if (arguments != null) {
			NBTTagList chestItemNbt = new NBTTagList();
			codeBlockNbt.setTag("ChestItems", chestItemNbt);
			
			for (int i = 0; i < (arguments.length <= 27 ? arguments.length : 27); i++) {
				chestItemNbt.appendTag(ItemUtils.toNbt(arguments[i]));
			}
		}
		
		return codeBlockNbt;
	}
	
	/**
	 * Inputs the data from the given NBTTagCompound into this CodeBlock object.
	 *
	 * @param codeBlockNbt The NBTTagCompound to be read from.
	 */
	@Deprecated
	public void fromNbt(NBTTagCompound codeBlockNbt) {
		
		//Resets all the code block's values.
		function = null;
		subFunction = null;
		dynamicFunction = null;
		target = null;
		conditionalNot = false;
		
		arguments = null;
		
		//Reads the code block NBT data and inputs it.
		blockType = CodeBlockUtils.stringToBlock(codeBlockNbt.getString("Name"));
		
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
			arguments = new ItemStack[chestItemNbt.tagCount()];
			
			for (int i = 0; i < chestItemNbt.tagCount(); i++) {
				arguments[i] = new ItemStack(chestItemNbt.getCompoundTagAt(i));
			}
		}
	}
	
	/**
	 * Copies this CodeBlock object.
	 */
	public CodeBlock copy() {
		return new CodeBlock(blockType, function, subFunction, dynamicFunction, target, conditionalNot, ItemUtils.copyStackArray(arguments));
	}
	
	/**
	 * @return Whether this CodeBlock object contains any function, target, etc. data. Basically whether
	 * the sign data for this CodeBlock needs to be processed.
	 */
	public boolean hasSignData() {
		return hasFunction() || hasSubFunction() || hasDynamicFunction() || hasTarget() || !conditionalNot;
	}
	
	/**
	 * @return The enum type of the given code block.
	 */
	public CodeBlockType getBlockType() {
		return blockType;
	}
	
	/**
	 * @return Whether this CodeBlock object contains a non-null function name.
	 */
	public boolean hasFunction() {
		return function != null;
	}
	
	/**
	 * @return Whether a valid function entry can be found in the codeData.json file for the given function
	 * name.
	 */
	public boolean isValidFunction() {
		if (hasFunction()) {
			if (CodeBlockData.codeData.hasKey(blockType.name())) {
				return CodeBlockData.codeData.getCompoundTag(blockType.name()).hasKey(function);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * @return The NBTTagCompound of the given function from the codeData.json file.
	 */
	public NBTTagCompound getFunctionData() {
		if (isValidFunction()) {
			return CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag(function);
		} else {
			return null;
		}
	}
	
	/**
	 * @return The names of the items in the code block GUI that need to be clicked in order to select the
	 * given function OR sub-function.
	 */
	public String[] getFunctionPath() {
		NBTTagList pathNbt;
		
		if (hasSubFunction()) {
			pathNbt = getSubFunctionData().getTagList("path", 8);
		} else {
			pathNbt = getFunctionData().getTagList("path", 8);
		}
		
		String[] functionPath = new String[pathNbt.tagCount()];
		
		for (int i = 0; i < pathNbt.tagCount(); i++) {
			functionPath[i] = pathNbt.getStringTagAt(i);
		}
		
		return functionPath;
	}
	
	/**
	 * @return Whether this CodeBlock object contains a nun-null sub-function name.
	 */
	public boolean hasSubFunction() {
		return subFunction != null;
	}
	
	/**
	 * @return Whether a valid sub-function entry can be found in the codeData.json file for the given
	 * sub-function name.
	 */
	public boolean isValidSubFunction() {
		if (isValidFunction() && hasSubFunction()) {
			return CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag(function).hasKey(subFunction);
		} else {
			return false;
		}
	}
	
	/**
	 * @return Whether the currently stored sub-function is ambiguous and can possibly be several different types
	 * of code functions.
	 */
	public boolean hasAmbiguousSubFunction() {
		if (isValidSubFunction()) {
			NBTTagCompound subFunctionData = CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag(function).getCompoundTag(subFunction);
			
			if (subFunctionData.hasKey("IsAmbiguous")) {
				return subFunctionData.getBoolean("IsAmbiguous");
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * @return The names of all of the possible outcome sub-functions for a given ambiguous sub-function.
	 */
	public NBTTagCompound[] getAmbiguousSubFunctions() {
		if (hasAmbiguousSubFunction()) {
			NBTTagCompound subFunctionData = CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag(function).getCompoundTag(subFunction);
			
			if (subFunctionData.hasKey("AmbiguousFunctions")) {
				NBTTagList ambiguousFuncList = subFunctionData.getTagList("AmbiguousFunctions", 8);
				NBTTagCompound[] ambiguousFuncData = new NBTTagCompound[ambiguousFuncList.tagCount()];
				
				for (int i = 0; i < ambiguousFuncList.tagCount(); i++) {
					ambiguousFuncData[i] = CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag(function).getCompoundTag(ambiguousFuncList.getStringTagAt(i));
				}
				
				return ambiguousFuncData;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @return The NBTTagCompound of a given sub-function from the codeData.json file.
	 */
	public NBTTagCompound getSubFunctionData() {
		if (!hasAmbiguousSubFunction()) {
			return CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag(function).getCompoundTag(subFunction);
		} else {
			return null;
		}
	}
	
	/**
	 * @return Whether this CodeBlock object contains a non-null dynamic function name.
	 */
	public boolean hasDynamicFunction() {
		return dynamicFunction != null;
	}
	
	/**
	 * @return Whether this CodeBlock object contains a non-null code block target name.
	 */
	public boolean hasTarget() {
		return target != null;
	}
	
	/**
	 * @return Whether a valid target entry can be found in the codeData.json file for the given target
	 * name in the given code block type.
	 */
	public boolean isValidTarget() {
		if (hasTarget()) {
			if (CodeBlockData.codeData.hasKey(blockType.name())) {
				if (CodeBlockData.codeData.getCompoundTag(blockType.name()).hasKey("CodeTarget")) {
					return CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag("CodeTarget").hasKey(target);
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
	
	/**
	 * @return The names of the items that need to be clicked in the code block GUI in order to select the
	 * given code block target.
	 */
	public String getTargetPath() {
		if (isValidTarget()) {
			return CodeBlockData.codeData.getCompoundTag(blockType.name()).getCompoundTag(function).getCompoundTag("CodeTarget").getString(target);
		} else {
			return null;
		}
	}
	
	/**
	 * @return Whether this CodeBlock object contains any chest items.
	 */
	public boolean hasArguments() {
		for (ItemStack argument : arguments) {
			if (argument != null) {
				return true;
			}
		}
		
		return false;
	}
}
