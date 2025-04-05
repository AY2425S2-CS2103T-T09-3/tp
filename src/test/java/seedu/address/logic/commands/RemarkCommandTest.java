package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.GastroBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartTime;
import seedu.address.testutil.ReservationBuilder;
import seedu.address.testutil.TypicalReservations;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    // Create a model using typical reservations.
    private Model model = new ModelManager(new GastroBook(TypicalReservations.getTypicalGastroBook()), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Reservation originalReservation = model.getFilteredReservationList().get(0);
        Identification id = originalReservation.getId();
        Remark newRemark = new Remark(CommandTestUtil.VALID_REMARK_AMY);

        // Create an edited reservation with the updated remark
        Reservation editedReservation = new ReservationBuilder(originalReservation).withRemark(newRemark.value).build();

        RemarkCommand remarkCommand = new RemarkCommand(id, newRemark);
        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new GastroBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReservation(originalReservation, editedReservation);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Reservation originalReservation = model.getFilteredReservationList().get(0);
        Identification id = originalReservation.getId();

        // Created an edited reservation with an empty remark (ie delete remark)
        Remark newRemark = new Remark("");
        Reservation editedReservation = new ReservationBuilder(originalReservation).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(id, newRemark);
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new GastroBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReservation(originalReservation, editedReservation);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        Reservation originalReservation = model.getFilteredReservationList().get(0);
        Identification invalidId = new Identification(originalReservation.getDate(), originalReservation.getPhone(),
                new StartTime("2359"));
        Remark newRemark = new Remark("Dummy remark");
        RemarkCommand remarkCommand = new RemarkCommand(invalidId, newRemark);

        String expectedMessage = "Input reservation id does not exist.";
        assertCommandFailure(remarkCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Reservation originalReservation = model.getFilteredReservationList().get(0);
        Identification id = originalReservation.getId();
        Remark remarkAmy = new Remark("Amy's remark");
        Remark remarkBob = new Remark("Bob's remark");

        RemarkCommand command1 = new RemarkCommand(id, remarkAmy);
        RemarkCommand command2 = new RemarkCommand(id, remarkAmy);
        RemarkCommand command3 = new RemarkCommand(id, remarkBob);

        // same values -> returns true
        assertTrue(command1.equals(command2));
        // same object -> returns true
        assertTrue(command1.equals(command1));
        // null -> returns false
        assertFalse(command1.equals(null));
        // different type -> returns false
        assertFalse(command1.equals(new ClearCommand()));
        // different remark -> returns false
        assertFalse(command1.equals(command3));
    }
}
