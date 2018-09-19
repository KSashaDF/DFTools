package itemcontrol.bettertoolbars;

import diamondcore.utils.ItemUtils;
import diamondcore.utils.MessageUtils;
import net.minecraft.client.Minecraft;
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
import java.util.ArrayList;

class ToolbarTab {
    
    String tabName;
    String fileName;
    String newFileName;
    String originalName;
    ItemStack tabIcon;
    private ArrayList<ItemStack> tabItems = new ArrayList<>();
    File toolbarItemFile;
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    //Determines whether the toolbar's item data file is currently being written to.
    private boolean isWriting = false;

    //Determines whether the tab's item data needs to be saved.
    boolean isModified = false;

    //Stores the last time that the toolbar tab file was reloaded, used to determine if
    //the toolbar tab file needs to be reloaded.
    private long lastReloadedTabTime;
    
    ToolbarTab(NBTTagCompound tabNbt) {
        this(tabNbt.getString("Name"), tabNbt.getString("FileName"), tabNbt.getString("OriginalName"), new ItemStack(tabNbt.getCompoundTag("IconItem")));
    }
    
    ToolbarTab(String tabName, String fileName, String originalName, ItemStack tabIcon) {
        this.tabName = tabName;
        this.fileName = fileName;
        this.newFileName = this.fileName;
        this.originalName = originalName;
        this.tabIcon = tabIcon;
    
        toolbarItemFile = new File(ToolbarTabManager.toolbarDataDir, this.fileName + ".toolbar");
        lastReloadedTabTime = toolbarItemFile.lastModified() - 1;
    }

    void setTabItem(int itemIndex, ItemStack tabItem) {
        isModified = true;
        tabItems.set(itemIndex, tabItem);
    }
    
    void addRow(int parentRow) {
        isModified = true;
        
        if (!toolbarItemFile.exists() || tabItems.isEmpty()) {
            
            for (int i = 0; i < (parentRow + 1) * 9; i++) {
                tabItems.add(ItemStack.EMPTY);
            }
        } else {
            for (int i = (parentRow + 1) * 9; i < (parentRow + 2) * 9; i++ ) {
                tabItems.add(i, ItemStack.EMPTY);
            }
        }
    }
    
    void removeRow(int row) {
        isModified = true;
        
        for (int i = 0; i < 9; i++) {
            tabItems.remove(row * 9);
        }
        
        if (tabItems.size() < 45) {
            while (tabItems.size() < 45) {
                tabItems.add(ItemStack.EMPTY);
            }
        }
    }

    ItemStack[] getTabItems() {
        if (lastReloadedTabTime < toolbarItemFile.lastModified()) {
            if (!isWriting) {
                if (toolbarItemFile.exists()) {
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
                                tabItems.clear();
                
                                for (int i = 0; i < itemList.tagCount(); i++) {
                                    tabItems.add(new ItemStack(itemList.getCompoundTagAt(i)));
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
                    
                    if (tabItems == null) {
                        addRow(4);
                    }
                    
                } else {
                    addRow(4);
                }
            } else {
                return null;
            }
        }

        return tabItems.toArray(new ItemStack[0]);
    }

    //Saves the tab's items to the tab's item file.
    void saveTab() {
        if (isModified) {
            
            //Checks if the name of the toolbar item file should be renamed, if so, renames the file.
            if (!fileName.equals(newFileName) && toolbarItemFile.exists()) {
                if (toolbarItemFile.delete()) {
                    toolbarItemFile = new File(ToolbarTabManager.toolbarDataDir, newFileName + ".toolbar");
                    fileName = newFileName;
                }
            }
            
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
