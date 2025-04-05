package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalReservations.ALICE;
import static seedu.address.testutil.TypicalReservations.getTypicalGastroBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.GastroBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.EditReservationDescriptorBuilder;
import seedu.address.testutil.ReservationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalGastroBook(), new UserPrefs());
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Reservation editedReservation = ALICE;
        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder(
            editedReservation).build();
        EditCommand editCommand = new EditCommand(editedReservation.getId(), descriptor);

        String expectedMessage = String.format(
            EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
            Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new GastroBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReservation(model.getFilteredReservationList().get(0), editedReservation);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Reservation lastReservation = model.getFilteredReservationList().get(1); // BENSON

        ReservationBuilder personInList = new ReservationBuilder(lastReservation);
        Reservation editedReservation = personInList.withName(VALID_NAME_BOB).withDuration(VALID_DURATION_BOB)
            .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditReservationDescriptor descriptor =
            new EditReservationDescriptorBuilder().withName(VALID_NAME_BOB)
            .withDuration(VALID_DURATION_BOB).withTags(VALID_TAG_HUSBAND).build();


        EditCommand editCommand = new EditCommand(lastReservation.getId(), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
            Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new GastroBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReservation(lastReservation, editedReservation);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Reservation editedReservation = model.getFilteredReservationList().get(1);
        EditCommand editCommand = new EditCommand(editedReservation.getId(),
                new EditCommand.EditReservationDescriptor());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new GastroBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        //  showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Reservation reservationInFilteredList = model.getFilteredReservationList()
                .get(1);
        Reservation editedReservation = new ReservationBuilder(reservationInFilteredList)
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(reservationInFilteredList.getId(),
                new EditReservationDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new GastroBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setReservation(model.getFilteredReservationList().get(1), editedReservation);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
}
