package dfutils.utils.analytics;

// -------------------------
// Created by: Timeraa
// Created at: 16.08.18
// -------------------------


import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dfutils.Reference;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AnalyticsHandler {
    public static AnalyticType aType;
    public static String data;

    public static void send(AnalyticType aType) {
        sendData(aType);
    }

    public static void send(AnalyticType aType, String data) {
        sendData(aType, data);
    }

    //TODO Finish stuffs
    private static void sendData(AnalyticType atype) {
        aType = atype;
        new Thread(new sendData(), "AnalyticsHandler").start();
    }

    private static void sendData(AnalyticType atype, String data1) {
        aType = atype;
        data = data1;
        new Thread(new sendData1(), "AnalyticsHandler").start();
    }
}

class sendData implements Runnable {
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    @Override
    public void run() {
        try {
            URL url = new URL(Reference.HOSTURL + "api/sendAnalytic?uuid=" + URLEncoder.encode(minecraft.getSession().getProfile().getId().toString(), "UTF-8") + "&type=" + URLEncoder.encode(AnalyticsHandler.aType.name(), "UTF-8") + "&version=" + URLEncoder.encode(Reference.VERSION, "UTF-8"));
            URLConnection urlConnection = url.openConnection();
            try (InputStream inputStream = urlConnection.getInputStream()) {
                JsonObject result = new JsonParser().parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();

                if (result.has("ERROR")) {
                    System.out.println("Error while sending analytic: " + result.get("ERROR").getAsString());
                }
            } catch (FileNotFoundException ignored) {
            }

        } catch (IOException ignored) {
        }
    }
}

class sendData1 implements Runnable {
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    @Override
    public void run() {
        try {
            URL url = new URL(Reference.HOSTURL + "api/sendAnalytic?uuid=" + URLEncoder.encode(minecraft.getSession().getProfile().getId().toString(), "UTF-8") + "&data=" + URLEncoder.encode(AnalyticsHandler.data, "UTF-8") + "&type=" + URLEncoder.encode(AnalyticsHandler.aType.name(), "UTF-8") + "&version=" + URLEncoder.encode(Reference.VERSION, "UTF-8"));
            URLConnection urlConnection = url.openConnection();
            try (InputStream inputStream = urlConnection.getInputStream()) {
                JsonObject result = new JsonParser().parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();

                if (result.has("ERROR")) {
                    System.out.println("Error while sending analytic: " + result.get("ERROR").getAsString());
                }
            } catch (FileNotFoundException ignored) {
            }

        } catch (IOException ignored) {
        }
    }
}