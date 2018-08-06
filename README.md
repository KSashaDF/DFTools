# Planned Future Features:
- Add a function auto-set feature. When you set a call function and get a "This function does not exist!" message the next 
function block you place down will be named after that call function. **Note that this should also be toggleable inside a 
configuration file.**
- Add a feature that allows you to shift + left click a function, this will highlight all the corrisponding call functions.
This would also work the opposite way, shift + left click a call function to highlight the corrisponding function.
- Add a `/code search` command, this command will output all the events that are found on the plot to chat. You will be
able to click one of those event output messages to highlight the event. Note this feature will also output how many events 
were found on the plot (simply for interesting stats) along with how many functions and call functions were found on the plot.
- Add a feature that allows you to shift + right click a container in creative mode while looking at air, this will open 
the currently held container item. **Note that if you add or remove items from the container GUI, the mod will store 
the changed items inside the item's blockData NBT tag.**
- Add feature that allows you to toggle whether to automatically switch to the compact dev inventory when entering dev mode. 
**Note that this will be toggleable through a configuration file.**
- Add a feature that allows you to convert NBS files into playsound sequences.
- Implement the tab completion feature for all the commands within the mod.
- Add a GUI for uploading and downloading code templates and packages from within Minecraft.
- Add commands that will allow you to easily edit signs. The new `/sign` command will contain a `/sign copy` command, this 
command will give you a sign item with the copied sign NBT, there will also be a `/sign set <line> <text>` command, 
a `/sign edit <line>` command, this command will put the specified line of sign text into your chat box, similar to the 
quick rename feature or the `/lore edit` command, and a `/sign swap <line 1> <line 2>` command, this command will swap 
the specified sign text lines.

	**Summary:**
	- `/sign copy`
	- `/sign set <line> <text>`
	- `/sign edit <line>`
	- `/sign swap <line 1> <line 2>`
	
- Add the `/lock [text]` command, this command will add the locked NBT tag to the currently held item.
- Add the `/setcolor <color code>` command, this command is used for setting the color of items like leather armor. 
Also add the `/setcolor list` command, this command will simply output a list of a bunch of different color codes.

- Add the `/messages` command, this command will open a GUI with some toggles for various messages inside DiamondFire.

	**Toggleable messages:**
	- Join and leave messages.
	- Support queue notifications. **Note that this toggle will only work inside sessions.**
	- Expert support session chat.
	- Plot CP messages.
	
- Change the location highlight color to most likely a green color. Also make it so if the highlighted location is 
outside the plot borders, the highlighted location's color becomes a red color.
- Add a feature that allows you to shift + left click a code block, what this will do is if the code block has a 
location value inside it, the given location will be highlighted. 

	**Extra notes:**
	- If there are multiple locations within the code block, all of them will be highlighted - except if multiple 
	locations are set to the same block. (this is so you do not get a highlighted block with an extreme opacity)

	- When doing this, the code block will also be highlighted. Click the code block again to turn the highlighting off.

	- If the selected code block is a setblock action, and if the setblock action has two locations inside it, the 
	area between those two locations will be highlighted. **However**, if the selected area is larger than `5,000` blocks 
	the highlighting will turn orange.

	- If the selected code block is an If Variable: Is Within Range code block, the region between the two specified 
	locations will be highlighted.

	- If the selected code block is a copyblocks action, the region that is being copied from will be a bluish color, and 
	the region being copied to will be the default green color. **And** if the selected region is larger than `50,000` blocks 
	the region being copied to will turn orange.

	**Note that this might not be easily possible to display with OpenGL and therefor might not be implemented.**
	- If the selected code block has a range argument, a sphere will be drawn around the center location. **Note that the 
	default range argument should be taken into account if a range is not specified.**
- Add keybinds for the selection stick, and the `/code copy` command. Also make them toggleable via the mod configuration.
- Add a feature that makes it so that if a sign item has sign text NBT, the sign text is displayed within the lore 
of that sign.

# Planned Snapshot Features:

- Add Better Toolbars. When you press the `~` key the Better Toolbar menu will be opened, this menu will be very similar the 
default creative menu. In this menu you will be able to create new toolbar tabs and you will be able to easily set the 
items inside of these toolbar tabs.

	**Controls:**
	- Click the "Add Tab" tab icon to add another toolbar tab.
	- When inside a toolbar tab, click the "Delete Tab" button to delete the toolbar tab.
	- Set an item inside the toolbar tab icon slot to set the icon for that toolbar tab.
	- Set the toolbar tab name by setting the name in the toolbar tab name text box.
	- Open up the full player inventory by clicking the chest tab near the bottom of the toolbar menu.
	
	- Add an item to a toolbar tab by clicking an empty slot with the item.
	- Remove an item from a toolbar tab by shift + right clicking the item.
	- Remove a row from a toolbar tab by clicking the remove row button to the left of the row.
	- Insert a row in a toolbar tab by clicking the add row button to the left of the row.
	
- Add a feature that improves code printing so that locations get properly transfered between plots. When copying, a 
location item will be converted into a "location offset", then, when printing, that "location offset" will be applied to 
the plot corner location. This means that locations will not break when being transferred between plots.
- Add a feature that improves code printing so that if there is not enough space on the current code line for the 
printed code, the printed code will roll over to the next line or codespace.
- Add a StateManager class that will manage the various codetools within the mod and make sure you are not trying to 
copy some code and print some other at code the same time.
- Add a codelayer selection mode to the selection stick, and also most likely remove the local scope selection mode.
- Add a `/code cancel` command. This command will cancel things such as code printing, code copying, piston highlighting, etc..
- Add a configuration option to allow for quick item renaming to work with all items, not only number, text, and variable items.
- Add internal utilities within the mod for detecting what the ID of the plot the player is currently on is, the owner of 
the plot, the location of the plot, and the size of the plot. Additionally, add utilities for detecting whether the player 
is inside a support session, and what mode the player is in.
- Add a feature that detects whether the `/num` number range is under 9 numbers, if so, set the items in the players 
hotbar **but** move any items that are in the players hotbar to the players inventory if there is space. **Note that this 
will be the same as shift clicking the items.**
- Add a `/rejoin` command.

# Snapshot Changelog:

- Added a configuration option to make it so you need to type `/plot varpurge confirm` to varpurge.
- Added a feature to the `/num` command that allows the command to now accept one number argument for a single number item.
- Added the `/lore edit <line>` command, this command will take the specified line of item lore, append `/lore set <line>` to 
the front, and put the text into your chatbox.
- Added support for color codes in the `/give` command.
- Added support for adding a `-s` argument onto the end of the `/var` command to make the variable a save variable.
- Added the `/lore copy` and the `/lore paste [history index]` commands, these commands allow for copying lore from one item 
and pasting it onto another.
- Added a configuration option that allows you to disable having to type `/plot clear confirm` to clear a plot.
- Added an override to the default command cooldown, this makes it so that sending another command while the default command cooldown has not finished does not reset the command cooldown.

- Fixed a bug where the pitch and yaw arguments would not work for the `/loc move` command.
- Fixed a crash where a missing opening piston could crash Minecraft.
- Fixed a bug where the code printer could put two items inside a code chest where there as supposed to be one.
