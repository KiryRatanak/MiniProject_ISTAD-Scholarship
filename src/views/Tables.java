package views;

import model.Scholarship;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

import static utils.PrintUtils.*;
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

    public static void renderAdminMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        t.addCell(GREEN + "Admin Dashboard" + RESET);
        t.addCell(BLUE + "1. Scholarship" + RESET);
        t.addCell(BLUE + "2. Enrollment" + RESET);
        t.addCell(RED + "0. Back" + RESET);
        System.out.println(t.render());
    }

    public static void renderScholarshipMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        t.addCell(GREEN + "Scholarship Management" + RESET);
        t.addCell(BLUE + "1. Create Scholarship" + RESET);
        t.addCell(BLUE + "2. View All Scholarship" + RESET);
        t.addCell(BLUE + "3. Search Scholarship" + RESET);
        t.addCell(BLUE + "4. Update Scholarship" + RESET);
        t.addCell(BLUE + "5. Delete Scholarship" + RESET);
        t.addCell(RED + "0. Back" + RESET);
        System.out.println(t.render());
    }

    public static void renderEnrollmentMenu(){
        Table t = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        t.addCell(GREEN + "Enrollment Management" + RESET);
        t.addCell(BLUE + "1. Create Enrollment" + RESET);
        t.addCell(BLUE + "2. View All Enrollment" + RESET);
        t.addCell(BLUE + "3. Search Enrollment" + RESET);
        t.addCell(BLUE + "4. Update Enrollment" + RESET);
        t.addCell(BLUE + "5. Delete Enrollment" + RESET);
        t.addCell(RED + "0. Back" + RESET);
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

        for (Scholarship s : scholarships) {
            t.addCell(String.valueOf(s.getId()));
            t.addCell(s.getType());
            t.addCell(s.getDescription());
            t.addCell(String.valueOf(s.getScholarship()));
            t.addCell(s.getFullPrice().toString());
            t.addCell(s.getSponsor());
            t.addCell(s.getDuration());
            t.addCell(String.valueOf(s.getMaxQuota()));
            t.addCell(String.valueOf(s.getYearLevel()));
            t.addCell(s.getIsEnabled() ? "Active" : "Disabled");
        }

        System.out.println(t.render());
    }
}
