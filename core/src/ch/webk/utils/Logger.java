package ch.webk.utils;

public class Logger {

    private static String T = "MyGame";
    public static boolean LOG_ALL = true;
    private boolean log = true;
    private String tag;


    public Logger(String tag, boolean log) {
        this.tag = tag;
        this.log = log;
    }

    public void i(String msg) {
        if (LOG_ALL && log) {
            System.out.println(T + "_" + tag + " | " + msg);
        }
    }

}
