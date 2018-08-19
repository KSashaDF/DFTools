package dfutils.utils.analytics;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Runnable {
    
    @Override
    public void run() {
        File crashDirectory = new File(Minecraft.getMinecraft().mcDataDir, "crash-reports");
        File crashReport = new File(crashDirectory, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        
        System.out.println("Searching for crash report file...");
        if (crashReport.exists()) {
            System.out.println("Found crash report file!");
        }
    }
}
