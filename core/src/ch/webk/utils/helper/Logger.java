package ch.webk.utils.helper;

public class Logger {

    private static String T = "MG";
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
