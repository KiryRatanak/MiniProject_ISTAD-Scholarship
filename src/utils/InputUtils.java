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

    public static int readYear(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int year = Integer.parseInt(scanner.nextLine());

                if (year >= 0 && year <= 5) {
                    return year;
                } else {
                    printErr("Please enter a year between 0 and 5.");
                }
            } catch (NumberFormatException e) {
                printErr("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static String readPhoneNumber(String prompt) {
        while (true) {
            System.out.print(prompt + " (e.g. +855 ...): ");
            String input = scanner.nextLine().trim();

            if (input.length() >= 12 && input.length() <= 20) {

                if (input.matches("^\\+[\\d\\s]+")) {
                    return input;
                } else {
                    printErr("Format must start with '+' and contain only numbers/spaces.");
                }

            } else {
                printErr("Invalid Input.");
            }
        }
    }

    public static String readOptionalPhoneNumber(String prompt, String current) {
        while (true) {
            printCurrent("(Current : " + current + ")");
            System.out.print(prompt + " (e.g. +855... ): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) return null;

            if (input.length() >= 9 && input.length() <= 17) {
                if (input.matches("^\\+[\\d\\s]+")) {
                    return input;
                } else {
                    printErr("Format must start with '+' and contain only numbers/spaces.");
                }
            } else {
                printErr("Length must be between 9 and 17 characters.");
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

    public static void readEnter(String message) {
        print(message);
        scanner.nextLine();
    }

    public static LocalDate readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                printErr("Invalid format. Please use YYYY-MM-DD (e.g., 2001-01-01).");
            }
        }
    }

    public static LocalDate readOptionalDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                printErr("Invalid Format! Use YYYY-MM-DD.");
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
            if (input.isEmpty()) return null;
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printErr("Invalid input. Please enter a number.");
            }
        }
    }

    public static String readGender(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("M") || input.equals("F")) {
                return input;
            }
            printErr("Invalid Gender! Please enter 'M' for Male or 'F' for Female.");
        }
    }

    public static String readOptionalGender(String prompt, String current) {
        while (true) {
            printCurrent("(Current : " + current + ")");
            System.out.print(prompt + " (Enter to skip): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty()) return null;

            if (input.equals("M") || input.equals("F")) {
                return input;
            }
            printErr("Invalid! Use 'M', 'F', or press Enter.");
        }
    }
}