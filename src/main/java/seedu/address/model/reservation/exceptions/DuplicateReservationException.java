package seedu.address.model.reservation.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException() {
        super("Operation would result in duplicate persons");
    }
}
