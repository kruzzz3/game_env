package ch.webk.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
        l.i("addTask key="+key);
        l.i("addTask milli="+milli);
        long fTime = System.currentTimeMillis() + (long)milli;
        finishTime.put(key,fTime);
        l.i("addTask sec="+(milli / 1000f));
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
                l.i("lTime="+lTime);
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
        String key = task.toString();
        l.i("getTimeLeft key="+key);
        l.i("getTimeLeft milli="+timeLeft.get(key));
        return (float) timeLeft.get(key);
    }

}
