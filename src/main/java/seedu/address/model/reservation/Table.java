package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a table in a reservation system.
 * Ensures that the table number follows the valid format:
 * a capital letter followed by 1 to 3 digits.
 */
public class Table {
    public static final String MESSAGE_CONSTRAINTS =
            "Table number should start with a capital letter, followed by 1-3 numbers.";
    public static final String VALIDATION_REGEX = "^[A-Z]\\d{1,3}$";

    public final String value;

    /**
     * Constructs a {@code Table}.
     *
     * @param table A valid Table.
     */
    public Table(String table) {
        requireNonNull(table);
        checkArgument(isValidTable(table), MESSAGE_CONSTRAINTS);
        value = table;
    }

    /**
     * Returns true if a given string is a table.
     */
    public static boolean isValidTable(String test) {
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
        if (!(other instanceof Table)) {
            return false;
        }

        Table otherTable = (Table) other;
        return value.equals(otherTable.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
