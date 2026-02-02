package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static utils.PrintUtils.*;
import static views.Colors.*;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer with basic type validation.
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println( RED +"Invalid input. Please enter a whole number." + RESET);
            }
        }
    }

    /**
     * Reads an integer within a specific range [min, max].
     */
    public static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int input = readInt(prompt);
            if (input >= min && input <= max) {
                return input;
            }
            System.out.println( RED + "Error: Input must be between " + min + " and " + max + RESET);
        }
    }

    /**
     * Reads a double with basic type validation.
     */
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a decimal number.");
            }
        }
    }


    public static void print(Object msg) {
        System.out.print(msg);
    }

    /**
     * Reads a non-empty string.
     */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    public static String readText(String message) {
        print(GREEN + ">>> "+message+ " : " + RESET);
        return scanner.nextLine().trim();
    }

    public static void readEnter(String message) {
        print(message);
        scanner.nextLine();
    }

    public static String readValidText(String message) {
        while (true) {
            String input = readText(message);
            if (!input.isEmpty()) return input;
            printErr("Input cannot be empty.");
        }
    }

    /**
     * Reads a yes/no confirmation.
     */
    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    /**
     * Reads a date in yyyy-MM-dd format with optional validation.
     */
    public static LocalDate readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt + " (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use YYYY-MM-DD (e.g., 2026-02-02).");
            }
        }
    }

    public static LocalDate readOptionalDate(String prompt, boolean mustBeInPast) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.print(prompt + " (yyyy-MM-dd) [Optional - Press Enter to skip]: ");
            String input = scanner.nextLine().trim();

            // Handle the "Optional" part
            if (input.isEmpty()) {
                return null;
            }

            try {
                LocalDate date = LocalDate.parse(input, formatter);

                // Optional Validation: Check if it's in the past
                if (mustBeInPast && date.isAfter(LocalDate.now())) {
                    System.out.println("Error: Date cannot be in the future.");
                    continue;
                }

                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use YYYY-MM-DD.");
            }
        }
    }

    /**
     * Optional String: Returns null if input is empty.
     */
    public static String readOptionalString(String prompt) {
        System.out.print(prompt + " (Leave blank to skip): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    /**
     * Optional Integer: Returns -1 (or any sentinel value) if empty.
     */
    public static Integer readOptionalInt(String prompt) {
        while (true) {
            System.out.print(prompt + " (Leave blank to skip): ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null; // Using Integer object allows null
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Reads a double. Returns null if the user leaves it blank.
     */
    public static Double readOptionalDouble(String prompt) {
        while (true) {
            System.out.print(prompt + " (Decimal) [Optional - Press Enter to skip]: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number (e.g., 12.5).");
            }
        }
    }
}