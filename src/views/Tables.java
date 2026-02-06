package views;

import model.Enrollment;
import model.Scholarship;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

import static utils.PrintUtils.*;
import static views.Colors.*;


public class Tables {
    public static void renderHeader(String messages){
        Table t = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER);
        t.addCell(PURPLE+ "   ***   " + messages.toUpperCase() + "   ***   " + RESET);
        System.out.println(t.render());
    }

    public static void renderStartMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER);
        t.addCell(CYAN + "   WELCOME TO ISTAD SCHOLARSHIP SYSTEM   " + RESET);
        t.addCell(BLUE + "   1. Login   " + RESET);
        t.addCell(BLUE + "   2. Sign Up   " + RESET);
        t.addCell(RED + "   0. Exit   " + RESET);
        System.out.println(t.render());
    }

    public static void renderPage(String message1, String message2){
        Table t = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        t.addCell(CYAN+message1+RESET);
        t.addCell(message2);
        System.out.println(t.render());
    }

    public static void renderAdminMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER);
        t.addCell(CYAN + "   ***   Admin Dashboard   ***   " + RESET);
        t.addCell(BLUE + "   1. Scholarship   " + RESET);
        t.addCell(BLUE + "   2. Enrollment   " + RESET);
        t.addCell(RED + "   0. LogOut   " + RESET);
        System.out.println(t.render());
    }

    public static void renderUserMenu(){

        Table t = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER);
        t.addCell(CYAN + "   ***   Student Dashboard   ***   " + RESET);
        t.addCell(BLUE + "   1. View Scholarship   " + RESET);
        t.addCell(BLUE + "   2. Search Scholarship   " + RESET);
        t.addCell(BLUE + "   3. Apply Scholarship   " + RESET);
        t.addCell(BLUE + "   4. View Own Enrollment   " + RESET);
        t.addCell(BLUE + "   5. Update Own Enrollment   " + RESET);
        t.addCell(BLUE + "   6. Delete Own Enrollment   " + RESET);
        t.addCell(RED + "   0. LogOut" + RESET);
        System.out.println(t.render());

    }

    public static void renderScholarshipMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER);
        t.addCell(CYAN + "   *** Scholarship Management ***   " + RESET);
        t.addCell(BLUE + "   1. Create Scholarship   " + RESET);
        t.addCell(BLUE + "   2. View All Scholarship   " + RESET);
        t.addCell(BLUE + "   3. Search Scholarship   " + RESET);
        t.addCell(BLUE + "   4. Update Scholarship   " + RESET);
        t.addCell(BLUE + "   5. Delete Scholarship   " + RESET);
        t.addCell(RED + "   0. Back" + RESET);
        System.out.println(t.render());
    }

    public static void renderEnrollmentMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_BOX_HEAVY_BORDER);
        t.addCell(CYAN + "   *** Enrollment Management ***   " + RESET);
        t.addCell(BLUE + "   1. Create Enrollment   " + RESET);
        t.addCell(BLUE + "   2. View All Enrollment   " + RESET);
        t.addCell(BLUE + "   3. Search Enrollment   " + RESET);
        t.addCell(BLUE + "   4. Update Enrollment   " + RESET);
        t.addCell(BLUE + "   5. Delete Enrollment   " + RESET);
        t.addCell(RED + "   0. Back   " + RESET);
        System.out.println(t.render());
    }

    public static void renderSearchScholarship(Scholarship s) {

        Table t = new Table(10, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        t.addCell("Id");
        t.addCell("Type");
        t.addCell("Description");
        t.addCell("Scholarship");
        t.addCell("Full Price");
        t.addCell("Sponsor");
        t.addCell("Duration");
        t.addCell("Quota");
        t.addCell("Year");
        t.addCell("Status");

        t.addCell(String.valueOf(s.getId()));
        t.addCell(s.getType());
        t.addCell(s.getDescription() != null ? s.getDescription() : "N/A");
        t.addCell(s.getScholarship() + "%");
        t.addCell("$" + s.getFullPrice());
        t.addCell(s.getSponsor());
        t.addCell(s.getDuration());
        t.addCell(String.valueOf(s.getMaxQuota()));
        t.addCell(String.valueOf(s.getYearLevel()));
        t.addCell(s.getIsEnabled() ? "Active" : "Disabled");

        System.out.println(t.render());
    }

    public static void renderAllScholarship(List<Scholarship> scholarships) {
        Table t = new Table(11, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        t.addCell(BLUE+"Id");
        t.addCell(BLUE+"Type"+RESET);
        t.addCell(BLUE+"Description"+RESET);
        t.addCell(BLUE+"Scholarship"+RESET);
        t.addCell(BLUE+"Full Price"+RESET);
        t.addCell(BLUE+"Sponsor"+RESET);
        t.addCell(BLUE+"Duration"+RESET);
        t.addCell(BLUE+"Quota"+RESET);
        t.addCell(BLUE+"Week"+RESET);
        t.addCell(BLUE+"Year"+RESET);
        t.addCell(BLUE+"Status"+RESET);

        for (Scholarship s : scholarships) {
            t.addCell(PURPLE + s.getId() + RESET);
            t.addCell(GREEN + s.getType()+RESET);
            t.addCell(s.getDescription());
            t.addCell(GREEN + s.getScholarship() + RESET);
            t.addCell(RED + s.getFullPrice().toString() + RESET);
            t.addCell(CYAN + s.getSponsor() + RESET);
            t.addCell(YELLOW + s.getDuration() + RESET);
            t.addCell(YELLOW + s.getMaxQuota() +RESET);
            t.addCell(CYAN + s.getWeek() + RESET);
            t.addCell(PURPLE + s.getYearLevel() + RESET);
            t.addCell(s.getIsEnabled() ? GREEN+"Active"+RESET : RED+"Disabled"+RESET);
        }

        System.out.println(t.render());
    }

    public static void renderViewOwnEnrollment(List<Enrollment> list) {
        if (list == null || list.isEmpty()) {
            printErr(YELLOW + "No enrollment records found." + RESET);
            return;
        }

        Table t = new Table(11, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        t.addCell("ID");
        t.addCell("Scholar ID");
        t.addCell("Full Name");
        t.addCell("Gen");
        t.addCell("DOB");
        t.addCell("Phone");
        t.addCell("Year");
        t.addCell("School");
        t.addCell("Major");
        t.addCell("Status");
        t.addCell("Payment");

        for (Enrollment e : list) {
            t.addCell(e.getId().toString());
            t.addCell(e.getScholarshipId().toString());
            t.addCell(e.getFullName());
            t.addCell(e.getGender());
            t.addCell(e.getDob().toString());
            t.addCell(e.getPhoneNumber());
            t.addCell(e.getYearLevel().toString());
            t.addCell(e.getSchool());
            t.addCell(e.getMajor());

            String statusColor = "PENDING".equalsIgnoreCase(e.getPaymentStatus()) ? YELLOW : GREEN;
            t.addCell(statusColor + e.getPaymentStatus() + RESET);

            t.addCell(e.getPaymentMethod());
        }

        System.out.println(t.render());
    }

    public static void renderAllEnrollment(List<Enrollment> list) {
        if (list.isEmpty()) {
            printWarn("No enrollment records found for this page.");
            return;
        }

        Table table = new Table(12, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        String[] headers = {
                "ID", "USER ID", "SCHOLAR ID", "STUDENT NAME", "GENDER",
                "DOB", "PHONE", "YEAR", "SCHOOL", "MAJOR", "STATUS", "METHOD"
        };

        for (String header : headers) {
            table.addCell(" " + header);
        }

        // Add Rows from the List
        for (Enrollment e : list) {
            table.addCell(String.valueOf(e.getId()));
            table.addCell(String.valueOf(e.getUserId()));
            table.addCell(String.valueOf(e.getScholarshipId()));
            table.addCell(e.getFullName());
            table.addCell(e.getGender());
            Object dob = e.getDob();
            table.addCell(dob != null ? dob.toString() : "N/A");
            table.addCell(e.getPhoneNumber());
            table.addCell(String.valueOf(e.getYearLevel()));
            table.addCell(truncate(e.getSchool(), 15));
            table.addCell(truncate(e.getMajor(), 15));

            // Color coding for Status
            String status = e.getPaymentStatus().toUpperCase();
            if (status.contains("PAID")) {
                table.addCell(Colors.GREEN + status + Colors.RESET);
            } else {
                table.addCell(Colors.YELLOW + status + Colors.RESET);
            }

            table.addCell(e.getPaymentMethod() != null ? e.getPaymentMethod() : "CASH");
        }

        System.out.println(table.render());
    }

    private static String truncate(String text, int size) {
        if (text == null) return "N/A";
        return text.length() > size ? text.substring(0, size - 3) + ".." : text;
    }
}
