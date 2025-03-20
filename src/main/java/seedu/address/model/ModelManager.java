package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reservation.Reservation;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Reservation> filteredReservations;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredReservations = new FilteredList<>(this.addressBook.getReservationList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return addressBook.hasReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation target) {
        addressBook.removeReservation(target);
    }

    @Override
    public void addReservation(Reservation reservation) {
        addressBook.addReservation(reservation);
        updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
    }

    @Override
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        addressBook.setReservation(target, editedReservation);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return filteredReservations;
    }

    @Override
    public ObservableList<Reservation> getOverallReservationList() {
        updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return filteredReservations;
    }

    @Override
    public void updateFilteredReservationList(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        filteredReservations.setPredicate(predicate);
    }

    @Override
    public void filterReservationsForToday(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        Predicate<Reservation> todayPredicate = ReservationsFilter.filterForToday(filteredReservations);
        filteredReservations.setPredicate(todayPredicate);
    }
    @Override
    public void filterReservationsForTomorrow(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        Predicate<Reservation> tomorrowPredicate = ReservationsFilter.filterForTomorrow(filteredReservations);
        filteredReservations.setPredicate(tomorrowPredicate);
    }

    @Override
    public void filterReservationsByRegular(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        Predicate<Reservation> regularPredicate = ReservationsFilter.filterByRegular(filteredReservations);
        filteredReservations.setPredicate(regularPredicate);
    }
    @Override
    public void filterPreviousReservations(Predicate<Reservation> predicate) {

        Predicate<Reservation> previousPredicate = ReservationsFilter.filterForPrevious();
        filteredReservations.setPredicate(previousPredicate);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredReservations.equals(otherModelManager.filteredReservations);
    }

}
