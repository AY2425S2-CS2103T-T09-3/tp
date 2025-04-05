package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.MarkCommand.MESSAGE_DUPLICATE_MARK;
import static seedu.address.logic.commands.MarkCommand.MESSAGE_MARK_RESERVATION_SUCCESS;
import static seedu.address.testutil.TypicalReservations.getTypicalGastroBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartTime;
import seedu.address.testutil.ReservationBuilder;

public class MarkCommandTest {
    private Model model = new ModelManager(getTypicalGastroBook(), new UserPrefs());

    //partition: id that exist and id that does not exist

    @Test
    public void execute_validCommandAndId_success() {
        Reservation reservationToMark = model.getFilteredReservationList().get(1);
        Identification id = new Identification(reservationToMark.getDate(), reservationToMark.getPhone(),
                reservationToMark.getTime());

        MarkCommand markCommand = new MarkCommand(id);

        String expectedMessage = String.format(MESSAGE_MARK_RESERVATION_SUCCESS,
                Messages.format(reservationToMark));
        Reservation reservationMarked = new ReservationBuilder(reservationToMark)
                .withPaymentStatus(true).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setReservation(reservationToMark, reservationMarked);
        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markPaidReservation_fail() {
        //the reservation at index 5 is paid at the start
        Reservation reservationTomark = model.getFilteredReservationList().get(5);
        Identification id = new Identification(reservationTomark.getDate(), reservationTomark.getPhone(),
                reservationTomark.getTime());

        MarkCommand markCommand = new MarkCommand(id);

        String expectedMessage = String.format(MESSAGE_DUPLICATE_MARK,
                Messages.format(reservationTomark));
        assertCommandFailure(markCommand, model, expectedMessage);

    }

    @Test
    public void execute_validCommandAndNotExistId_fail() {
        Reservation reservationToMark = model.getFilteredReservationList().get(0);
        Identification id = new Identification(reservationToMark.getDate(), reservationToMark.getPhone(),
                new StartTime("2359"));

        MarkCommand markCommand = new MarkCommand(id);

        String expectedMessage = "Input reservation id does not exist.";

        assertCommandFailure(markCommand, model, expectedMessage);
    }

}
