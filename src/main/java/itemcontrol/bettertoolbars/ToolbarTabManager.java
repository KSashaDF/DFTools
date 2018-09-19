package itemcontrol.bettertoolbars;

import diamondcore.utils.ItemUtils;
import diamondcore.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class ToolbarTabManager {

    private static ArrayList<ToolbarTab> toolbarTabs = new ArrayList<>();
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    static File toolbarDataDir = new File(minecraft.gameDir, "toolbars");
    private static File toolbarDataFile = new File(minecraft.gameDir, "toolbars/ToolbarTabs.nbt");

    //Stores the last time that the toolbar tab data file was reloaded, used to determine if
    //the toolbar tab data file needs to be reloaded.
    private static long lastReloadedDataTime = toolbarDataFile.lastModified() - 1;

    private static void createToolbarTab() {
    
        toolbarTabs.add(new ToolbarTab("New Toolbar Tab", "New_Toolbar_Tab", "New Toolbar Tab", ItemStack.EMPTY));
        renameToolbarTab("New Toolbar Tab", toolbarTabs.size() - 1);

        //Creates the default 5 toolbar rows.
        toolbarTabs.get(toolbarTabs.size() - 1).addRow(4);
    }
    
    public static void deleteToolbarTab() {
        
        //If the toolbar data file exists, delete it. Also removes the toolbar tab from the tab list.
        if (!toolbarTabs.get(StateHandler.selectedTabIndex).toolbarItemFile.exists() || toolbarTabs.get(StateHandler.selectedTabIndex).toolbarItemFile.delete()) {
            toolbarTabs.remove(StateHandler.selectedTabIndex);
        }
    }

    public static void renameToolbarTab(String newName) {
        if (StateHandler.selectedTabIndex == toolbarTabs.size()) {
            createToolbarTab();
        }
        
        renameToolbarTab(newName, StateHandler.selectedTabIndex);
    }
    
    //NOTE: If you are wondering what tabIndex is for, it is so the the various name checks
    //ignore the tab actually being renamed.
    private static void renameToolbarTab(String newName, int tabIndex) {
        String modifiedName = newName;
        ToolbarTab toolbarTab = toolbarTabs.get(tabIndex);
        toolbarTab.originalName = newName;

        //Checks if the name is already taken, if so, find a valid number suffix for the name.
        for (int i = 0; i < toolbarTabs.size(); i++) {
            if (i != tabIndex && toolbarTabs.get(i).originalName.equals(newName)) {
                modifiedName = recursiveTabNameFinder(newName, 1, tabIndex);
                break;
            }
        }

        toolbarTab.tabName = modifiedName;

        //Replaces illegal file name characters and also makes sure the file name is unique.
        if (modifiedName.equals("")) {
            modifiedName = "BLANK";
        }
        
        modifiedName = modifiedName.replace(" ", "_");
        modifiedName = modifiedName.replace("/", ":");
        for (int i = 0; i < toolbarTabs.size(); i++) {
            if (i != tabIndex && toolbarTabs.get(i).fileName.equals(modifiedName)) {
                modifiedName = recursiveFileNameFinder(newName, 1, tabIndex);
                break;
            }
        }

        toolbarTab.newFileName = modifiedName;
        toolbarTab.isModified = true;
    }

    private static String recursiveTabNameFinder(String tabName, int tabNameSuffix, int tabIndex) {
        String modifiedTabName = tabName + " " + tabNameSuffix;

        //Checks if the tab name is already taken, if so, tries the next number suffix.
        for (int i = 0; i < toolbarTabs.size(); i++) {
            if (i != tabIndex && toolbarTabs.get(i).tabName.equals(modifiedTabName)) {
                return recursiveTabNameFinder(tabName, tabNameSuffix + 1, tabIndex);
            }
        }

        return modifiedTabName;
    }

    private static String recursiveFileNameFinder(String fileName, int fileNameSuffix, int tabIndex) {
        String modifiedFileName = fileName + "_" + fileNameSuffix;

        //Checks if the file name is already taken, if so, tries the next number suffix.
        for (int i = 0; i < toolbarTabs.size(); i++) {
            if (i != tabIndex && toolbarTabs.get(i).fileName.equals(modifiedFileName)) {
                return recursiveFileNameFinder(fileName, fileNameSuffix + 1, tabIndex);
            }
        }

        return modifiedFileName;
    }

    public static void loadToolbarTabs() throws IOException {

        //If the toolbar data file does not exist, simply don't try to load any tabs.
        if (!toolbarDataFile.exists()) {
            lastReloadedDataTime = toolbarDataFile.lastModified();
            return;
        }
    
        if (toolbarDataDir.isDirectory() && lastReloadedDataTime < toolbarDataFile.lastModified()) {
            NBTTagCompound toolbarTabNbt;

            //Reads the toolbar data from the ToolbarTabs.nbt file.
            try (InputStream inputStream = Files.newInputStream(toolbarDataFile.toPath())) {
                toolbarTabNbt = JsonToNBT.getTagFromJson(IOUtils.toString(inputStream, Charsets.UTF_8));
            } catch (NBTException exception) {
                minecraft.player.closeScreen();
                MessageUtils.errorMessage("Uh oh! Invalid toolbar data NBT format.");
                return;
            }

            //Parses the toolbar data NBT into toolbar tab objects.
            NBTTagList toolbarTabNbtList = toolbarTabNbt.getTagList("ToolbarTabs", 10);
            toolbarTabs.clear();
            for (int i = 0; i < toolbarTabNbtList.tagCount(); i++) {
                toolbarTabs.add(new ToolbarTab(toolbarTabNbtList.getCompoundTagAt(i)));
            }
    
            lastReloadedDataTime = toolbarDataFile.lastModified();
        }
    }

    //This method simply saves all the toolbar tab data.
    //Called when the toolbar GUI closes.
    public static void saveTabs() {
    
        //If the toolbar data folder does not exist, create one.
        if (!toolbarDataDir.isDirectory() && !toolbarDataDir.mkdirs()) {
            MessageUtils.errorMessage("Uh oh! Unable to save toolbar data.");
            return;
        }
        
        //Creates the base toolbar tab NBT.
        NBTTagCompound toolbarTabNbt = new NBTTagCompound();
        NBTTagList toolbarTabNbtList = new NBTTagList();
        toolbarTabNbt.setTag("ToolbarTabs", toolbarTabNbtList);

        //Iterates through all the toolbar tabs and calls the toolbar tab's save method,
        //also constructs the toolbar tab data NBT object.
        for (ToolbarTab toolbarTab : toolbarTabs) {
            toolbarTab.saveTab();

            NBTTagCompound tabNbt = new NBTTagCompound();
            tabNbt.setTag("Name", new NBTTagString(toolbarTab.tabName));
            tabNbt.setTag("FileName", new NBTTagString(toolbarTab.fileName));
            tabNbt.setTag("OriginalName", new NBTTagString(toolbarTab.originalName));
            tabNbt.setTag("IconItem", ItemUtils.toNbt(toolbarTab.tabIcon));

            toolbarTabNbtList.appendTag(tabNbt);
        }

        //Writes the toolbar NBT data to the ToolbarTabs.nbt file.
        try (OutputStream outputStream = Files.newOutputStream(toolbarDataFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            IOUtils.write(toolbarTabNbt.toString(), outputStream, Charsets.UTF_8);

        } catch (IOException exception) {
            MessageUtils.errorMessage("Uh oh! Encountered an IO Exception while trying to save toolbar data.");
        }
        
        lastReloadedDataTime = toolbarDataFile.lastModified();
    }
    
    public static void setTabIcon(ItemStack tabIcon) {
        if (StateHandler.selectedTabIndex == toolbarTabs.size()) {
            createToolbarTab();
        }
    
        toolbarTabs.get(StateHandler.selectedTabIndex).tabIcon = tabIcon;
    }
    
    public static void setTabItem(int itemIndex, ItemStack tabItem) {
        if (StateHandler.selectedTabIndex == toolbarTabs.size()) {
            createToolbarTab();
        }
        
        toolbarTabs.get(StateHandler.selectedTabIndex).setTabItem(itemIndex, tabItem);
    }
    
    public static void insertTabRow(int parentRow) {
        if (StateHandler.selectedTabIndex == toolbarTabs.size()) {
            createToolbarTab();
        }
    
        toolbarTabs.get(StateHandler.selectedTabIndex).addRow(parentRow);
    }
    
    public static void removeTabRow(int row) {
        if (StateHandler.selectedTabIndex == toolbarTabs.size()) {
            createToolbarTab();
        }
    
        toolbarTabs.get(StateHandler.selectedTabIndex).removeRow(row);
    }
    
    public static boolean isNotNewTab() {
        return StateHandler.selectedTabIndex != toolbarTabs.size();
    }
    
    public static int getTabCount() {
        return toolbarTabs.size();
    }
    
    public static ItemStack getTabIcon() {
        return getTabIcon(StateHandler.selectedTabIndex);
    }
    
    public static ItemStack getTabIcon(int tabIndex) {
        if (tabIndex == toolbarTabs.size()) {
            return ItemStack.EMPTY;
        } else {
            return toolbarTabs.get(tabIndex).tabIcon;
        }
    }
    
    public static ItemStack[] getTabItems() {
        if (StateHandler.selectedTabIndex == toolbarTabs.size()) {
            return new ItemStack[0];
        } else {
            return toolbarTabs.get(StateHandler.selectedTabIndex).getTabItems();
        }
    }
    
    public static String getOriginalTabName() {
        if (StateHandler.selectedTabIndex == toolbarTabs.size()) {
            return "New Toolbar Tab";
        } else {
            return toolbarTabs.get(StateHandler.selectedTabIndex).originalName;
        }
    }
    
    public static String getTabName(int tabIndex) {
        
        if (tabIndex == toolbarTabs.size()) {
            //Checks if the name "New Toolbar Tab" is already taken, if so, find a name that is not taken.
            for (ToolbarTab toolbarTab : toolbarTabs) {
                if (toolbarTab.originalName.equals("New Toolbar Tab")) {
                    return recursiveTabNameFinder("New Toolbar Tab", 1, tabIndex);
                }
            }
            
            return "New Toolbar Tab";
        } else {
            return toolbarTabs.get(tabIndex).tabName;
        }
    }
}
