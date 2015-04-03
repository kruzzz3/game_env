package ch.webk.utils.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class BreakableTimer {

    private static Logger l = new Logger("BreakableTimer", true);

    private static HashMap<String,Long> finishTime;
    private static HashMap<String,Long> timeLeft;
    private static ArrayList<Timer.Task> tasks;
    private static Timer timer;

    public static void clear() {
        timer = Timer.instance();
        finishTime = new HashMap<String,Long>();
        timeLeft = new HashMap<String,Long>();
        tasks = new ArrayList<Timer.Task>();
    }

    public static Task addTask(Task task, float milli) {
        String key = task.toString();
        long fTime = System.currentTimeMillis() + (long)milli;
        finishTime.put(key,fTime);
        timer.schedule(task, milli / 1000f);
        return task;
    }

    public static void stop() {
        timer.stop();
        timer.clear();
        long cTime = System.currentTimeMillis();
        if (!finishTime.isEmpty()) {
            timeLeft = new HashMap<String,Long>();

            Iterator iterator = finishTime.keySet().iterator();
            while(iterator.hasNext()) {
                String key = (String) iterator.next();
                long fTime = finishTime.get(key);
                long lTime = fTime - cTime;
                timeLeft.put(key,lTime);
            }

            for (Task task : tasks) {
                task.cancel();
            }
        }
        timer.start();
        timer = Timer.instance();
        tasks = new ArrayList<Timer.Task>();
        finishTime = new HashMap<String,Long>();
    }

    public static float getTimeLeft(Task task) {
        return (float) timeLeft.get(task.toString());
    }

}
