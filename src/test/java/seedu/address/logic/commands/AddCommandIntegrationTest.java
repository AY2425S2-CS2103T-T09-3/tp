package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalReservations.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.ReservationBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Reservation validReservation = new ReservationBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addReservation(validReservation);

        assertCommandSuccess(new AddCommand(validReservation), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validReservation)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Reservation reservationInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(reservationInList), model,
                AddCommand.MESSAGE_DUPLICATE_RESERVATION);
    }

}
