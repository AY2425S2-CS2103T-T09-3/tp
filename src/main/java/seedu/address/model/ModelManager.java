package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Reservation;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final PersonsList personsList;
    private final PersonsListManager personsListManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Reservation> filteredReservations;


    /**
     * Initializes a ModelManager with the given addressBook, personsList and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, PersonsList personsList, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, personsList, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + ", persons list: "
                + personsList
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.personsList = personsList;
        this.personsListManager = new PersonsListManager(personsList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredReservations = new FilteredList<>(this.addressBook.getReservationList());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs, and an empty personsList.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        this(addressBook, new PersonsList(), userPrefs);
    }

    public ModelManager() {
        this(new AddressBook(), new PersonsList(), new UserPrefs());
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

    //=========== PersonsList ================================================================================

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return personsList.hasPerson(person);
    }

    @Override
    public void addPerson(Person person) {
        personsList.addPerson(person);
    }

    @Override
    public Person recordBooking(Name name, Phone phone) {
        return personsList.recordBooking(name, phone);
    }

    @Override
    public ArrayList<Person> getRegularCustomers() {
        return personsList.getRegularCustomers();
    }

    public PersonsList getPersonsList() {
        return this.personsList;
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

    /**
     * Updates person details in PersonsList when a reservation is edited.
     * @param oldReservation the reservation before editing
     * @param newReservation the reservation after editing
     */
    @Override
    public void updatePersonsListAfterEdit(Reservation oldReservation, Reservation newReservation) {
        personsListManager.updatePersonsListAfterEdit(oldReservation, newReservation);
    }

    @Override
    public void addReservation(Reservation reservation) {
        addressBook.addReservation(reservation);

        // Update customer booking history when adding a reservation
        Name customerName = reservation.getName();
        Phone customerPhone = reservation.getPhone();
        recordBooking(customerName, customerPhone);

        updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
    }

    @Override
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        addressBook.setReservation(target, editedReservation);
    }

    //=========== Filtered Reservation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of
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
                && personsList.equals(otherModelManager.personsList) // Added personsList comparison
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredReservations.equals(otherModelManager.filteredReservations);
    }
}
