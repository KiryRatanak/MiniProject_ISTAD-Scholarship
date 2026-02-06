package views;

import model.Enrollment;
import model.Scholarship;
import service.EnrollmentService;
import service.ScholarshipService;
import service.impl.EnrollmentServiceImpl;
import service.impl.ScholarshipServiceImpl;
import utils.InputUtils;
import utils.PrintUtils;

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
    private static final Enrollment enrollment = new Enrollment();

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
//                case 2 -> viewAllEnrollment();
//                case 3 -> searchEnrollmentById();
//                case 4 -> updateStudentInfoByAdmin();
//                case 5 -> deleteEnrollment();
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
                System.out.println("✅ Found Result :");
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
                    PrintUtils.printWarn("No scholarships found in the system.");
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
        int id = readInt("Enter ID to update : ");
        printWarn("Press (Enter) to keep data...");
        Scholarship existing = scholarshipService.getScholarshipById(id);
        if (existing == null) {
            printErr("Scholarship not found.");
            return;
        }

        try {

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
        }
        catch (RuntimeException e){
            printErr(e.getMessage());
        }

        scholarshipService.updateScholarship(existing);
        printTrue("Scholarship updated!");
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

//    public static void viewAllEnrollment() {
//        List<Enrollment> allData = enrollmentService.getAllEnrollments();
//        if (allData.isEmpty()) {
//            printWarn("No enrollment records found.");
//            return;
//        }
//        renderViewOwnEnrollment(allData);
//    }
//
//    public static void searchEnrollmentById() {
//        int id = readInt("Search Enrollment ID: ");
//        Enrollment e = enrollmentService.getEnrollmentById(id);
//        if (e != null) {
//            renderAllEnrollment(e);
//        } else {
//            printErr("❌ ID not found.");
//        }
//    }
//
//    public static void deleteEnrollment() {
//        int id = readInt("Enter ID to Delete: ");
//        Enrollment e = enrollmentService.getEnrollmentById(id);
//
//        if (e != null) {
//            renderDetailedEnrollment(e);
//            System.out.print(RED + "PERMANENTLY DELETE this record? (Y/N): " + RESET);
//            if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
//                enrollmentService.deleteEnrollment(id);
//                printTrue("✅ Record deleted.");
//            }
//        }
//    }
//
//    public static void updateStudentInfoByAdmin() {
//        int id = readInt("Enter Enrollment ID to update student info: ");
//        Enrollment existing = enrollmentService.getEnrollmentById(id);
//
//        if (existing == null) {
//            printErr("❌ Enrollment not found.");
//            return;
//        }
//
//        // Show the full record so Admin knows who they are editing
//        renderDetailedEnrollment(existing);
//        printWarn("Press (Enter) to keep current value...");
//
//        // Update Full Name
//        String name = readOptionalString("New Full Name: ", existing.getFullName());
//        if (name != null) existing.setFullName(name);
//
//        // Update Gender
//        String gender = readOptionalGender("New Gender", existing.getGender());
//        if (gender != null) existing.setGender(gender);
//
//        // Update Phone (Supports +855 format)
//        String phone = readOptionalPhoneNumber("New Phone Number", existing.getPhoneNumber());
//        if (phone != null) existing.setPhoneNumber(phone);
//
//        // Update Year Level (0-5)
//        Integer year = readOptionalInt("New Year Level: ");
//        if (year != null) existing.setYearLevel(year);
//
//        // Update Major
//        String major = readOptionalString("New Major: ", existing.getMajor());
//        if (major != null) existing.setMajor(major);
//
//        // Update School
//        String school = readOptionalString("New School Name: ", existing.getSchool());
//        if (school != null) existing.setSchool(school);
//
//        // Final Save
//        if (enrollmentService.updateMyEnrollment(existing)) {
//            printTrue("✅ Student information updated successfully by Admin!");
//        }
//    }

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
        int id = readInt("Enter Enrollment ID to update: ");

        try {

            Enrollment existing = enrollmentService.getEnrollmentById(id);

            if (existing == null || !existing.getUserId().equals(currentUser.getId())) {
                printWarn("Enrollment not found or access denied.");
                return;
            }

            printWarn("Press (Enter) to keep current data...");

            printCurrent("(Current : " + existing.getFullName() + ")");
            String name = readOptionalString("Enter new Name : ");
            if (name != null) existing.setFullName(name);

            printCurrent("(Current : " + existing.getGender() + ")");
            String gender = readOptionalString("Enter new Gender (M/F) : ");
            if (gender != null) existing.setGender(gender);

            printCurrent("(Current : " + existing.getDob() + ")");
            LocalDate dob = readOptionalDate("Enter new DOB (YYYY-MM-DD) : ");
            if (dob != null) existing.setDob(dob);

            printCurrent("(Current : " + existing.getPhoneNumber() + ")");
            String phone = readOptionalString("Enter new Phone Number : ");
            if (phone != null) existing.setPhoneNumber(phone);

            printCurrent("(Current : " + existing.getYearLevel() + ")");
            Integer year = readOptionalInt("Enter new Year Level : ");
            if (year != null) existing.setYearLevel(year);

            printCurrent("(Current : " + existing.getSchool() + ")");
            String school = readOptionalString("Enter new School Name : ");
            if (school != null) existing.setSchool(school);

            printCurrent("(Current : " + existing.getMajor() + ")");
            String major = readOptionalString("Enter new Major : ");
            if (major != null) existing.setMajor(major);

            printCurrent("(Current : " + existing.getPaymentMethod() + ")");
            String payMethod = readOptionalString("Enter new Payment Method : ");
            if (payMethod != null) existing.setPaymentMethod(payMethod);

            if (enrollmentService.updateMyEnrollment(existing)) {
                printTrue("Enrollment updated successfully!");
            }

        }
        catch (RuntimeException e){
            printErr(e.getMessage());
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
