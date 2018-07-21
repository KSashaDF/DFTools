package dfutils.bettertoolbars;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class ToolbarTabHandler {

    private static ToolbarTab[] toolbarTabs;
    private static Minecraft minecraft = Minecraft.getMinecraft();
    private static File toolbarDataDir = new File(minecraft.mcDataDir, "toolbars");
    private static File toolbarDataFile = new File(toolbarDataDir, "ToolbarTabs.nbt");

    //Stores the last time that the toolbar tab data file was reloaded, used to determine if
    //the toolbar tab data file needs to be reloaded.
    private static long lastReloadedDataTime = 0;

    static ToolbarTab[] getToolbarTabs() throws IOException {

        if (!toolbarDataDir.isDirectory()) {
            toolbarDataDir.mkdirs();
        }

        if (!toolbarDataFile.exists()) {
            toolbarDataFile.createNewFile();

            IOUtils.write("{ToolbarTabs:[]}", new FileOutputStream(toolbarDataFile), Charsets.UTF_8);
            lastReloadedDataTime = toolbarDataFile.lastModified();

            return null;
        }

        if (toolbarDataFile.lastModified() > lastReloadedDataTime) {
            lastReloadedDataTime = toolbarDataFile.lastModified();
        }

        return toolbarTabs;
    }

    /*static ItemStack[] getToolbarItems(int tabIndex) {

    }*/
}
