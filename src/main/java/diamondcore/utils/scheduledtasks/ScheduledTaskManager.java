package diamondcore.utils.scheduledtasks;

import diamondcore.Reference;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ScheduledTaskManager {
	
	private static final Minecraft minecraft = Minecraft.getMinecraft();
	private static int ticksRun;
	private static ArrayList<ScheduledTask> tasks = new ArrayList<>();
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (minecraft.player != null) {
			if (event.phase == TickEvent.Phase.END) {
				ticksRun++;
				updateTasks();
			}
		} else {
			ticksRun = 0;
			tasks.clear();
		}
	}
	
	private static void updateTasks() {
		if (!tasks.isEmpty() && tasks.get(0).triggerTickTime < ticksRun) {
			tasks.get(0).task.runTask();
			tasks.remove(0);
		}
	}
	
	public static void scheduleTask(int tickDelay, IScheduledTask scheduledTask) {
		ScheduledTask taskObject = new ScheduledTask(ticksRun + tickDelay, scheduledTask);
		
		if (tasks.isEmpty()) {
			tasks.add(taskObject);
		} else {
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).triggerTickTime > taskObject.triggerTickTime) {
					tasks.add(i, taskObject);
					return;
				}
			}
			
			tasks.add(taskObject);
		}
	}
}
