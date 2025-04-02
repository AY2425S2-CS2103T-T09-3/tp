package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.reservation.exceptions.DuplicateReservationException;
import seedu.address.model.reservation.exceptions.ReservationNotFoundException;

/**
 * A list of reservations that enforces uniqueness between its elements and does not allow nulls.
 * A reservation is considered unique by comparing using {@code Person#isSamePerson(Person)}.
 * As such, adding and updating of persons uses
 * Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reservation#isSameReservation(Reservation)
 */
public class UniqueReservationList implements Iterable<Reservation> {

    private final ObservableList<Reservation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reservation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent reservation as the given argument.
     */
    public boolean contains(Reservation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameReservation);
    }

    /**
     * Adds a reservation to the list.
     * The reservation must not already exist in the list.
     */
    public void add(Reservation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReservationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the reservation {@code target} in the list with {@code editedReservation}.
     * {@code target} must exist in the list.
     * The reservation identity of {@code editedReservation} must not be the same as another existing
     * reservation in the list.
     */
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReservationNotFoundException();
        }

        if (!target.isSameReservation(editedReservation) && contains(editedReservation)) {
            throw new DuplicateReservationException();
        }

        internalList.set(index, editedReservation);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Reservation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReservationNotFoundException();
        }
    }

    public void setReservations(UniqueReservationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code reservations}.
     * {@code reservations} must not contain duplicate reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        requireAllNonNull(reservations);
        if (!reservationsAreUnique(reservations)) {
            throw new DuplicateReservationException();
        }

        internalList.setAll(reservations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reservation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Reservation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueReservationList)) {
            return false;
        }

        UniqueReservationList otherUniqueReservationList = (UniqueReservationList) other;
        return internalList.equals(otherUniqueReservationList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code reservations} contains only unique persons.
     */
    private boolean reservationsAreUnique(List<Reservation> reservations) {
        for (int i = 0; i < reservations.size() - 1; i++) {
            for (int j = i + 1; j < reservations.size(); j++) {
                if (reservations.get(i).isSameReservation(reservations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
