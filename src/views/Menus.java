package views;

import model.Scholarship;
import service.ScholarshipService;
import service.impl.ScholarshipServiceImpl;

import java.sql.SQLException;
import java.util.List;

import static service.impl.UserServiceImpl.userService;
import static utils.InputUtils.*;
import static utils.PrintUtils.*;
import static views.Colors.*;
import static views.Tables.*;

public class Menus {
    private static final ScholarshipService scholarshipService = new ScholarshipServiceImpl();

    public static void run() throws SQLException {
        while (true) {
            renderStartMenu();
            int choice = readIntInRange(PURPLE+"> Choose: "+RESET,0,2);

            switch (choice) {
                case 1 -> userService.login();
                case 2 -> userService.signUp();
                case 0 -> System.exit(0);
                default -> printErr("Invalid option!");
            }
        }
    }

    public static void userMenu() {
        System.out.println("user");
    }

    public static void adminMenu() {
        while (true) {
            renderUserMenu();
            int choice = readIntInRange(PURPLE+"> Choose: "+RESET,0,5);

            switch (choice) {
                case 1 -> createNew();
                case 2 -> viewAll();
                case 3 -> searchById();
                case 4 -> update();
                case 5 -> delete();
                case 0 -> {
                    return;
                }
                default -> printErr("Invalid choice!");
            }
        }
    }

    public static void createNew() {
        printHead("Create New Scholarship");
        Scholarship s = new Scholarship();

        s.setType(readString("Enter Type : "));
        s.setDescription(readString("Enter Description : "));
        s.setScholarship(readInt("Enter Scholarship : "));
        s.setFullPrice(readBigDecimal("Enter Full Price: "));
        s.setSponsor(readString("Enter Sponsor: "));
        s.setDuration(readString("Enter Duration: "));
        s.setMaxQuota(readInt("Enter Max Quota: "));
        s.setYearLevel(readInt("Enter Year Level: "));
        s.setWeek(readString("Enter Week: "));
        s.setIsEnabled(true);

        // Call service to save to DB
        scholarshipService.createScholarship(s);
        printTrue("Scholarship created successfully!");
    }

    public static void searchById() {
        int id = readInt("Enter Scholarship ID: ");
        Scholarship s = scholarshipService.getScholarshipById(id);
        if (s != null) {
            System.out.println("Result: " + s);
        }
    }

    public static void viewAll() {
        List<Scholarship> list = scholarshipService.getAllScholarships();
        list.forEach(System.out::println);
    }

    public static void update() {
        int id = readInt("Enter ID of scholarship to update: ");
        printWarn("Press (Enter) to keep data...");
        Scholarship existing = scholarshipService.getScholarshipById(id);
        if (existing == null) {
            System.out.println("Scholarship not found!");
            return;
        }

        existing.setType(readOptionalString("Enter new Type (current: " + existing.getSponsor() + "): "));
        existing.setDescription(readOptionalString("Enter new Description (current: " + existing.getSponsor() + "): "));
        existing.setScholarship(readOptionalInt("Enter new Scholarship (current: " + existing.getSponsor() + "): "));
        existing.setFullPrice(readBigDecimal("Enter new Full Price (current: " + existing.getSponsor() + "): "));
        existing.setSponsor(readOptionalString("Enter new Sponsor (current: " + existing.getSponsor() + "): "));
        existing.setDuration(readOptionalString("Enter new Duration (current: " + existing.getSponsor() + "): "));
        existing.setMaxQuota(readOptionalInt("Enter new Max Quota (current: " + existing.getSponsor() + "): "));
        existing.setYearLevel(readOptionalInt("Enter new Year Level (current: " + existing.getSponsor() + "): "));
        existing.setWeek(readOptionalString("Enter new Week (current: " + existing.getSponsor() + "): "));
        existing.setIsEnabled(true);


        // Call service to push updates to DB
        scholarshipService.updateScholarship(existing);
        System.out.println("Scholarship updated!");
    }

    public static void delete() {
        int id = readInt("Enter ID to delete");

        Scholarship s = scholarshipService.getScholarshipById(id);
        if (s != null) {
            String confirm = readString("Are you sure you want to delete ID " + id + "? (Y/n): ");
            if (confirm.equalsIgnoreCase("y")) {
                scholarshipService.deleteScholarship(id);
                System.out.println("Deleted.");
            }
        }
    }

}
