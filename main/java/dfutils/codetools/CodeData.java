package dfutils.codetools;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.LogManager;
public class CodeData {
    
    public static NBTTagCompound codeReferenceData;
    
    public CodeData() {

        //Alright, I know this is EXTREMELY lazy coding, but lets be honest, this works fine, so why not? :P
        try {
            codeReferenceData = JsonToNBT.getTagFromJson("{\n" +
                    "    \"PLAYER_ACTION\": {\n" +
                    "\n" +
                    "        \"CodeTarget\": {\n" +
                    "            \"Selection\": \"§aCurrent Selection\",\n" +
                    "            \"Default\": \"§aDefault Player\",\n" +
                    "            \"Random\": \"§eRandom Player\",\n" +
                    "            \"All\": \"§bAll Players\",\n" +
                    "            \"Killer\": \"§cKiller\",\n" +
                    "            \"Damager\": \"§cDamager\",\n" +
                    "            \"Shooter\": \"§eShooter\",\n" +
                    "            \"Victim\": \"§9Victim\"\n" +
                    "        },\n" +
                    "\n" +
                    "        \"GiveItems\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§6Give Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetItems\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§5Set Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetArmor\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§6Set Armor\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetOffHand\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§cSet Item In Off Hand\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"RemoveItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eRemove Items\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ClearInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eClear Inventory\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ShowInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eShow Inventory Menu\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"CloseInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§aClose All Inventory Menus\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"ExpandInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§bExpand Inventory Menu\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SaveInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§aSave Current Inventory\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"LoadInv\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§eLoad Saved Inventory\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"SetSlot\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§9Set Hotbar Slot\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"GiveRngItem\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§6Item Management\",\n" +
                    "                \"§6Give Random Item\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    },\n" +
                    "\n" +
                    "    \"PLAYER_EVENT\": {\n" +
                    "        \"Join\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§aPlayer Join Game Event\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"Quit\": {\n" +
                    "            \"path\": [\n" +
                    "                \"§cPlayer Quit Game Event\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    }\n" +
                    "}");
        } catch (NBTException exception) {
            LogManager.getLogger().error("Hmm, it appears the mod author has screwed up the code reference data format. :/");
        }
    }
}
