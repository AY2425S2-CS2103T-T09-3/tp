package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a reservation duration in the system.
 * Ensures that the duration follows the specified constraints.
 * Duration must be a positive number in half-hour intervals (e.g., 0.5, 1, 1.5, 2, etc.).
 */
public class Duration {
    public static final String MESSAGE_CONSTRAINTS =
            "Duration should be positive intervals of half an hour greater than 0, given in hours.";
    public static final String VALIDATION_REGEX = "^(0|0\\.5|[1-9]\\d*(\\.5)?)$";

    public final String value;

    /**
     * Constructs a {@code Duration}.
     *
     * @param duration A valid Duration.
     */
    public Duration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        value = duration;
    }

    /**
     * Returns true if a given string is a valid duration.
     */
    public static boolean isValidDuration(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

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

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
