package dfutils.commands.codetools.code;

import dfutils.codetools.utils.CodeBlockName;
import dfutils.codetools.utils.CodeBlockType;
import dfutils.codetools.utils.CodeBlockUtils;
import dfutils.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.*;

import static dfutils.utils.MessageUtils.errorMessage;

class CommandLoadTemplate {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    static void executeLoadTemplate(ICommandSender sender, String[] commandArgs) {

        //Basically checks if the command has any extra arguments, if so, outputs usage.
        if (!checkFormat(sender, commandArgs)) {
            return;
        }

        File codeDataFile = new File(minecraft.mcDataDir, "codedata.nbt");

        try {
            //Checks if the file exists and if the file isn't empty.
            if (codeDataFile.exists() && codeDataFile.length() != 0) {

                //Reads codedata.nbt and converts the file into an NBT tag.
                NBTTagCompound rawCodeData = JsonToNBT.getTagFromJson(IOUtils.toString(new FileInputStream(codeDataFile), Charsets.UTF_8));

                //This simply gets the actual code data tag from the compound item data tag that the codedata.nbt file comes in.
                NBTTagList codeData = rawCodeData.getTagList("CodeData", 10);

                //Creates code template item.
                ItemStack itemStack = new ItemStack(Item.getItemById(130), 1, 0);
                itemStack.setTagCompound(new NBTTagCompound());
                itemStack.getTagCompound().setTag("CodeData", codeData);
                itemStack.getTagCompound().setTag("display", new NBTTagCompound());

                //The following code sets the code template item name.
                CodeBlockName codeLineHeader = CodeBlockUtils.stringToBlock(codeData.getCompoundTagAt(0).getString("Name"));

                if (codeLineHeader.codeBlockType == CodeBlockType.EVENT &&
                        (codeData.getCompoundTagAt(0).hasKey("Function") || codeData.getCompoundTagAt(0).hasKey("DynamicFunction"))) {

                    if (codeLineHeader == CodeBlockName.PLAYER_EVENT || codeLineHeader == CodeBlockName.ENTITY_EVENT) {
                        itemStack.getTagCompound().getCompoundTag("display").
                                setTag("Name", new NBTTagString("§3§l[ §bEvent §3| §b" + codeData.getCompoundTagAt(0).getString("Function") + " §3§l]"));
                    } else if (codeLineHeader == CodeBlockName.LOOP) {
                        itemStack.getTagCompound().getCompoundTag("display").
                                setTag("Name", new NBTTagString("§3§l[ §bEvent §3| §bLoop §3§l]"));
                    } else {
                        itemStack.getTagCompound().getCompoundTag("display").
                                setTag("Name", new NBTTagString("§3§l{ §bFunction §3| §b" + codeData.getCompoundTagAt(0).getString("DynamicFunction") + " §3§l}"));
                    }

                } else {
                    itemStack.getTagCompound().getCompoundTag("display").
                            setTag("Name", new NBTTagString("§3§l( §bCode Template §3§l)"));
                }

                //The following sets the lore for the code template item.
                NBTTagList itemLore = new NBTTagList();
                itemStack.getSubCompound("display").setTag("Lore", itemLore);

                itemLore.appendTag(new NBTTagString("§8§m                          "));
                itemLore.appendTag(new NBTTagString("§7Copied By: §c" + minecraft.player.getName()));
                itemLore.appendTag(new NBTTagString(""));
                itemLore.appendTag(new NBTTagString("§c§nNote§c: §7Place this block down"));
                itemLore.appendTag(new NBTTagString("§7to print the copied code."));
                itemLore.appendTag(new NBTTagString("§7You can also take this block"));
                itemLore.appendTag(new NBTTagString("§7to another node or even"));
                itemLore.appendTag(new NBTTagString("§7give it to another player and"));
                itemLore.appendTag(new NBTTagString("§7it will still work correctly."));
                itemLore.appendTag(new NBTTagString("§8§m                          "));

                //Sends updated item to the server.
                ItemUtils.setItemInHotbar(itemStack, true);

                minecraft.player.playSound(SoundEvents.BLOCK_ENDERCHEST_OPEN, 1.0F, 1.0F);
            } else {
                errorMessage("Hmm... there does not appear to be any code data!");
            }
        } catch (IOException exception) {
            errorMessage("Uh oh! An IO Exception occurred while trying to read the code data file... :/");
        } catch (NBTException exception) {
            errorMessage("Uh oh! Invalid file NBT format.");
        }
    }

    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length != 1) {
            errorMessage("Usage: \n" + new CommandCodeBase().getUsage(sender));
            return false;
        }

        return true;
    }
}
