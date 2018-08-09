package dfutils.commands.itemcontrol.item;

import com.google.common.base.Charsets;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ListItems implements Runnable {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    @Override
    public void run() {
        
        try {
            URL url;
            if (CommandItem.page != 0) {
                url = new URL("https://df.pocketclass.net/api/getItems?initialOffset=" + CommandItem.page + "&ownerUUID=" + URLEncoder.encode(minecraft.player.getUniqueID().toString(), "UTF-8"));
            } else {
                url = new URL("https://df.pocketclass.net/api/getItems?ownerUUID=" + URLEncoder.encode(minecraft.player.getUniqueID().toString(), "UTF-8"));
            }
            URLConnection urlConnection = url.openConnection();

            try (InputStream inputStream = urlConnection.getInputStream()) {
                NBTTagCompound response = JsonToNBT.getTagFromJson(IOUtils.toString(inputStream, Charsets.UTF_8));
                ArrayList<String> uploadedItemNames = convertToStringArray(response.getCompoundTag("SUCCESS").getCompoundTag("names"));
                
                minecraft.player.sendMessage(new TextComponentString("§e§m          §6 [ §eYour Items §6] §e§m          \n"));
                for (int i = 0; i < uploadedItemNames.size(); i++) {
                    //Creates the click and hover events for the message.
                    Style messageStyle = new Style();
                    messageStyle.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/item download " + minecraft.player.getUniqueID() + " " + uploadedItemNames.get(i)));
                    messageStyle.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("§aClick to download.")));

                    int itemCount = i + 1;
                    if (CommandItem.page == 2) {
                        itemCount = itemCount + 16;
                    } else if (CommandItem.page == 3) {
                        itemCount = itemCount + (16 * CommandItem.page);
                    }
                    
                    TextComponentString name = new TextComponentString("§b❱§3❱ §e§l" + itemCount + "§b: §r" + uploadedItemNames.get(i));
                    name.setStyle(messageStyle);
                    minecraft.player.sendMessage(name);
                }
                
                minecraft.player.sendMessage(new TextComponentString(""));
                minecraft.player.sendMessage(new TextComponentString("§b❱§3❱ §bPage §3" + CommandItem.page));
                minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, -2f);
            } catch (NBTException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException exception) {
        } catch (IOException exception) {}
    }

    private ArrayList<String> convertToStringArray(NBTTagCompound compoundTag) {
        boolean getArrayLength = true;
        int i = 1;
        ArrayList<String> stringArray = new ArrayList<>();
        while (getArrayLength) {
            String text = compoundTag.getString(Integer.toString(i));
            if (!text.equals("")) {
                stringArray.add(compoundTag.getString(Integer.toString(i)));
            } else {
                getArrayLength = false;
            }
            i++;
        }
        return stringArray;
    }
}