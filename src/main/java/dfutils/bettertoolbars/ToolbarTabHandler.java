package dfutils.bettertoolbars;

import dfutils.utils.ItemUtils;
import dfutils.utils.MessageUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.*;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class ToolbarTabHandler {

    static ToolbarTab[] toolbarTabs;
    private static Minecraft minecraft = Minecraft.getMinecraft();
    static File toolbarDataDir = new File(minecraft.mcDataDir, "toolbars");
    private static File toolbarDataFile = new File(minecraft.mcDataDir, "toolbars/ToolbarTabs.nbt");

    //Stores the last time that the toolbar tab data file was reloaded, used to determine if
    //the toolbar tab data file needs to be reloaded.
    private static long lastReloadedDataTime = toolbarDataFile.lastModified() - 1;

    static void createToolbarTab() {

        //Creates the default tab to be used when creating a new tab.
        ToolbarTab defaultTab;
        try {
            defaultTab = new ToolbarTab(JsonToNBT.getTagFromJson("" +
                    "{Name:\"Toolbar Tab\",FileName:\"Toolbar_Tab\",OriginalName:\"Toolbar Tab\",IconItem:{}}"));
        } catch (NBTException exception) {
            //Impossible condition!
            return;
        }

        //If there are no toolbar tabs, create a new one, else, add a new toolbar tab onto the list.
        if (toolbarTabs == null) {
            toolbarTabs = new ToolbarTab[1];
            toolbarTabs[0] = defaultTab;
        } else {
            ToolbarTab[] newToolbarTabList = new ToolbarTab[toolbarTabs.length + 1];
            System.arraycopy(toolbarTabs, 0, newToolbarTabList, 0, toolbarTabs.length);

            newToolbarTabList[newToolbarTabList.length - 1] = defaultTab;
            toolbarTabs = newToolbarTabList;

            renameToolbarTab("Toolbar Tab", toolbarTabs.length - 1);
        }

        //Creates the default 5 toolbar rows.
        toolbarTabs[toolbarTabs.length - 1].addRow(4);
    }

    //NOTE: If you are wondering what tabIndex is for, it is so the the various name checks
    //ignore the tab actually being renamed.
    static void renameToolbarTab(String newName, int tabIndex) {
        String modifiedName = newName;
        ToolbarTab toolbarTab = toolbarTabs[tabIndex];
        toolbarTab.originalName = newName;

        //Checks if the name is already taken, if so, find a valid number suffix for the name.
        for (int i = 0; i < toolbarTabs.length; i++) {
            if (i != tabIndex && toolbarTabs[i].originalName.equals(newName)) {
                modifiedName = recursiveTabNameFinder(newName, 1, tabIndex);
                break;
            }
        }

        toolbarTab.tabName = modifiedName;

        //Replaces illegal file name characters and also makes sure the file name is unique.
        modifiedName = modifiedName.replace(" ", "_");
        modifiedName = modifiedName.replace("/", ":");
        for (int i = 0; i < toolbarTabs.length; i++) {
            if (i != tabIndex && toolbarTabs[i].fileName.equals(modifiedName)) {
                modifiedName = recursiveFileNameFinder(newName, 1, tabIndex);
                break;
            }
        }

        toolbarTab.fileName = modifiedName;
    }

    private static String recursiveTabNameFinder(String tabName, int tabNameSuffix, int tabIndex) {
        String modifiedTabName = tabName + " " + tabNameSuffix;

        //Checks if the tab name is already taken, if so, tries the next number suffix.
        for (int i = 0; i < toolbarTabs.length; i++) {
            if (i != tabIndex && toolbarTabs[i].tabName.equals(modifiedTabName)) {
                return recursiveTabNameFinder(tabName, tabNameSuffix + 1, tabIndex);
            }
        }

        return modifiedTabName;
    }

    private static String recursiveFileNameFinder(String fileName, int fileNameSuffix, int tabIndex) {
        String modifiedFileName = fileName + "_" + fileNameSuffix;

        //Checks if the file name is already taken, if so, tries the next number suffix.
        for (int i = 0; i < toolbarTabs.length; i++) {
            if (i != tabIndex && toolbarTabs[i].fileName.equals(modifiedFileName)) {
                return recursiveFileNameFinder(fileName, fileNameSuffix + 1, tabIndex);
            }
        }

        return modifiedFileName;
    }

    public static void loadToolbarTabs() throws IOException {

        //If the toolbar data folder does not exist, create one.
        if (!toolbarDataDir.isDirectory()) {
            toolbarDataDir.mkdirs();
        }

        //If the toolbar data file does not exist, create one.
        if (!toolbarDataFile.exists()) {
            try (OutputStream outputStream = Files.newOutputStream(toolbarDataFile.toPath(), StandardOpenOption.CREATE)) {

                IOUtils.write("{ToolbarTabs:[]}", outputStream, Charsets.UTF_8);
                lastReloadedDataTime = toolbarDataFile.lastModified();
                return;
            }
        }

        if (lastReloadedDataTime < toolbarDataFile.lastModified()) {
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
            toolbarTabs = new ToolbarTab[toolbarTabNbtList.tagCount()];
            for (int i = 0; i < toolbarTabNbtList.tagCount(); i++) {
                toolbarTabs[i] = new ToolbarTab(toolbarTabNbtList.getCompoundTagAt(i));
            }

            lastReloadedDataTime = toolbarDataFile.lastModified();
        }
    }

    //This method simply saves all the toolbar tab data.
    static void saveTabs() {

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
    }
}
