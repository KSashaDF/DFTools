package dfutils.codetools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class CodeItems {
	
	public static ItemStack getCodeBlock(String codeBlockName) {
		
		ItemStack itemStack = new ItemStack(Item.getItemById(0), 1, 0);
		NBTTagCompound itemNbt = new NBTTagCompound();
		
		try {
			switch (codeBlockName) {
				case "PLAYER_ACTION":
					itemStack = new ItemStack(Item.getItemById(4), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to do something related to\",\"§7a player or multiple players.\",\"\",\"§fExamples:\",\"§7§b> §7Send a message to a player\",\"§7§b> §7Damage or heal a player\",\"§7§b> §7Clear a player's inventory\",\"\",\"§fCategory: §cAction\"],Name:\"§aPlayer Action\"}}");
					break;
				
				case "GAME_ACTION":
					itemStack = new ItemStack(Item.getItemById(87), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to do something related to\",\"§7the plot and everyone playing it.\",\"\",\"§fExamples:\",\"§7§b> §7Enable the plot's loop block\",\"§7§b> §7Delay the next code block\",\"§7§b> §7Spawn a mob or other entity\",\"\",\"§fCategory: §cAction\"],Name:\"§9Game Action\"}}");
					break;
				
				case "ENTITY_ACTION":
					itemStack = new ItemStack(Item.getItemById(48), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to do something related to\",\"§7an entity or multiple entities.\",\"\",\"§fExamples:\",\"§7§b> §7Set the age or size of a mob\",\"§7§b> §7Give a mob a potion effect\",\"§7§b> §7Delete an entity from the plot\",\"\",\"§fCategory: §cAction\"],Name:\"§cEntity Action\"}}");
					break;
				
				case "SET_VARIABLE":
					itemStack = new ItemStack(Item.getItemById(42), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to set the value of a dynamic\",\"§7variable.\",\"\",\"§fExamples:\",\"§7§b> §7Set a variable to a sum of numbers\",\"§7§b> §7Remove all instances of a certain\",\"§7text from a text variable\",\"§7§b> §7Set a variable to a random object\",\"\",\"§fCategory: §cAction\"],Name:\"§eSet Variable\"}}");
					break;
				
				case "SELECT_OBJECT":
					itemStack = new ItemStack(Item.getItemById(201), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to change the selection on the\",\"§7current line of code, which will affect\",\"§7the targets of most code blocks.\",\"\",\"§fExamples:\",\"§7§b> §7Select all players on the plot\",\"§7§b> §7Select a player with a certain username\",\"§7§b> §7Select all players on the plot that meet\",\"§7a certain condition\",\"\",\"§fCategory: §cAction\"],Name:\"§dSelect Object\"}}");
					break;
				
				case "CALL_FUNCTION":
					itemStack = new ItemStack(Item.getItemById(21), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to call functions declared\",\"§7by a Function block.\",\"\",\"§fCategory: §cAction\"],Name:\"§5Call Function\"}}");
					break;
				
				case "CONTROL":
					itemStack = new ItemStack(Item.getItemById(173), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to control the execution of\",\"§7some or all code blocks after it.\",\"\",\"§fExamples:\",\"§7§b> §7Stop the current line of code\",\"§7§b> §7Skip the rest of and return from\",\"§7a function\",\"§7§b> §7Stop a repeating sequence\",\"\",\"§fCategory: §cAction\"],Name:\"§9Control\"}}");
					break;
				
				
				case "IF_PLAYER":
					itemStack = new ItemStack(Item.getItemById(5), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if a certain condition related to a\",\"§7player or multiple players is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if a player is swimming\",\"§7§b> §7Check if a player has an item\",\"§7§b> §7Check a player's username\",\"\",\"§fCategory: §dCondition\"],Name:\"§6If Player\"}}");
					break;
				
				case "IF_GAME":
					itemStack = new ItemStack(Item.getItemById(215), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside\",\"§7it if a certain condition related\",\"§7to the plot is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if a container has all of\",\"§7a certain set of items\",\"§7§b> §7Check if a sign's text contains\",\"§7a certain text\",\"§7§b> §7Check if a block at a certain\",\"§7location is a certain block\",\"\",\"§fCategory: §dCondition\"],Name:\"§cIf Game\"}}");
					break;
				
				case "IF_ENTITY":
					itemStack = new ItemStack(Item.getItemById(45), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if a certain condition related to an\",\"§7entity or multiple entities is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if an entity is a projectile\",\"§7§b> §7Check if an entity is a certain type\",\"§7of mob\",\"§7§b> §7Check the name of an entity\",\"\",\"§fCategory: §dCondition\"],Name:\"§2If Entity\"}}");
					break;
				
				case "IF_VARIABLE":
					itemStack = new ItemStack(Item.getItemById(49), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if a certain condition related to the\",\"§7value of a variable is met.\",\"\",\"§fExamples:\",\"§7§b> §7Check if a number variable is less\",\"§7than or equal to another number\",\"§7§b> §7Check if a text variable contains\",\"§7a certain text\",\"§7§b> §7Check if a dynamic variable is equal\",\"§7to something\",\"\",\"§fCategory: §dCondition\"],Name:\"§6If Variable\"}}");
					break;
				
				case "ELSE":
					itemStack = new ItemStack(Item.getItemById(121), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute the code inside it\",\"§7if the condition directly before it\",\"§7was not met. Else must be placed\",\"§7directly after any Condition block\",\"§7(except for another Else block).\",\"\",\"§fCategory: §dCondition\"],Name:\"§3Else\"}}");
					break;
				
				case "REPEAT":
					itemStack = new ItemStack(Item.getItemById(168), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to repeat the code inside it.\",\"\",\"§fExamples:\",\"§7§b> §7Repeat code forever\",\"§7§b> §7Repeat code a certain number\",\"§7of times\",\"§7§b> §7Repeat code until a certain\",\"§7condition is met\",\"\",\"§fCategory: §cAction §a(Looping)\"],Name:\"§aRepeat\"}}");
					break;
				
				
				case "PLAYER_EVENT":
					itemStack = new ItemStack(Item.getItemById(57), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute code when a player\",\"§7does something or when something\",\"§7happens to a player.\",\"\",\"§fExamples:\",\"§7§b> §7Detect when a player joins the plot\",\"§7§b> §7Detect when a player takes damage\",\"§7§b> §7Detect when a player right clicks\",\"\",\"§fCategory: §9Event\"],Name:\"§bPlayer Event\"}}");
					break;
				
				case "ENTITY_EVENT":
					itemStack = new ItemStack(Item.getItemById(41), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute code when an entity\",\"§7does something or when something\",\"§7happens to an entity.\",\"\",\"§fExamples:\",\"§7§b> §7Detect when a mob takes damage\",\"§7§b> §7Detect when a projectile kills a mob\",\"§7§b> §7Detect when a mob kills another mob\",\"\",\"§fCategory: §9Event\"],Name:\"§eEntity Event\"}}");
					break;
				
				case "FUNCTION":
					itemStack = new ItemStack(Item.getItemById(22), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to define a line of code that can be\",\"§7called with a Call Function block.\",\"\",\"§9Additional Info:\",\"§7§b> §7Set the function name by right clicking\",\"§7the code block sign with a text item.\",\"§7§b> §7Set the name color, icon, and lore of the\",\"§7function by putting an item in the code block\",\"§7chest - if the item name is a color code, the\",\"§7function's name color will also change.\",\"§7§b> §7Functions can also be used for continuing\",\"§7lines of code that are out of space.\",\"\",\"§fCategory: §9Event\"],Name:\"§bFunction\"}}");
					break;
				
				case "LOOP":
					itemStack = new ItemStack(Item.getItemById(133), 1, 0);
					itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Used to execute code repeatedly\",\"§7for every player on the plot with\",\"§7a delay (in ticks) between each\",\"§7execution.\",\"\",\"§9Additional Info:\",\"§7§b> §7Right Click to increase the tick delay,\",\"§7and Sneak + Right Click to decrease it.\",\"§7§b> §7The Loop on your plot is disabled by\",\"§7default, and must be enabled with the\",\"§7'Start Loop' Game Action.\",\"\",\"§fCategory: §9Event §a(Looping)\"],Name:\"§cLoop Block\"}}");
			}
		} catch (NBTException exception) {
			System.out.println("Caught NBT exception!");
		}
		
		itemStack.setTagCompound(itemNbt);
		return itemStack;
	}
	
	public static ItemStack getNotArrow() {
		
		ItemStack itemStack = new ItemStack(Item.getItemById(262), 1, 0);
		NBTTagCompound itemNbt;
		
		try {
			itemNbt = JsonToNBT.getTagFromJson("{display:{Lore:[\"§7Click on a Condition block with this\",\"§7to switch between 'IF' and 'IF NOT'.\"],Name:\"§cThe Not Arrow\"}}");
		} catch (NBTException exception) {
			itemNbt = new NBTTagCompound();
		}
		
		itemStack.setTagCompound(itemNbt);
		return itemStack;
	}
	
	public static ItemStack getTextBook(String text, int stackSize) {
		ItemStack itemStack = new ItemStack(Item.getItemById(340), stackSize, 0);
		
		try {
			itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"" + text + "\"}}"));
		} catch (NBTException exception) {
			//Impossible condition! Exception never thrown.
		}
		
		return itemStack;
	}
	
	public static ItemStack getVarItem(String name, int stackSize, boolean isSaved) {
		ItemStack itemStack = new ItemStack(Item.getItemById(378), stackSize, 0);
		
		try {
			if (isSaved) {
				itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"" + name + "\",Lore:[\"SAVE\"]}}"));
			} else {
				itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"" + name + "\"}}"));
			}
		} catch (NBTException exception) {
			//Impossible condition! Exception never thrown.
		}
		
		return itemStack;
	}
	
	public static ItemStack getNumberSlimeball(int number, int stackSize) {
		ItemStack itemStack = new ItemStack(Item.getItemById(341), stackSize, 0);
		
		try {
			itemStack.setTagCompound(JsonToNBT.getTagFromJson("{HideFlags:63,display:{Name:\"§c" + number + "\"}}"));
		} catch (NBTException exception) {
			//Impossible condition! Exception never thrown.
		}
		
		return itemStack;
	}
}
