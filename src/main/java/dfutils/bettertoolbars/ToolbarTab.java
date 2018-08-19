package dfutils.bettertoolbars;

import dfutils.utils.ItemUtils;
import dfutils.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

class ToolbarTab {

    String tabName;
    String fileName;
    String originalName;
    ItemStack tabIcon;
    private ItemStack[] tabItems;
    private File toolbarItemFile;
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    //Determines whether the toolbar's item data file is currently being written to.
    private boolean isWriting = false;

    //Determines whether the tab's item data needs to be saved.
    private boolean isModified = false;

    //Stores the last time that the toolbar tab file was reloaded, used to determine if
    //the toolbar tab file needs to be reloaded.
    private long lastReloadedTabTime;

    ToolbarTab(NBTTagCompound tabNbt) {

        try {
            tabName = tabNbt.getString("Name");
            fileName = tabNbt.getString("FileName");
            originalName = tabNbt.getString("OriginalName");
            tabIcon = new ItemStack(tabNbt.getCompoundTag("IconItem"));

            toolbarItemFile = new File(ToolbarTabHandler.toolbarDataDir, fileName + ".toolbar");
            lastReloadedTabTime = toolbarItemFile.lastModified() - 1;
        } catch (NullPointerException exception) {
            minecraft.player.closeScreen();
            MessageUtils.errorMessage("Uh oh! Invalid tab data format.");
        }
    }

    void setTabItem(ItemStack tabItem, int itemIndex) {
        isModified = true;
        tabItems[itemIndex] = tabItem;
    }

    void addRow(int parentRow) {
        try {
            isModified = true;

            if (toolbarItemFile.exists() && toolbarItemFile.createNewFile()) {
                tabItems = new ItemStack[(parentRow + 1) * 9];

                for (int i = 0; i < tabItems.length; i++) {
                    tabItems[i] = new ItemStack(Item.getItemById(0));
                }

                saveTab();
            } else {

            }
        } catch (IOException exception) {
            minecraft.player.closeScreen();
            MessageUtils.errorMessage("Uh oh! Encountered an IO Exception while trying to add toolbar row.");
        }
    }

    ItemStack[] getTabItems() {
        if (lastReloadedTabTime < toolbarItemFile.lastModified()) {
            if (!isWriting && toolbarItemFile.exists()) {
                String fileData;
                NBTTagCompound fileNbt;

                //Reads from the tab's item list file and converts the output into ItemStack objects.
                try (InputStream inputStream = Files.newInputStream(toolbarItemFile.toPath())) {

                    fileData = IOUtils.toString(inputStream, Charsets.UTF_8);

                    //Makes sure that the file isn't empty.
                    if (!fileData.equals("")) {
                        fileNbt = JsonToNBT.getTagFromJson(fileData);

                        //Converts the item NBT read from the file into actual ItemStack objects.
                        if (fileNbt.hasKey("Items")) {
                            NBTTagList itemList = fileNbt.getTagList("Items", 10);
                            tabItems = new ItemStack[itemList.tagCount()];

                            for (int i = 0; i < itemList.tagCount(); i++) {
                                tabItems[i] = new ItemStack(itemList.getCompoundTagAt(i));
                            }
                        }

                        lastReloadedTabTime = toolbarItemFile.lastModified();
                    }
                } catch (IOException exception) {
                    minecraft.player.closeScreen();
                    MessageUtils.errorMessage("Uh oh! Encountered an IO Exception while trying to load toolbar item data.");
                } catch (NBTException exception) {
                    minecraft.player.closeScreen();
                    MessageUtils.errorMessage("Uh oh! Invalid toolbar item NBT format.");
                }
            }
        }

        return tabItems.clone();
    }

    //Saves the tab's items to the tab's item file.
    void saveTab() {
        if (isModified) {
            MessageUtils.actionMessage("Saving tab...");
            new Thread(new ToolbarTabWriter()).start();
        }
    }

    class ToolbarTabWriter implements Runnable {

        @Override
        public void run() {

            if (!isWriting) {
                isWriting = true;
                NBTTagCompound tabItemNbt = new NBTTagCompound();
                NBTTagList tabItemNbtList = new NBTTagList();
                tabItemNbt.setTag("Items", tabItemNbtList);

                if (tabItems != null) {
                    //Converts the items inside the tab into NBT.
                    for (ItemStack tabItem : tabItems) {
                        tabItemNbtList.appendTag(ItemUtils.toNbt(tabItem));
                    }
                }

                //Writes to the tab's item list file, also creates a new file if an item data file does not already exist.
                try (OutputStream outputStream = Files.newOutputStream(toolbarItemFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                    MessageUtils.infoMessage(tabItemNbt.toString());
                    IOUtils.write(tabItemNbt.toString(), outputStream, Charsets.UTF_8);

                } catch (IOException exception) {
                    MessageUtils.errorMessage("Uh oh! Encountered an IO Exception while trying to save toolbar item data.");
                    isWriting = false;
                    return;
                }

                lastReloadedTabTime = toolbarItemFile.lastModified();
                isModified = false;
                isWriting = false;
            }
        }
    }
}
