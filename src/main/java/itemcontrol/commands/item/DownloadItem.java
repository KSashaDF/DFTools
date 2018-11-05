package itemcontrol.commands.item;

import com.google.common.base.Charsets;
import diamondcore.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class DownloadItem implements Runnable {
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	
	@Override
	public void run() {
		try {
			URL url;
			if (CommandItem.ownerUUID != null) {
				url = new URL("https://df.pocketclass.net/api/downloadItem?ownerUUID=" + URLEncoder.encode(CommandItem.ownerUUID) + "&name=" + URLEncoder.encode(CommandItem.itemID, "UTF-8"));
			} else {
				url = new URL("https://df.pocketclass.net/api/downloadItem?itemID=" + URLEncoder.encode(CommandItem.itemID, "UTF-8"));
			}
			URLConnection urlConnection = url.openConnection();
			
			try (InputStream inputStream = urlConnection.getInputStream()) {
				NBTTagCompound response = JsonToNBT.getTagFromJson(IOUtils.toString(inputStream, Charsets.UTF_8));
				
				if (response.hasKey("ERROR")) {
					minecraft.player.sendMessage(new TextComponentString("§c❱§4❱ §cID \"" + CommandItem.itemID + "§c\" invalid."));
					minecraft.player.playSound(SoundEvents.ENTITY_CAT_HURT, 1f, 1f);
				} else {
					minecraft.player.sendMessage(new TextComponentString("§a❱§2❱ §aSuccessfully downloaded \"§r" + response.getString("name") + "§a\" by §e" + response.getString("ownerName")));
					minecraft.player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1f, -2f);
					ItemUtils.setItemInHotbar(new ItemStack(response.getCompoundTag("SUCCESS").getCompoundTag("itemNBT")), true);
				}
				
				
				CommandItem.isDownloading = false;
			} catch (NBTException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException exception) {
		
		} catch (IOException exception) {
		
		}
	}
}