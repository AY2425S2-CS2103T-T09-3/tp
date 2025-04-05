package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a start date in a reservation.
 * Ensures that the date follows the valid format: DD/MM/YYYY with leading zeros for single digits.
 */
public class StartDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should only be today or tomorrow in the form of DD/MM/YYYY, with leading zeros for single digits.";
    public static final String VALIDATION_REGEX = "^(?:(?:31/(?:0[13578]|1[02]))|"
            + "(?:(?:29|30)/(?:0[13-9]|1[0-2]))|"
            + "(?:29/02/(?:(?:(?:1[6-9]|[2-9]\\d)"
            + "(?:0[48]|[2468][048]|[13579][26]))"
            + "|(?:(?:16|[2468][048]|[3579][26])00)))|"
            + "(?:0[1-9]|1\\d|2[0-8])/(?:0[1-9]|1[0-2]))/(?:1\\d{3}|[2-9]\\d{3})$";

    // Flag to bypass date range validation during sample data initialization
    private static boolean bypassDateRangeValidation = false;

    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid Date.
     */
    public StartDate(String date) {
        requireNonNull(date);
        // Always check format validity
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);

        // Check date range only if bypass is not enabled to
        if (!bypassDateRangeValidation) {
            checkArgument(isValidDateRange(date), MESSAGE_CONSTRAINTS);
        }

        value = date;
    }

    /**
     * Enables bypass for date range validation (for sample data initialization).
     */
    public static void enableBypassForSampleData() {
        bypassDateRangeValidation = true;
    }

    /**
     * Disables bypass for date range validation for sample data injection.
     */
    public static void disableBypassForSampleData() {
        bypassDateRangeValidation = false;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns this StartDate's value (in "dd/MM/yyyy" format) as a LocalDate object.
     */
    public LocalDate toLocalDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(value, dateFormatter);
    }

    @Override
    public String toString() {
        return value;
    }

    public String toWithoutSlashString() {
        return value.replace("/", "");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StartDate)) {
            return false;
        }

        StartDate otherDate = (StartDate) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Validates whether the given date string corresponds to today's or tomorrow's date.
     *
     * @param userInputDate The date string to validate, expected in the format "dd/MM/yyyy".
     * @return {@code true} if the date matches today's or tomorrow's date, otherwise {@code false}.
     */
    public static boolean isValidDateRange(String userInputDate) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToday = date.format(formatter);
        String dateTomorrow = date.plusDays(1).format(formatter);

        if (!Objects.equals(userInputDate, dateToday) && !Objects.equals(userInputDate, dateTomorrow)) {
            return false;
        }
        return true;
    }
}
