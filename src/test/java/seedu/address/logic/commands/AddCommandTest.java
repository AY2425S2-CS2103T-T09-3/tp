package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReservations.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.ReservationBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Reservation validReservation = new ReservationBuilder().build();

        CommandResult commandResult = new AddCommand(validReservation).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validReservation)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validReservation), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Reservation validReservation = new ReservationBuilder().build();
        AddCommand addCommand = new AddCommand(validReservation);
        ModelStub modelStub = new ModelStubWithPerson(validReservation);

        assertThrows(CommandException.class, AddCommand
                .MESSAGE_DUPLICATE_RESERVATION, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Reservation alice = new ReservationBuilder().withName("Alice").build();
        Reservation bob = new ReservationBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReservation(Reservation reservation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReservation(Reservation reservation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReservation(Reservation target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setReservation(Reservation target, Reservation editedReservation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reservation> getFilteredReservationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReservationList(Predicate<Reservation> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Reservation reservation;

        ModelStubWithPerson(Reservation reservation) {
            requireNonNull(reservation);
            this.reservation = reservation;
        }

        @Override
        public boolean hasReservation(Reservation reservation) {
            requireNonNull(reservation);
            return this.reservation.isSameReservation(reservation);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Reservation> personsAdded = new ArrayList<>();

        @Override
        public boolean hasReservation(Reservation reservation) {
            requireNonNull(reservation);
            return personsAdded.stream().anyMatch(reservation::isSameReservation);
        }

        @Override
        public void addReservation(Reservation reservation) {
            requireNonNull(reservation);
            personsAdded.add(reservation);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
