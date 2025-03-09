package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a start date in a reservation.
 * Ensures that the date follows the valid format: DD/MM/YYYY with leading zeros for single digits.
 */
public class StartDate {
    /** Message indicating the constraints for a valid date format. */
    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in the form of DD/MM/YYYY, with leading zeros for single digits.";

    /** Regular expression that defines a valid date format (DD/MM/YYYY). */
    public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

    /** The date value stored as a string. */
    public final String value;

    /**
     * Constructs a {@code StartDate}.
     *
     * @param date A valid date in the format DD/MM/YYYY.
     * @throws NullPointerException if {@code date} is null.
     * @throws IllegalArgumentException if {@code date} does not match the valid format.
     */
    public StartDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if the given string is a valid date.
     *
     * @param test The date string to validate.
     * @return true if {@code test} matches the required format, false otherwise.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the string representation of the date.
     *
     * @return The date value as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this start date is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if both objects are of type {@code StartDate} and have the same value.
     */
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

    /**
     * Returns the hash code of this start date.
     *
     * @return The hash code of the stored date value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
