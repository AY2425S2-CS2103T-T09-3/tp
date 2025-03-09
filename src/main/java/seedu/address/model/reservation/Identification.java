package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an identification number in the reservation system.
 * Ensures that the ID consists only of integers.
 */
public class Identification {
    /** Message indicating the constraints for a valid identification number. */
    public static final String MESSAGE_CONSTRAINTS =
            "Identification should only contain integers";

    /** Regular expression that defines a valid identification format. */
    public static final String VALIDATION_REGEX = "^-?\\d+$";

    /** The identification value stored as a string. */
    public final String value;

    /**
     * Constructs an {@code Identification}.
     *
     * @param id A valid identification string.
     * @throws NullPointerException if {@code id} is null.
     * @throws IllegalArgumentException if {@code id} does not match the valid format.
     */
    public Identification(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Returns true if the given string is a valid identification number.
     *
     * @param test The identification string to validate.
     * @return true if {@code test} matches the required format, false otherwise.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the string representation of the identification number.
     *
     * @return The identification value as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this identification is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if both objects are of type {@code Identification} and have the same value.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Identification)) {
            return false;
        }

        Identification otherId = (Identification) other;
        return value.equals(otherId.value);
    }

    /**
     * Returns the hash code of this identification.
     *
     * @return The hash code of the stored identification value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
