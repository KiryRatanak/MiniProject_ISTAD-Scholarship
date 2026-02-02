package views;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import static views.Colors.*;

public class Tables {
    public static void renderStartMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        t.addCell(GREEN + "WELCOME TO ISTAD SCHOLARSHIP SYSTEM" + RESET);
        t.addCell(BLUE + "1. Login" + RESET);
        t.addCell(BLUE + "2. Sign Up" + RESET);
        t.addCell(RED + "0. Exit" + RESET);
        System.out.println(t.render());

    }

    public static void renderGreen(String messages){
        Table t = new Table(1, BorderStyle.UNICODE_ROUND_BOX);
        t.addCell(GREEN +"[+] "+ messages + RESET);
        System.out.println(t.render());
    }

    public static void renderRed(String messages){
        Table t = new Table(1, BorderStyle.UNICODE_ROUND_BOX);
        t.addCell(RED +"[x] "+ messages + RESET);
        System.out.println(t.render());
    }
}
