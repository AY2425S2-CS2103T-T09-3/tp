package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of people (pax) in a reservation.
 * Ensures that the value is a valid positive integer greater than zero.
 */
public class Pax {
    /** Message indicating the constraints for a valid number of people. */
    public static final String MESSAGE_CONSTRAINTS =
            "Number of people should be a whole number greater than 0.";

    /** Regular expression that defines a valid pax format (only positive integers). */
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";

    /** The number of people in the reservation, stored as a string. */
    public final String value;

    /**
     * Constructs a {@code Pax} instance.
     *
     * @param pax A valid number of people.
     * @throws NullPointerException if {@code pax} is null.
     * @throws IllegalArgumentException if {@code pax} does not match the valid format.
     */
    public Pax(String pax) {
        requireNonNull(pax);
        checkArgument(isValidPax(pax), MESSAGE_CONSTRAINTS);
        value = pax;
    }

    /**
     * Returns true if the given string is a valid number of people.
     *
     * @param test The number of people as a string.
     * @return true if {@code test} matches the required format, false otherwise.
     */
    public static boolean isValidPax(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the string representation of the number of people.
     *
     * @return The pax value as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this pax value is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if both objects are of type {@code Pax} and have the same value.
     */
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

    /**
     * Returns the hash code of this pax value.
     *
     * @return The hash code of the stored pax value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
