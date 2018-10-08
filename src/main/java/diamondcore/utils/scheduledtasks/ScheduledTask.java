package diamondcore.utils.scheduledtasks;

class ScheduledTask {
	
	int triggerTickTime;
	IScheduledTask task;
	
	ScheduledTask(int triggerTickTime, IScheduledTask task) {
		this.triggerTickTime = triggerTickTime;
		this.task = task;
	}
}
