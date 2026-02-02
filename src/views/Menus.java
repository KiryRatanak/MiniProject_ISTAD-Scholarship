package views;

import java.sql.SQLException;

import static service.impl.UserServiceImpl.userService;
import static utils.InputUtils.*;
import static utils.PrintUtils.*;
import static views.Colors.*;
import static views.Tables.*;

public class Menus {

    public static void run() throws SQLException {
        while (true) {
            renderStartMenu();
            int choice = readIntInRange(PURPLE+"> Choose: "+RESET,0,2);

            switch (choice) {
                case 1 -> userService.login();
                case 2 -> userService.signUp();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid option!");
            }
        }
    }

    public static void userMenu() {
        System.out.println("user");
    }

    public static void adminMenu() {
        System.out.println("admin");
    }

}
