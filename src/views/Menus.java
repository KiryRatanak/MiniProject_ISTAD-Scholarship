package views;

import model.Enrollment;
import model.Scholarship;
import service.EnrollmentService;
import service.ScholarshipService;
import service.impl.EnrollmentServiceImpl;
import service.impl.ScholarshipServiceImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static model.User.currentUser;
import static service.impl.UserServiceImpl.userService;
import static utils.InputUtils.*;
import static utils.PrintUtils.*;
import static views.Colors.*;
import static views.Tables.*;

public class Menus {
    public static final ScholarshipService scholarshipService = new ScholarshipServiceImpl();
    private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl();

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

        while (true){
            renderUserMenu();
            int choice = readIntInRange(PURPLE+"> Choose : "+RESET,0,6);

            switch (choice) {
                case 1 -> viewAllScholarship();
                case 2 -> searchScholarshipById();
                case 3 -> applyScholarship();
                case 4 -> viewOwnEnrollment();
                case 5 -> updateOwnEnrollment();
                case 6 -> deleteOwnEnrollment();
                case 0 -> {return;}
                default -> printErr("Invalid choice.");
            }
        }

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
                default -> printErr("Invalid choice.");
            }
        }
    }

    /* admin */

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
                case 1 -> applyScholarship();
                case 2 -> viewAllEnrollments();
                case 3 -> searchEnrollments();
                case 4 -> updateEnrollmentByAdmin();
                case 5 -> deleteEnrollmentByAdmin();
                case 0 -> {
                    return;
                }
                default -> printErr("Invalid choice.");
            }
        }
    }

    // scholarship

    public static void createScholarship() {
        printHead("Create New Scholarship");
        Scholarship s = new Scholarship();

        try {

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
        catch (RuntimeException e){
            printErr(e.getMessage());
        }
    }

    public static void searchScholarshipById() {

        int id = readInt("Enter Scholarship ID : ");

        try {

            Scholarship s = scholarshipService.getScholarshipById(id);

            if (s != null) {
                printTrue("Found Result");
                renderSearchScholarship(s);
            }
        }
        catch (RuntimeException e){
            printErr(e.getMessage());
        }
    }

    public static void viewAllScholarship() {
        int pageSize = 5;
        int currentPage = 0;

        label:
        while (true) {
            int offset = currentPage * pageSize;
            try {
                List<Scholarship> list = scholarshipService.getScholarshipsByPage(pageSize, offset);

                if (list.isEmpty() && currentPage == 0) {
                    printWarn("No scholarships found in the system.");
                    return;
                }

                Tables.renderAllScholarship(list);
                Tables.renderPage("Page: " + (currentPage + 1), "["+ GREEN+"N"+ RESET+"] Next | ["+ BLUE+"P"+ RESET+"] Previous | ["+ RED+"E"+ RESET+"] Exit");
                String choice = readString(PURPLE+"> Choose option : "+ RESET).toUpperCase().trim();

                switch (choice) {

                    case "N" -> {
                        if (list.size() == pageSize) currentPage++;
                        else printWarn("No more data.");
                    }

                    case "P" -> {
                        if (currentPage > 0) currentPage--;
                        else printWarn("Already on page 1.");
                    }

                    case "E" -> {
                        return;
                    }

                    default -> {
                        printErr("Invalid input: '" + choice + "'. Use N, P, E or an ID number.");
                    }
                }

            } catch (RuntimeException e) {
                printErr("Error: " + e.getMessage());
                break;
            }
        }
    }

    public static void updateScholarship() {
        int id = readInt(PURPLE + "> Enter Scholarship ID to update: " + RESET);
        Scholarship existing = scholarshipService.getScholarshipById(id);

        if (existing == null) {
            printErr("❌ Scholarship not found.");
            return;
        }

        renderAllScholarship(List.of(existing));
        printWarn("Press (Enter) to keep current data...");

        try {
            existing.setType(readOptionalString("New Type", existing.getType()));

            existing.setDescription(readOptionalString("New Description", existing.getDescription()));

            existing.setScholarship(readOptionalInt("New Scholarship (%)", existing.getScholarship()));

            existing.setFullPrice(readOptionalBigDecimal("New Full Price", existing.getFullPrice()));

            existing.setSponsor(readOptionalString("New Sponsor", existing.getSponsor()));

            existing.setDuration(readOptionalString("New Duration", existing.getDuration()));

            existing.setMaxQuota(readOptionalInt("New Max Quota", existing.getMaxQuota()));

            existing.setYearLevel(readOptionalInt("New Year Level", existing.getYearLevel()));

            existing.setWeek(readOptionalString("New Week Info", existing.getWeek()));

            String confirm = readString(YELLOW + "Apply these changes? (Y/N): " + RESET).toUpperCase().trim();

            if (confirm.equals("Y")) {
                printTrue("Enrollment updated successfully!");
            } else {
                printWarn("Update cancelled.");
            }

        } catch (Exception e) {
            printErr("Input Error: " + (e.getMessage() != null ? e.getMessage() : "Invalid data type"));
        }
    }

    public static void deleteScholarship() {
        int id = readInt("Enter ID to delete : ");

        try{

            Scholarship s = scholarshipService.getScholarshipById(id);
            if (s != null) {
                String confirm = readString("Are you sure to deleteScholarship ID " + id + "? (Y/n) : ");
                if (confirm.equalsIgnoreCase("y")) {
                    scholarshipService.deleteScholarship(id);
                    printTrue("Deleted.");
                }
            }}
        catch (RuntimeException e){
            printErr(e.getMessage());
        }

    }

    // enrollment

    public static void searchEnrollments() {
        String keyword = readString(PURPLE + "> Enter search keyword (Name/Major/School): " + RESET);

        int pageSize = 5;
        int currentPage = 0;

        while (true) {
            int offset = currentPage * pageSize;

            try {
                List<Enrollment> list = enrollmentService.searchEnrollments(keyword, pageSize, offset);

                if (list.isEmpty() && currentPage == 0) {
                    printWarn("No results found for: " + keyword);
                    return;
                }

                renderAllEnrollment(list);

                Tables.renderPage("Search: '" + keyword + "' | Page: " + (currentPage + 1),
                        "["+ GREEN +"N"+ RESET +"] Next | ["+ BLUE +"P"+ RESET +"] Previous | ["+ RED +"E"+ RESET +"] Exit Search");

                String choice = readString(PURPLE + "> Choose option : " + RESET).toUpperCase().trim();

                switch (choice) {
                    case "N" -> {
                        if (list.size() == pageSize) currentPage++;
                        else printWarn("No more search results.");
                    }
                    case "P" -> {
                        if (currentPage > 0) currentPage--;
                        else printWarn("You are on the first page of results.");
                    }
                    case "E" -> { return; }
                    default -> printErr("Invalid choice. Try again.");


                }
            } catch (Exception e) {
                printErr("An error occurred during search: " + e.getMessage());
                break;
            }
        }
    }

    public static void viewAllEnrollments() {
        int pageSize = 5;
        int currentPage = 0;

        while (true) {
            int offset = currentPage * pageSize;

            try {
                List<Enrollment> list = enrollmentService.getAllEnrollments(pageSize, offset);

                renderAllEnrollment(list);
                Tables.renderPage("Page: " + (currentPage + 1), "["+ GREEN+"N"+ RESET+"] Next | ["+ BLUE+"P"+ RESET+"] Previous | ["+ RED+"E"+ RESET+"] Exit");
                String choice = readString(PURPLE+"> Choose option : "+ RESET).toUpperCase().trim();

                switch (choice) {
                    case "N" -> {
                        if (list.size() == pageSize) currentPage++;
                        else printWarn("You've reached the end of the list.");
                    }
                    case "P" -> {
                        if (currentPage > 0) currentPage--;
                        else printWarn("You are on the first page.");
                    }
                    case "E" -> { return; }
                    default -> {
                        printErr("Invalid choice. Try again.");
                    }
                }
            } catch (Exception e) {
                printErr("An error occurred: " + e.getMessage());
                break;
            }
        }
    }

    public static void updateEnrollmentByAdmin() {
        int id = readInt(PURPLE + "> Enter Enrollment ID to update: " + RESET);
        Enrollment existing = enrollmentService.getEnrollmentById(id);

        if (existing == null) {
            printErr("❌ Enrollment ID not found.");
            return;
        }

        renderAllEnrollment(List.of(existing));
        printWarn("Press (Enter) to keep current value.");

        try {
            existing.setFullName(readOptionalString("Full Name", existing.getFullName()));
            existing.setGender(readOptionalGender("Gender", existing.getGender()));
            existing.setDob(readOptionalDate("Date of Birth (yyyy-MM-dd)", existing.getDob()));
            existing.setPhoneNumber(readOptionalPhoneNumber("Phone Number", existing.getPhoneNumber()));
            existing.setYearLevel(readOptionalInt("Year Level", existing.getYearLevel()));
            existing.setSchool(readOptionalString("School Name", existing.getSchool()));
            existing.setMajor(readOptionalString("Major", existing.getMajor()));
            String status = readOptionalString("Payment Status (PAID/PENDING)", existing.getPaymentStatus());
            existing.setPaymentStatus(status.toUpperCase());

            existing.setPaymentMethod(readOptionalString("Payment Method", existing.getPaymentMethod()));

            String confirm = readString(YELLOW + "Apply these changes? (Y/N): " + RESET).toUpperCase().trim();

            if (confirm.equals("Y")) {
                if (enrollmentService.updateEnrollment(existing)) {
                    printTrue("Enrollment updated successfully!");
                }
            } else {
                printWarn("Update cancelled.");
            }

        } catch (Exception e) {
            printErr("Input Error: " + e.getMessage());
        }
    }

    public static void deleteEnrollmentByAdmin() {
        int id = readInt(PURPLE + "> Enter Enrollment ID to delete: " + RESET);
        Enrollment existing = enrollmentService.getEnrollmentById(id);

        if (existing == null) {
            printErr("Enrollment ID not found.");
            return;
        }

        renderAllEnrollment(List.of(existing));

        String confirm = readString(RED + "❗ Are you sure you want to delete this record? (Y/N): " + RESET).toUpperCase().trim();

        if (confirm.equals("Y")) {
            if (enrollmentService.deleteEnrollment(id)) {
                printTrue("Enrollment ID " + id + " has been deleted.");
            } else {
                printErr("Failed to delete the record.");
            }
        } else {
            printWarn("Deletion cancelled.");
        }
    }

    /* end admin */


    /* user */

    public static void applyScholarship() {

        renderHeader("Apply Scholarship");
        viewAllScholarship();
        int sId;

        while (true) {
            sId = readInt("Enter Scholarship ID : ");

            if (scholarshipService.getScholarshipById(sId) == null) {
                continue;
            }// handle already in getScholarshipById

            if (enrollmentService.isAlreadyEnrolled(currentUser.getId(), sId)) {
                printErr("You have already applied for this scholarship! Choose another.");
                continue;
            }

            break;
        }

        try{
            String name = readString("Full Name : ");
            String gender = readGender("Gender (M/F) : ");
            LocalDate dob = readDate("DOB (YYYY-MM-DD) : ");
            String phone = readPhoneNumber("Phone Number");
            int year = readYear("Current Year Level : ");
            String school = readString("School Name : ");
            String major = readString("Major : ");
            String payMethod = readString("Payment Method (CASH/MOBILE) : ");

            Enrollment e = Enrollment.builder()
                .scholarshipId(sId)
                .userId(currentUser.getId())
                .fullName(name)
                .gender(gender)
                .dob(dob)
                .phoneNumber(phone)
                .yearLevel(year)
                .school(school)
                .major(major)
                .paymentMethod(payMethod)
                .build();

            enrollmentService.applyForScholarship(e);
            printTrue("Application submitted successfully!");
        }
        catch (RuntimeException e){
            printErr(e.getMessage());
        }
    }

    public static void viewOwnEnrollment() {

        List<Enrollment> list = enrollmentService.getMyEnrollments(currentUser.getId());
        if (list.isEmpty()) {
            printWarn("You have no active applications.");
            return;
        }
        renderViewOwnEnrollment(list);
    }

    public static void updateOwnEnrollment() {
        viewOwnEnrollment();
        int id = readInt(PURPLE + "> Enter Enrollment ID to update: " + RESET);

        try {
            Enrollment existing = enrollmentService.getEnrollmentById(id);

            // Security check: Ensure the enrollment exists and belongs to the logged-in user
            if (existing == null || !existing.getUserId().equals(currentUser.getId())) {
                printWarn("Enrollment not found or access denied.");
                return;
            }

            renderAllEnrollment(List.of(existing));
            printWarn("Press (Enter) to keep current data...");

            // Simply pass the current value. The helper handles 'Enter' to keep old data.
            existing.setFullName(readOptionalString("Enter new Name", existing.getFullName()));

            existing.setGender(readOptionalGender("Enter new Gender (M/F/Other)", existing.getGender()));

            existing.setDob(readOptionalDate("Enter new DOB (YYYY-MM-DD)", existing.getDob()));

            existing.setPhoneNumber(readOptionalPhoneNumber("Enter new Phone Number", existing.getPhoneNumber()));

            existing.setYearLevel(readOptionalInt("Enter new Year Level", existing.getYearLevel()));

            existing.setSchool(readOptionalString("Enter new School Name", existing.getSchool()));

            existing.setMajor(readOptionalString("Enter new Major", existing.getMajor()));

            existing.setPaymentMethod(readOptionalString("Enter new Payment Method", existing.getPaymentMethod()));

            String confirm = readString(YELLOW + "Apply changes? (Y/N): " + RESET).toUpperCase().trim();

            if (confirm.equals("Y")) {
                if (enrollmentService.updateEnrollment(existing)) {
                    printTrue("Your enrollment has been updated successfully!");
                } else {
                    printErr("Update failed at the database level.");
                }
            } else {
                printWarn("Update cancelled.");
            }

        } catch (Exception e) {
            printErr("Input Error: " + e.getMessage());
        }
    }

    public static void deleteOwnEnrollment() {
        viewOwnEnrollment();
        int id = readInt("Enter Enrollment ID to cancel: ");

        try {

            if (enrollmentService.cancelMyEnrollment(id, currentUser.getId())) {
                printTrue("Enrollment cancelled.");
            } else {
                printErr("Failed to cancel. Check ID.");
            }
        }
        catch (RuntimeException e){
            printErr(e.getMessage());
        }
    }

    /* end user */
}
