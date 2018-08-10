# Changelogs &middot; DiamondFire Utilities

## Developer builds

<details>
<summary>V1.1.4-DEVBUILD-3</summary>

## Changelog &middot; 1.1.4-DEVBUILD-2

Click [here]() to download.

### What's new?

- Added `/item <upload|download|list>`

</details>
<details>
<summary>V1.1.4-DEVBUILD-2</summary>

## Changelog &middot; 1.1.4-DEVBUILD-2

Click [here]() to download.

### What's new?

- Added Discord Rich Presence support
- Added `/item <upload|download|list>`
- Added Language files

</details>
<details>
<summary>V1.1.4-DEVBUILD-1</summary>

## Changelog &middot; 1.1.4-DEVBUILD-1

Click [here]() to download.

### What's new?

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
- Added internal utilities within the mod for detecting what the ID of the plot the player currently on is, the owner of
  the plot, the location of the plot, and the size of the plot, etc.. Additionally, added utilities for detecting whether the
  player is inside a support session, and what mode the player is in.
- Added the `/rejoin` command.
- Added a feature that improves code printing so that locations get properly transfered between plots. When copying, a
  location item will be converted into a "location offset", then, when printing, that "location offset" will be applied to
  the plot corner location. This means that locations will no longer break when being transferred between plots.
- Added a configuration option to allow for quick item renaming to work with all items, not only number, text, and variable items.

### What changed?

- Fixed a bug where the pitch and yaw arguments would not work for the `/loc move` command.
- Fixed a crash where a missing opening piston could crash Minecraft.
- Fixed a bug where the code printer could put two items inside a code chest where there as supposed to be one.
  </details>

---

## Releases

<details>
<summary>V1.1.3</summary>

### What's new?

- Added `/loc align` (sets the location to the corner of the block), `/loc center` (centers the location on a block), and `/loc move <x> <y> <z> [pitch] [yaw]` (increments the location by the given coordinates)
- Made it so if you hold two locations (one in your offhand and one in your mainhand) the area between those two locations is highlighted.
- Made it so when you click on the `/itemdata` output, the outputted NBT is copied to your clipboard.
- Made it so "generic." is automatically added to the attribute name when running the `/attribute add` and `/attribute remove` commands.
- Made it so when you shift + left click (while looking at air) when holding either a text item, number item, or variable item the item name will be automatically put into your chatbox so you can easily change the item name.
- Added `/disenchant \<enchantment>` and `/clearenchants`, along with a `/enchant` override.

### What has changed?

- Fixed the bug where you could get kicked from the server for having an invalid hotbar selection.
  </details>

<details>
<summary>V1.1.2</summary>

### What's new?

- Added `/code export <file name>` and `/code import <file name>`
  </details>

<details>
<summary>V1.1.1</summary>

### What's new?

- Held location item highlights the selected block on the plot
- Added `/txt <text>` and `/var <text>`
- Added quick code selection
  - Press <kbd>V</kbd> while looking at a code block to copy the sign data
  - Click on another code block to paste the copied sign data

### What has changed?

- More information added to `/setflags` to prevent confusion.
- `/give` does no longer replace the current held item
- `/give` now uses Minecraft's default command while in singleplayer
- Highlighted pistons are now shown through blocks
- Fixed typo for the DisallowDrops Player Action
- Small changes to command help menu
  </details>

<details>
<summary>V1.0</summary>
This release contains most of the features that were planned when the development of this mod started. The main features include code copy/pasting, a bunch of item manipulation commands, and a couple other shortcuts and utilities.
</details>

---

### DiamondFire Utilities &middot; &copy; 2018 DiamondFire
