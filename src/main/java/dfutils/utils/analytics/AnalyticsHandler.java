package dfutils.utils.analytics;

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
    
    static AnalyticType analyticType;
    public static String data;

    public static void send(AnalyticType analyticType) {
        sendData(analyticType);
    }

    public static void send(AnalyticType analyticType, String data) {
        sendData(analyticType, data);
    }

    //TODO Finish stuffs
    private static void sendData(AnalyticType analyticTypeIn) {
        analyticType = analyticTypeIn;
        new Thread(new SendAnalytic(), "AnalyticsHandler").start();
    }

    private static void sendData(AnalyticType analyticTypeIn, String dataIn) {
        analyticType = analyticTypeIn;
        data = dataIn;
        new Thread(new SendDataAnalytic(), "AnalyticsHandler").start();
    }
    
    static class SendAnalytic implements Runnable {
        
        private static final Minecraft minecraft = Minecraft.getMinecraft();
        
        @Override
        public void run() {
            try {
                URL url = new URL(Reference.HOST_URL + "api/sendAnalytic?uuid=" + URLEncoder.encode(minecraft.getSession().getProfile().getId().toString(), "UTF-8") + "&type=" + URLEncoder.encode(AnalyticsHandler.analyticType.name(), "UTF-8") + "&version=" + URLEncoder.encode(Reference.VERSION, "UTF-8"));
                URLConnection urlConnection = url.openConnection();
                try (InputStream inputStream = urlConnection.getInputStream()) {
                    JsonObject result = new JsonParser().parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
                    
                    if (result.has("ERROR")) {
                        System.out.println("Error while sending analytic: " + result.get("ERROR").getAsString());
                    }
                } catch (FileNotFoundException ignored) {}
                
            } catch (IOException ignored) {}
        }
    }
    
    static class SendDataAnalytic implements Runnable {
        
        private static final Minecraft minecraft = Minecraft.getMinecraft();
        
        @Override
        public void run() {
            try {
                URL url = new URL(Reference.HOST_URL + "api/sendAnalytic?uuid=" + URLEncoder.encode(minecraft.getSession().getProfile().getId().toString(), "UTF-8") + "&data=" + URLEncoder.encode(AnalyticsHandler.data, "UTF-8") + "&type=" + URLEncoder.encode(AnalyticsHandler.analyticType.name(), "UTF-8") + "&version=" + URLEncoder.encode(Reference.VERSION, "UTF-8"));
                URLConnection urlConnection = url.openConnection();
                try (InputStream inputStream = urlConnection.getInputStream()) {
                    JsonObject result = new JsonParser().parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
                    
                    if (result.has("ERROR")) {
                        System.out.println("Error while sending analytic: " + result.get("ERROR").getAsString());
                    }
                } catch (FileNotFoundException ignored) {}
                
            } catch (IOException ignored) {}
        }
    }
}