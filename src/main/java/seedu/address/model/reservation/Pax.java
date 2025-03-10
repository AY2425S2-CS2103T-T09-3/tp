
package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of people (pax) in a reservation.
 * Ensures that the value is a valid positive integer greater than zero.
 */
public class Pax {
    public static final String MESSAGE_CONSTRAINTS =
            "Number of people should be a whole number greater than 0.";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";

    public final String value;

    /**
     * Constructs a {@code Pax}.
     *
     * @param pax A valid number of people.
     */
    public Pax(String pax) {
        requireNonNull(pax);
        checkArgument(isValidPax(pax), MESSAGE_CONSTRAINTS);
        value = pax;
    }

    /**
     * Returns true if a given string is a valid number of people.
     */
    public static boolean isValidPax(String test) {
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
        if (!(other instanceof Pax)) {
            return false;
        }

        Pax otherPax = (Pax) other;
        return value.equals(otherPax.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
