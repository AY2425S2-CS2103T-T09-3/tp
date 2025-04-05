package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UnmarkCommand.MESSAGE_DUPLICATE_UNMARK;
import static seedu.address.logic.commands.UnmarkCommand.MESSAGE_UNMARK_RESERVATION_SUCCESS;
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

public class UnmarkCommandTest {
    private Model model = new ModelManager(getTypicalGastroBook(), new UserPrefs());
    @Test
    public void execute_unmarkPaidReservation_success() {
        //the reservation at index 2 is paid at the start
        Reservation reservationToUnmark = model.getFilteredReservationList().get(2);
        Identification id = new Identification(reservationToUnmark.getDate(), reservationToUnmark.getPhone(),
                reservationToUnmark.getTime());

        UnmarkCommand unmarkCommand = new UnmarkCommand(id);

        String expectedMessage = String.format(MESSAGE_UNMARK_RESERVATION_SUCCESS,
                Messages.format(reservationToUnmark));
        Reservation reservationUnmarked = new ReservationBuilder(reservationToUnmark).withPaymentStatus(false)
                .build();


        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setReservation(reservationToUnmark, reservationUnmarked);
        assertCommandSuccess(unmarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unmarkUnpaidReservation_fail() {
        //the reservation at index 3 is unpaid at the start
        Reservation reservationToUnmark = model.getFilteredReservationList().get(3);
        Identification id = new Identification(reservationToUnmark.getDate(), reservationToUnmark.getPhone(),
                reservationToUnmark.getTime());

        UnmarkCommand unmarkCommand = new UnmarkCommand(id);

        String expectedMessage = String.format(MESSAGE_DUPLICATE_UNMARK,
                Messages.format(reservationToUnmark));
        assertCommandFailure(unmarkCommand, model, expectedMessage);

    }

    @Test
    public void execute_validCommandAndNotExistId_fail() {
        Reservation reservationToUnmark = model.getFilteredReservationList().get(0);
        Identification id = new Identification(reservationToUnmark.getDate(), reservationToUnmark.getPhone(),
                new StartTime("2359"));

        UnmarkCommand unmarkCommand = new UnmarkCommand(id);

        String expectedMessage = "Input reservation id does not exist.";

        assertCommandFailure(unmarkCommand, model, expectedMessage);
    }


}
