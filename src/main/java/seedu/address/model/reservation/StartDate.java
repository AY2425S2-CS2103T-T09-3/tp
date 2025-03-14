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
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid Date.
     */
    public StartDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX) && isValidDateRange(test);
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

    private static boolean isValidDateRange(String userInputDate) {
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
