package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.GastroBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalReservations;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalReservations.getTypicalGastroBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        // When list is not filtered, executing list command should show the same list
        CommandResult result = new ListCommand().execute(model);

        // Check that the command result contains the correct message
        assertEquals(ListCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Check that the model's filtered list matches the expected model's filtered list
        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());

        // Check that the predicate is set to show all reservations
        assertTrue(model.getFilteredReservationList().size() > 0);
    }

    @Test
    public void execute_listIsFiltered_showsAllReservations() {
        // First filter the list
        model.updateFilteredReservationList(reservation -> false);

        // Execute list command
        CommandResult result = new ListCommand().execute(model);

        // Check that the command result contains the correct message
        assertEquals(ListCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Check that all reservations are shown after executing list command
        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());

        // Check that the predicate is set to show all reservations
        assertTrue(model.getFilteredReservationList().size() == expectedModel.getFilteredReservationList().size());
    }

    @Test
    public void execute_emptyAddressBook_showsEmptyList() {
        // Create models with empty address book
        model = new ModelManager(new GastroBook(), new UserPrefs());
        expectedModel = new ModelManager(new GastroBook(), new UserPrefs());

        // Execute list command
        CommandResult result = new ListCommand().execute(model);

        // Check that the command result contains the correct message
        assertEquals(ListCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Check that the filtered list is empty
        assertEquals(0, model.getFilteredReservationList().size());
        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ListCommand listCommand = new ListCommand();
        assertThrows(NullPointerException.class, () -> listCommand.execute(null));
    }

}
