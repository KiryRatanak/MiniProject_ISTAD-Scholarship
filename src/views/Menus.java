package views;

import model.Scholarship;
import service.ScholarshipService;
import service.impl.ScholarshipServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static service.impl.UserServiceImpl.userService;
import static utils.InputUtils.*;
import static utils.PrintUtils.*;
import static views.Colors.*;
import static views.Tables.*;

public class Menus {
    public static final ScholarshipService scholarshipService = new ScholarshipServiceImpl();

    public static void run() throws SQLException {
        while (true) {
            renderStartMenu();
            int choice = readIntInRange(PURPLE+"> Choose : "+RESET,0,2);

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

            renderAdminMenu();
            int choice = readIntInRange(PURPLE+"> Choose : "+RESET,0,2);

            switch (choice){
                case 1 -> scholarshipMenu();
                case 2 -> enrollmentMenu();
                case 0 -> {
                    return;
                }
                default -> printErr("Invalid choice..!");
            }
        }
    }

    public static void scholarshipMenu(){
        while (true) {

            renderScholarshipMenu();
            int choice = readIntInRange(PURPLE+"> Choose : "+RESET,0,5);

            switch (choice) {
                case 1 -> createScholarship();
                case 2 -> viewAllScholarship();
                case 3 -> searchScholarshipById();
                case 4 -> updateScholarship();
                case 5 -> deleteScholarship();
                case 0 -> {
                    return;
                }
                default -> printErr("Invalid choice..!");
            }
        }
    }

    public static void enrollmentMenu(){
        while (true) {

            renderEnrollmentMenu();
            int choice = readIntInRange(PURPLE+"> Choose : "+RESET,0,5);

            switch (choice) {
//                case 1 -> createScholarship();
//                case 2 -> viewAllScholarship();
//                case 3 -> searchScholarshipById();
//                case 4 -> updateScholarship();
                case 5 -> deleteScholarship();
                case 0 -> {
                    return;
                }
                default -> printErr("Invalid choice.");
            }
        }
    }

    public static void createScholarship() {
        printHead("Create New Scholarship");
        Scholarship s = new Scholarship();

        s.setType(readString("Enter Type : "));
        s.setDescription(readString("Enter Description : "));
        s.setScholarship(readInt("Enter Scholarship : "));
        s.setFullPrice(readBigDecimal("Enter Full Price : "));
        s.setSponsor(readString("Enter Sponsor : "));
        s.setDuration(readString("Enter Duration : "));
        s.setMaxQuota(readInt("Enter Max Quota : "));
        s.setYearLevel(readInt("Enter Year Level : "));
        s.setWeek(readString("Enter Week : "));
        s.setIsEnabled(true);

        scholarshipService.createScholarship(s);
        printTrue("Scholarship created successfully!");
    }

    public static void searchScholarshipById() {

        int id = readInt("Enter Scholarship ID : ");

        Scholarship s = scholarshipService.getScholarshipById(id);

        if (s != null) {
            System.out.println("✅ Found Result :");
            renderSearchScholarship(s);
        }
    }

    public static void viewAllScholarship() {
        List<Scholarship> list = scholarshipService.getAllScholarships();
        renderAllScholarship(list);
    }

    public static void updateScholarship() {
        int id = readInt("Enter ID to update : ");
        printWarn("Press (Enter) to keep data...");
        Scholarship existing = scholarshipService.getScholarshipById(id);
        if (existing == null) {
            printErr("Scholarship not found.");
            return;
        }

        printCurrent("(Current : " + existing.getType() + ")");
        String type = readOptionalString("Enter new Type : ");
        if (type != null) existing.setType(type);

        printCurrent("(Current : " + existing.getDescription() + ")");
        String desc = readOptionalString("Enter new Description : ");
        if (desc != null) existing.setDescription(desc);

        printCurrent("(Current : " + existing.getScholarship() + ")");
        Integer scholarship = readOptionalInt("Enter new Scholarship : ");
        if (scholarship != null) existing.setScholarship(scholarship);

        printCurrent("(Current : " + existing.getFullPrice() + ")");
        BigDecimal price = readOptionalBigDecimal("Enter new Full Price : ");
        if (price != null) existing.setFullPrice(price);

        printCurrent("(Current : " + existing.getSponsor() + ")");
        String sponsor = readOptionalString("Enter new Sponsor : ");
        if (sponsor != null) existing.setSponsor(sponsor);

        printCurrent("(Current : " + existing.getDuration() + ")");
        String duration = readOptionalString("Enter new Duration : ");
        if (duration != null) existing.setDuration(duration);

        printCurrent("(Current : " + existing.getMaxQuota() + ")");
        Integer quota = readOptionalInt("Enter new Max Quota : ");
        if (quota != null) existing.setMaxQuota(quota);

        printCurrent("(Current : " + existing.getYearLevel() + ")");
        Integer year = readOptionalInt("Enter new Year Level : ");
        if (year != null) existing.setYearLevel(year);

        printCurrent("(Current : " + existing.getWeek() + ")");
        String week = readOptionalString("Enter new Week : ");
        if (week != null) existing.setWeek(week);
        existing.setIsEnabled(true);


        scholarshipService.updateScholarship(existing);
        printTrue("Scholarship updated!");
    }

    public static void deleteScholarship() {
        int id = readInt("Enter ID to delete : ");

        Scholarship s = scholarshipService.getScholarshipById(id);
        if (s != null) {
            String confirm = readString("Are you sure to deleteScholarship ID " + id + "? (Y/n) : ");
            if (confirm.equalsIgnoreCase("y")) {
                scholarshipService.deleteScholarship(id);
                System.out.println("Deleted.");
            }
        }
    }

}
