package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PersonsList;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalReservations;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListRnCommand.
 */
public class ListRnCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalReservations.getTypicalAddressBook(), new PersonsList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getPersonsList(), new UserPrefs());
        // Set up expected model to filter for tomorrow's reservations
        expectedModel.filterReservationsForTomorrow(PREDICATE_SHOW_ALL_RESERVATIONS);
    }

    @Test
    public void execute_showsTomorrowReservations() {
        // Execute ListRn command
        CommandResult result = new ListRnCommand().execute(model);

        // Check that the command result contains the correct message
        assertEquals(ListRnCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Check that the model's filtered list matches the expected model's filtered list
        // (which has been filtered for tomorrow's reservations)
        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());
    }

    @Test
    public void execute_afterOtherFilterIsApplied_stillShowsTomorrowReservations() {
        // First apply a different filter
        model.updateFilteredReservationList(reservation -> false);

        // Verify the list is filtered (should be empty)
        assertEquals(0, model.getFilteredReservationList().size());

        // Execute ListRn command
        CommandResult result = new ListRnCommand().execute(model);

        // Check that the command result contains the correct message
        assertEquals(ListRnCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Check that the model's filtered list now matches the expected model's list
        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());
    }

    @Test
    public void execute_emptyAddressBook_showsEmptyList() {
        // Create models with empty address book
        model = new ModelManager(new AddressBook(), new PersonsList(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(), new PersonsList(), new UserPrefs());

        // Execute ListRn command
        CommandResult result = new ListRnCommand().execute(model);

        // Check that the command result contains the correct message
        assertEquals(ListRnCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Check that the filtered list is empty
        assertEquals(0, model.getFilteredReservationList().size());
        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ListRnCommand listRnCommand = new ListRnCommand();
        assertThrows(NullPointerException.class, () -> listRnCommand.execute(null));
    }

}
