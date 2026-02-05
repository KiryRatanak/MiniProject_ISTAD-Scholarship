package utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static utils.PrintUtils.*;
import static views.Colors.*;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printErr( "Invalid input. Please enter number.");
            }
        }
    }

    public static BigDecimal readBigDecimal(String label) {
        while (true) {
            System.out.print(label);
            String input = scanner.nextLine();
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                printErr("Invalid format! Please enter a numeric value (e.g., 1500.00).");
            }
        }
    }

    public static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int input = readInt(prompt);
            if (input >= min && input <= max) {
                return input;
            }
            System.out.println( RED + "Error: Input must be between " + min + " and " + max + RESET);
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                printErr("Invalid input. Please enter a decimal number.");
            }
        }
    }


    public static void print(Object msg) {
        System.out.print(msg);
    }

    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            printErr("Input cannot be empty.");
        }
    }

    public static BigDecimal readOptionalBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null; // User skipped
            }

            try {
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number! Please enter a valid decimal (e.g., 99.99).");
            }
        }
    }

    public static boolean readOptionalBoolean(String label, boolean defaultValue) {
        System.out.print(label + " (y/n, default " + (defaultValue ? "y" : "n") + "): ");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.isEmpty()) {
            return defaultValue;
        }
        return input.startsWith("y");
    }

    public static void readEnter(String message) {
        print(message);
        scanner.nextLine();
    }

    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Please enter 'Y' or 'n'.");
        }
    }

    public static LocalDate readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt + " (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                printErr("Invalid format. Please use YYYY-MM-DD (e.g., 2026-02-02).");
            }
        }
    }

    public static LocalDate readOptionalDate(String prompt, boolean mustBeInPast) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                LocalDate date = LocalDate.parse(input, formatter);

                if (mustBeInPast && date.isAfter(LocalDate.now())) {
                    printErr("Error: Date cannot be in the future.");
                    continue;
                }

                return date;
            } catch (DateTimeParseException e) {
                printErr("Invalid format. Please use YYYY-MM-DD.");
            }
        }
    }

    public static String readOptionalString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    public static Integer readOptionalInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null; // Using Integer object allows null
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printErr("Invalid input. Please enter a number.");
            }
        }
    }

    public static Double readOptionalDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                printErr("Invalid input. Please enter a valid decimal number (e.g., 12.5).");
            }
        }
    }
}