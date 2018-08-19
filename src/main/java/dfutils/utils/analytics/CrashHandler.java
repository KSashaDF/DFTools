package dfutils.utils.analytics;

import net.minecraft.client.Minecraft;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Runnable {
    
    @Override
    public void run() {
        File crashDirectory = new File(Minecraft.getMinecraft().mcDataDir, "crash-reports");
        File crashReport = new File(crashDirectory, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        
        if (crashReport.exists()) {
            try (InputStream inputStream = Files.newInputStream(crashReport.toPath())) {
                AnalyticsHandler.send(AnalyticType.GAME_CRASH, IOUtils.toString(inputStream, Charsets.UTF_8));
            } catch (IOException ignored) {}
        }
    }
}
