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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandImportTemplate {

    private static Minecraft minecraft = Minecraft.getMinecraft();

    static void executeImportTemplate(ICommandSender sender, String[] commandArgs) {

        //Checks if command arguments are valid.
        if (!checkFormat(sender, commandArgs)) {
            return;
        }

        File codeTemplateDirectory = new File(minecraft.mcDataDir, "codetemplates");
        File codeTemplateFile = new File(codeTemplateDirectory, commandArgs[1] + ".dfcode");

        try {
            //Checks if the file exists and if the file isn't empty.
            if (codeTemplateFile.exists() && codeTemplateFile.length() != 0) {

                //Reads codedata.nbt and converts the file into an NBT tag.
                NBTTagCompound rawTemplateData = JsonToNBT.getTagFromJson(IOUtils.toString(new FileInputStream(codeTemplateFile), Charsets.UTF_8));

                //This simply gets the actual code data tag from the compound item data tag that the codedata.nbt file comes in.
                NBTTagList codeTemplate = rawTemplateData.getTagList("CodeData", 10);

                //Creates code template item.
                ItemStack itemStack = new ItemStack(Item.getItemById(120), 1, 0);
                itemStack.setTagCompound(new NBTTagCompound());
                itemStack.getTagCompound().setTag("CodeData", codeTemplate);
                itemStack.getTagCompound().setTag("display", new NBTTagCompound());

                //The following code sets the code template item name.
                CodeBlockName codeLineHeader = CodeBlockUtils.stringToBlock(codeTemplate.getCompoundTagAt(0).getString("Name"));

                if (codeLineHeader.codeBlockType == CodeBlockType.EVENT &&
                        (codeTemplate.getCompoundTagAt(0).hasKey("Function") || codeTemplate.getCompoundTagAt(0).hasKey("DynamicFunction"))) {

                    if (codeLineHeader == CodeBlockName.PLAYER_EVENT || codeLineHeader == CodeBlockName.ENTITY_EVENT) {
                        itemStack.getTagCompound().getCompoundTag("display").
                                setTag("Name", new NBTTagString("§5§l[ §dEvent §5| §d" + codeTemplate.getCompoundTagAt(0).getString("Function") + " §5§l]"));
                    } else if (codeLineHeader == CodeBlockName.LOOP) {
                        itemStack.getTagCompound().getCompoundTag("display").
                                setTag("Name", new NBTTagString("§5§l[ §dEvent §5| §dLoop §5§l]"));
                    } else {
                        itemStack.getTagCompound().getCompoundTag("display").
                                setTag("Name", new NBTTagString("§5§l{ §dFunction §5| §d" + codeTemplate.getCompoundTagAt(0).getString("DynamicFunction") + " §5§l}"));
                    }

                } else {
                    itemStack.getTagCompound().getCompoundTag("display").
                            setTag("Name", new NBTTagString("§5§l( §dCode Template §5§l)"));
                }

                //The following sets the lore for the code template item.
                NBTTagList itemLore = new NBTTagList();
                itemStack.getSubCompound("display").setTag("Lore", itemLore);

                itemLore.appendTag(new NBTTagString("§8§m                          "));
                itemLore.appendTag(new NBTTagString("§7Created By: §c" + rawTemplateData.getString("Author")));
                itemLore.appendTag(new NBTTagString(""));
                itemLore.appendTag(new NBTTagString("§c§nNote§c: §7Place this block down"));
                itemLore.appendTag(new NBTTagString("§7to print the copied code."));
                itemLore.appendTag(new NBTTagString("§7You can also take this block"));
                itemLore.appendTag(new NBTTagString("§7to another node or even"));
                itemLore.appendTag(new NBTTagString("§7give it to another player and"));
                itemLore.appendTag(new NBTTagString("§7it will still work correctly."));
                itemLore.appendTag(new NBTTagString("§8§m                          "));

                //Sends template item to the server.
                ItemUtils.setItemInHotbar(itemStack, true);

                minecraft.player.playSound(SoundEvents.BLOCK_SHULKER_BOX_OPEN, 1.0F, 1.0F);
            } else {
                errorMessage("Unable to find specified code template!");
            }
        } catch (IOException exception) {
            errorMessage("Uh oh! An IO Exception occurred while trying to read the code template file... :/");
        } catch (NBTException exception) {
            errorMessage("Uh oh! Invalid file NBT format.");
        }
    }

    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length != 2) {
            infoMessage("Usage: \n" + new CommandCodeBase().getUsage(sender));
            return false;
        }

        return true;
    }
}
