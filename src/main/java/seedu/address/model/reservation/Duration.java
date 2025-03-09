package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a reservation duration in the system.
 * Ensures that the duration follows the specified constraints.
 * Duration must be a positive number in half-hour intervals (e.g., 0.5, 1, 1.5, 2, etc.).
 */
public class Duration {
    /** Message indicating the constraints for a valid duration. */
    public static final String MESSAGE_CONSTRAINTS =
            "Duration should be positive intervals of half an hour greater than 0, given in hours.";

    /** Regular expression that defines a valid duration format. */
    public static final String VALIDATION_REGEX = "^(0|0\\.5|[1-9]\\d*(\\.5)?)$";

    /** The duration value stored as a string. */
    public final String value;

    /**
     * Constructs a {@code Duration}.
     *
     * @param duration A valid duration string.
     * @throws NullPointerException if {@code duration} is null.
     * @throws IllegalArgumentException if {@code duration} does not match the valid format.
     */
    public Duration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        value = duration;
    }

    /**
     * Returns true if the given string is a valid duration.
     *
     * @param test The duration string to validate.
     * @return true if {@code test} matches the required duration format, false otherwise.
     */
    public static boolean isValidDuration(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the string representation of the duration.
     *
     * @return The duration value as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this duration is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if both objects are of type {@code Duration} and have the same value.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Duration)) {
            return false;
        }

        Duration otherDuration = (Duration) other;
        return value.equals(otherDuration.value);
    }

    /**
     * Returns the hash code of this duration.
     *
     * @return The hash code of the stored duration value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
