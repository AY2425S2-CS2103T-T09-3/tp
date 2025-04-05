package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.UniqueReservationList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class GastroBook implements ReadOnlyGastroBook {

    private final UniqueReservationList reservations;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        reservations = new UniqueReservationList();
    }

    public GastroBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public GastroBook(ReadOnlyGastroBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the reservation list with {@code reservation}.
     * {@code reservations} must not contain duplicate reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations.setReservations(reservations);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyGastroBook newData) {
        requireNonNull(newData);

        setReservations(newData.getReservationList());
    }
    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reservations.contains(reservation);
    }

    /**
     * Adds a reservation to the address book.
     * The reservation must not already exist in the address book.
     */
    public void addReservation(Reservation p) {
        reservations.add(p);
    }

    /**
     * Replaces the given reservation {@code target} in the list with {@code editedReservation}.
     * {@code target} must exist in the address book.
     * The reservation identity of {@code editedReservation} must not be the same as another
     * existing reservation in the address book.
     */
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireNonNull(editedReservation);

        reservations.setReservation(target, editedReservation);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeReservation(Reservation key) {
        reservations.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", reservations)
                .toString();
    }

    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GastroBook)) {
            return false;
        }

        GastroBook otherGastroBook = (GastroBook) other;
        return reservations.equals(otherGastroBook.reservations);
    }

    @Override
    public int hashCode() {
        return reservations.hashCode();
    }
}
