package dfutils.utils;

// -------------------------
// Created by: Timeraa
// Created at: 09.08.18
// -------------------------


import com.google.common.base.Charsets;
import dfutils.commands.CommandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class uploadItem implements Runnable {
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    private static String urlString = "";

    private static boolean overWriteItem = false;

    @Override
    public void run() {
        NBTTagCompound itemToUpload = ItemUtils.toNbt(minecraft.player.getHeldItemMainhand());
        try {
            System.out.println(CommandItem.itemNameOld);

            if (CommandItem.itemName.equals(CommandItem.itemNameOld)) {
                urlString = "https://df.pocketclass.net/api/uploadItem?overwrite=true&name=" + URLEncoder.encode(CommandItem.itemName) + "&ownerUUID=" + URLEncoder.encode(minecraft.player.getUniqueID().toString(), "UTF-8") + "&ownerName=" + URLEncoder.encode(minecraft.player.getName(), "UTF-8") + "&itemNBT=" + URLEncoder.encode(itemToUpload.toString(), "UTF-8");
                overWriteItem = true;
            } else {
                urlString = "https://df.pocketclass.net/api/uploadItem?name=" + URLEncoder.encode(CommandItem.itemName) + "&ownerUUID=" + URLEncoder.encode(minecraft.player.getUniqueID().toString(), "UTF-8") + "&ownerName=" + URLEncoder.encode(minecraft.player.getName(), "UTF-8") + "&itemNBT=" + URLEncoder.encode(itemToUpload.toString(), "UTF-8");
                overWriteItem = false;
            }

            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            try (InputStream inputStream = urlConnection.getInputStream()) {
                NBTTagCompound response = JsonToNBT.getTagFromJson(IOUtils.toString(inputStream, Charsets.UTF_8));
                if (response.hasKey("ERROR")) {
                    minecraft.player.sendMessage(new TextComponentString("§c❱§6❱ §eYou are about to overwrite \"§r" + CommandItem.itemName + "§e\""));
                    minecraft.player.sendMessage(new TextComponentString("§c❱§6❱ §eTo continue repeat the previous command."));
                    minecraft.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, -2f);
                    CommandItem.itemNameOld = CommandItem.itemName;
                } else {
                    if (overWriteItem) {
                        minecraft.player.sendMessage(new TextComponentString("§a❱§2❱ §aItem updated! ID copied to your clipboard."));
                    } else {
                        minecraft.player.sendMessage(new TextComponentString("§a❱§2❱ §aItem uploaded! ID copied to your clipboard."));
                    }
                    minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, -2f);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(response.getString("SUCCESS")), null);
                    CommandItem.itemNameOld = "";
                }
                CommandItem.isUploading = false;
            } catch (NBTException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException exception) {

        } catch (IOException exception) {

        }
    }
}