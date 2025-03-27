package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a start time in a reservation.
 * Ensures that the time follows the valid 24-hour format: HHMM.
 */
public class StartTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should be in the form of HHMM.";
    public static final String VALIDATION_REGEX = "^(?:[01]\\d|2[0-3])[0-5]\\d$";

    public final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid Time.
     */
    public StartTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        value = time;
    }

    /**
     * Returns true if a given string is a time.
     */
    public static boolean isValidTime(String test) {
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
        if (!(other instanceof StartTime)) {
            return false;
        }

        StartTime otherTime = (StartTime) other;
        return value.equals(otherTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
