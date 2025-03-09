package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a start time in a reservation.
 * Ensures that the time follows the valid 24-hour format: HHMM.
 */
public class StartTime {
    /** Message indicating the constraints for a valid time format. */
    public static final String MESSAGE_CONSTRAINTS =
            "Time should be in the form of HHMM.";

    /** Regular expression that defines a valid time format (HHMM in 24-hour format). */
    public static final String VALIDATION_REGEX = "^(?:[01]\\d|2[0-3])[0-5]\\d$";

    /** The time value stored as a string. */
    public final String value;

    /**
     * Constructs a {@code StartTime}.
     *
     * @param time A valid time in the format HHMM.
     * @throws NullPointerException if {@code time} is null.
     * @throws IllegalArgumentException if {@code time} does not match the valid format.
     */
    public StartTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        value = time;
    }

    /**
     * Returns true if the given string is a valid time.
     *
     * @param test The time string to validate.
     * @return true if {@code test} matches the required format, false otherwise.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the string representation of the time.
     *
     * @return The time value as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this start time is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if both objects are of type {@code StartTime} and have the same value.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StartTime)) {
            return false;
        }

        StartTime otherTime = (StartTime) other;
        return value.equals(otherTime.value);
    }

    /**
     * Returns the hash code of this start time.
     *
     * @return The hash code of the stored time value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
