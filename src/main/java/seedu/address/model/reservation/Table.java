package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a table in a reservation system.
 * Ensures that the table number follows the valid format:
 * a capital letter followed by 1 to 3 digits.
 */
public class Table {
    /** Message indicating the constraints for a valid table number format. */
    public static final String MESSAGE_CONSTRAINTS =
            "Table number should start with a capital letter, followed by 1-3 numbers.";

    /** Regular expression that defines a valid table format (e.g., A1, B12, C123). */
    public static final String VALIDATION_REGEX = "^[A-Z]\\d{1,3}$";

    /** The table number stored as a string. */
    public final String value;

    /**
     * Constructs a {@code Table}.
     *
     * @param table A valid table identifier.
     * @throws NullPointerException if {@code table} is null.
     * @throws IllegalArgumentException if {@code table} does not match the valid format.
     */
    public Table(String table) {
        requireNonNull(table);
        checkArgument(isValidTable(table), MESSAGE_CONSTRAINTS);
        value = table;
    }

    /**
     * Returns true if the given string is a valid table identifier.
     *
     * @param test The table identifier string to validate.
     * @return true if {@code test} matches the required format, false otherwise.
     */
    public static boolean isValidTable(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the string representation of the table.
     *
     * @return The table identifier as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if this table identifier is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if both objects are of type {@code Table} and have the same value.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Table)) {
            return false;
        }

        Table otherTable = (Table) other;
        return value.equals(otherTable.value);
    }

    /**
     * Returns the hash code of this table identifier.
     *
     * @return The hash code of the stored table value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
