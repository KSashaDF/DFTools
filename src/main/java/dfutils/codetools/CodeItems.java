package dfutils.codetools;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public class CodeItems {
	
	private static final ItemStack PLAYER_ACTION_BLOCK = new ItemStack(Blocks.COBBLESTONE);
	private static final ItemStack GAME_ACTION_BLOCK = new ItemStack(Blocks.NETHERRACK);
	private static final ItemStack ENTITY_ACTION_BLOCK = new ItemStack(Blocks.MOSSY_COBBLESTONE);
	private static final ItemStack SET_VARIABLE_BLOCK = new ItemStack(Blocks.IRON_BLOCK);
	private static final ItemStack SELECT_OBJECT_BLOCK = new ItemStack(Blocks.PURPUR_BLOCK);
	private static final ItemStack CALL_FUNCTION_BLOCK = new ItemStack(Blocks.LAPIS_ORE);
	private static final ItemStack CONTROL_BLOCK = new ItemStack(Blocks.COAL_BLOCK);
	
	private static final ItemStack IF_PLAYER_BLOCK = new ItemStack(Blocks.PLANKS);
	private static final ItemStack IF_GAME_BLOCK = new ItemStack(Blocks.RED_NETHER_BRICK);
	private static final ItemStack IF_ENTITY_BLOCK = new ItemStack(Blocks.BRICK_BLOCK);
	private static final ItemStack IF_VARIABLE_BLOCK = new ItemStack(Blocks.OBSIDIAN);
	private static final ItemStack ELSE_BLOCK = new ItemStack(Blocks.END_STONE);
	private static final ItemStack REPEAT_BLOCK = new ItemStack(Blocks.PRISMARINE);
	
	private static final ItemStack PLAYER_EVENT_BLOCK = new ItemStack(Blocks.DIAMOND_BLOCK);
	private static final ItemStack ENTITY_EVENT_BLOCK = new ItemStack(Blocks.GOLD_BLOCK);
	private static final ItemStack FUNCTION_BLOCK = new ItemStack(Blocks.LAPIS_BLOCK);
	private static final ItemStack LOOP_BLOCK = new ItemStack(Blocks.EMERALD_BLOCK);
	
	
	private static final ItemStack NOT_ARROW_ITEM = new ItemStack(Items.ARROW);
	
	static {
		try {
			PLAYER_ACTION_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to do something related to\",\"§7a player or multiple players.\",\"\",\"§fExamples:\",\"§7§b> §7Send a message to a player\",\"§7§b> §7Damage or heal a player\",\"§7§b> §7Clear a player's inventory\",\"\",\"§fCategory: §cAction\"],Name:\"§aPlayer Action\"}}"));
			GAME_ACTION_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to do something related to\",\"§7the plot and everyone playing it.\",\"\",\"§fExamples:\",\"§7§b> §7Enable the plot's loop block\",\"§7§b> §7Delay the next code block\",\"§7§b> §7Spawn a mob or other entity\",\"\",\"§fCategory: §cAction\"],Name:\"§9Game Action\"}}"));
			ENTITY_ACTION_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to do something related to\",\"§7an entity or multiple entities.\",\"\",\"§fExamples:\",\"§7§b> §7Set the age or size of a mob\",\"§7§b> §7Give a mob a potion effect\",\"§7§b> §7Delete an entity from the plot\",\"\",\"§fCategory: §cAction\"],Name:\"§cEntity Action\"}}"));
			ENTITY_ACTION_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to do something related to\",\"§7an entity or multiple entities.\",\"\",\"§fExamples:\",\"§7§b> §7Set the age or size of a mob\",\"§7§b> §7Give a mob a potion effect\",\"§7§b> §7Delete an entity from the plot\",\"\",\"§fCategory: §cAction\"],Name:\"§cEntity Action\"}}"));
			SET_VARIABLE_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to set the value of a dynamic\",\"§7variable.\",\"\",\"§fExamples:\",\"§7§b> §7Set a variable to a sum of numbers\",\"§7§b> §7Remove all instances of a certain\",\"§7text from a text variable\",\"§7§b> §7Set a variable to a random object\",\"\",\"§fCategory: §cAction\"],Name:\"§eSet Variable\"}}"));
			SELECT_OBJECT_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to change the selection on the\",\"§7current line of code, which will affect\",\"§7the targets of most code blocks.\",\"\",\"§fExamples:\",\"§7§b> §7Select all players on the plot\",\"§7§b> §7Select a player with a certain username\",\"§7§b> §7Select all players on the plot that meet\",\"§7a certain condition\",\"\",\"§fCategory: §cAction\"],Name:\"§dSelect Object\"}}"));
			CALL_FUNCTION_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to call functions declared\",\"§7by a Function block.\",\"\",\"§fCategory: §cAction\"],Name:\"§5Call Function\"}}"));
			CONTROL_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to control the execution of\",\"§7some or all code blocks after it.\",\"\",\"§fExamples:\",\"§7§b> §7Stop the current line of code\",\"§7§b> §7Skip the rest of and return from\",\"§7a function\",\"§7§b> §7Stop a repeating sequence\",\"\",\"§fCategory: §cAction\"],Name:\"§9Control\"}}"));
			
			IF_PLAYER_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if a certain condition related to a\",\"§7player or multiple players is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if a player is swimming\",\"§7§b> §7Check if a player has an item\",\"§7§b> §7Check a player's username\",\"\",\"§fCategory: §dCondition\"],Name:\"§6If Player\"}}"));
			IF_GAME_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside\",\"§7it if a certain condition related\",\"§7to the plot is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if a container has all of\",\"§7a certain set of items\",\"§7§b> §7Check if a sign's text contains\",\"§7a certain text\",\"§7§b> §7Check if a block at a certain\",\"§7location is a certain block\",\"\",\"§fCategory: §dCondition\"],Name:\"§cIf Game\"}}"));
			IF_ENTITY_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if a certain condition related to an\",\"§7entity or multiple entities is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if an entity is a projectile\",\"§7§b> §7Check if an entity is a certain type\",\"§7of mob\",\"§7§b> §7Check the name of an entity\",\"\",\"§fCategory: §dCondition\"],Name:\"§2If Entity\"}}"));
			IF_VARIABLE_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if a certain condition related to the\",\"§7value of a variable is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if a number variable is less\",\"§7than or equal to another number\",\"§7§b> §7Check if a text variable contains\",\"§7a certain text\",\"§7§b> §7Check if a dynamic variable is equal\",\"§7to something\",\"\",\"§fCategory: §dCondition\"],Name:\"§6If Variable\"}}"));
			ELSE_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if the condition directly before it\",\"§7was not met. Else must be placed\",\"§7directly after any Condition block\",\"§7(except for another Else block).\",\"\",\"§fCategory: §dCondition\"],Name:\"§3Else\"}}"));
			REPEAT_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to repeat the code inside it.\",\"\",\"§fExamples:\",\"§7§b> §7Repeat code forever\",\"§7§b> §7Repeat code a certain number\",\"§7of times\",\"§7§b> §7Repeat code until a certain\",\"§7condition is met\",\"\",\"§fCategory: §cAction §a(Looping)\"],Name:\"§aRepeat\"}}"));
			
			PLAYER_EVENT_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute code when a player\",\"§7does something or when something\",\"§7happens to a player.\",\"\",\"§fExamples:\",\"§7§b> §7Detect when a player joins the plot\",\"§7§b> §7Detect when a player takes damage\",\"§7§b> §7Detect when a player right clicks\",\"\",\"§fCategory: §9Event\"],Name:\"§bPlayer Event\"}}"));
			ENTITY_EVENT_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute code when an entity\",\"§7does something or when something\",\"§7happens to an entity.\",\"\",\"§fExamples:\",\"§7§b> §7Detect when a mob takes damage\",\"§7§b> §7Detect when a projectile kills a mob\",\"§7§b> §7Detect when a mob kills another mob\",\"\",\"§fCategory: §9Event\"],Name:\"§eEntity Event\"}}"));
			FUNCTION_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to define a line of code that can be\",\"§7called with a Call Function block.\",\"\",\"§9Additional Info:\",\"§7§b> §7Set the function name by right clicking\",\"§7the code block sign with a text item.\",\"§7§b> §7Set the name color, icon, and lore of the\",\"§7function by putting an item in the code block\",\"§7chest - if the item name is a color code, the\",\"§7function's name color will also change.\",\"§7§b> §7Functions can also be used for continuing\",\"§7lines of code that are out of space.\",\"\",\"§fCategory: §9Event\"],Name:\"§bFunction\"}}"));
			LOOP_BLOCK.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute code repeatedly\",\"§7for every player on the plot with\",\"§7a delay (in ticks) between each\",\"§7execution.\",\"\",\"§9Additional Info:\",\"§7§b> §7Right Click to increase the tick delay,\",\"§7and Sneak + Right Click to decrease it.\",\"§7§b> §7The Loop on your plot is disabled by\",\"§7default, and must be enabled with the\",\"§7'Start Loop' Game Action.\",\"\",\"§fCategory: §9Event §a(Looping)\"],Name:\"§cLoop Block\"}}"));
			
			
			NOT_ARROW_ITEM.setTagCompound(JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Click on a Condition block with this\",\"§7to switch between 'IF' and 'IF NOT'.\"],Name:\"§cThe Not Arrow\"}}"));
		} catch (Exception ignored) {}
	}
	
	public static ItemStack getCodeBlock(String codeBlockName) {
		
		switch (codeBlockName) {
			case "PLAYER_ACTION": return PLAYER_ACTION_BLOCK;
			case "GAME_ACTION": return GAME_ACTION_BLOCK;
			case "ENTITY_ACTION": return ENTITY_ACTION_BLOCK;
			case "SET_VARIABLE": return SET_VARIABLE_BLOCK;
			case "SELECT_OBJECT": return SELECT_OBJECT_BLOCK;
			case "CALL_FUNCTION": return CALL_FUNCTION_BLOCK;
			case "CONTROL": return CONTROL_BLOCK;
			
			case "IF_PLAYER": return IF_PLAYER_BLOCK;
			case "IF_GAME": return IF_GAME_BLOCK;
			case "IF_ENTITY": return IF_ENTITY_BLOCK;
			case "IF_VARIABLE": return IF_VARIABLE_BLOCK;
			case "ELSE": return ELSE_BLOCK;
			case "REPEAT": return REPEAT_BLOCK;
			
			case "PLAYER_EVENT": return PLAYER_EVENT_BLOCK;
			case "ENTITY_EVENT": return ENTITY_EVENT_BLOCK;
			case "FUNCTION": return FUNCTION_BLOCK;
			case "LOOP": return LOOP_BLOCK;
			
			default:
				return ItemStack.EMPTY;
		}
	}
	
	public static ItemStack getNotArrow() {
		return NOT_ARROW_ITEM;
	}
	
	public static ItemStack getTextBook(String text, int stackSize) {
		ItemStack itemStack = new ItemStack(Items.BOOK, stackSize);
		
		try {
			itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"" + text + "\"}}"));
		} catch (NBTException ignored) {}
		
		return itemStack;
	}
	
	public static ItemStack getVarItem(String name, int stackSize, boolean isSaved) {
		ItemStack itemStack = new ItemStack(Items.MAGMA_CREAM, stackSize);
		
		try {
			if (isSaved) {
				itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"" + name + "\",Lore:[\"SAVE\"]}}"));
			} else {
				itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"" + name + "\"}}"));
			}
		} catch (NBTException ignored) {}
		
		return itemStack;
	}
	
	public static ItemStack getNumberSlimeball(int number, int stackSize) {
		ItemStack itemStack = new ItemStack(Items.SLIME_BALL, stackSize);
		
		try {
			itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"§c" + number + "\"}}"));
		} catch (NBTException ignored) {}
		
		return itemStack;
	}
}
