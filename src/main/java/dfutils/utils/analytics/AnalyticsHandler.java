package dfutils.utils.analytics;

// -------------------------
// Created by: Timeraa
// Created at: 16.08.18
// -------------------------


import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AnalyticsHandler {
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public static void send(AnalyticType aType) {
        sendData(aType);
    }

    public static void send(AnalyticType aType, String data) {
        sendData(aType, data);
    }

    //TODO Finish stuffs
    private static void sendData(AnalyticType aType) {
        try {
            URL url = new URL("https://df.pocketclass.net/api/sendAnalytic?UUID=" + URLEncoder.encode(minecraft.getSession().getProfile().getId().toString(), "UTF-8") + "&type=" + URLEncoder.encode(aType.name(), "UTF-8"));
            URLConnection urlConnection = url.openConnection();
            try (InputStream inputStream = urlConnection.getInputStream()) {
                JsonObject result = new JsonParser().parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();

                System.out.println(result);
            } catch (FileNotFoundException e) {
                // Most likely offline user...
            }

        } catch (IOException e) {
        }
    }

    private static void sendData(AnalyticType aType, String data) {
        try {
            URL url = new URL("https://df.pocketclass.net/api/sendAnalytic?UUID=" + URLEncoder.encode(minecraft.player.getUniqueID().toString(), "UTF-8") + "&type=" + URLEncoder.encode(aType.name(), "UTF-8") + "&data=" + URLEncoder.encode(data, "UTF-8"));
            URLConnection urlConnection = url.openConnection();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
