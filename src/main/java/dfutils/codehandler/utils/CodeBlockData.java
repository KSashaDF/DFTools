package dfutils.codehandler.utils;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

public class CodeBlockData {
    
    public static NBTTagCompound codeReferenceData;
    
    public CodeBlockData() {

        try {
            //Reads codeData.json and converts it into an NBT tag.
            codeReferenceData = JsonToNBT.getTagFromJson(IOUtils.toString(this.getClass().getResourceAsStream("/assets/dfutils/codeData.json"), Charsets.UTF_8));
        } catch (IOException exception) {
            LogManager.getLogger().error("Uh oh! Encountered IO Exception while loading code reference data...");
        } catch (NBTException exception) {
            LogManager.getLogger().error("Uh oh, looks like the mod author messed up the code reference data NBT format. :/");
        }
    }
}
