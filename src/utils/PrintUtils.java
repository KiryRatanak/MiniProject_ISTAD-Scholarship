package utils;

import static utils.InputUtils.*;
import static views.Colors.*;

public class PrintUtils {
    public static String printHead(String title) {
        System.out.println(CYAN + "\n*** " + title.toUpperCase() +" ***" + RESET);
        return title;
    }

    public static void printTrue(String msg) {
        System.out.println(GREEN + "[✅] " + msg + RESET);
    }

    public static void printErr(String msg) {
        System.out.println(RED + "[❌] " + msg + RESET);
    }


    public static void printWarm(String msg) {
        System.out.println(YELLOW + "[!] " + msg + RESET);
    }

    public static void pressEnter() {
        readEnter(YELLOW+"[!] Press ENTER to continue....");
    }
}
