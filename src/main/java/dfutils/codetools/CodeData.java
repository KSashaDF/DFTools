package dfutils.codetools;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStreamReader;

public class CodeData {
    
    public static NBTTagCompound codeReferenceData;
    
    public CodeData() {

        try {
            //Reads codeData.json and converts it into an NBT tag.
            codeReferenceData = JsonToNBT.getTagFromJson(IOUtils.toString(new InputStreamReader(this.getClass().getResourceAsStream("/assets/dfutils/codeData.json"))));
        } catch (IOException exception) {
            LogManager.getLogger().error("Uh oh! Encountered IO Exception while loading code reference data...");
        } catch (NBTException exception) {
            LogManager.getLogger().error("Uh oh, looks like the mod author messed up the code reference data NBT format. :/");
        }
    }
}
