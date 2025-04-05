package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_NO_RESERVATIONS_LISTED;
import static seedu.address.logic.Messages.MESSAGE_RESERVATIONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalReservations.getTypicalGastroBook;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.TimeMatchesPredicate;

public class FindByTimeCommandTest {
    private Model model = new ModelManager(getTypicalGastroBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalGastroBook(), new UserPrefs());

    @Test
    public void execute_matchingTimeToday_reservationsFound() {
        LocalTime queryTime = LocalTime.of(14, 30);
        //Should match BENSON
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(queryTime);
        FindByTimeCommand command = new FindByTimeCommand(predicate);

        expectedModel.updateFilteredReservationList(reservation ->
                reservation.getDate().toLocalDate().equals(LocalDate.now())
                        && predicate.test(reservation));

        CommandResult result = command.execute(model);

        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());
        assertEquals(
                String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW,
                        model.getFilteredReservationList().size()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_timeWithNoOngoingReservations_noResults() {
        LocalTime queryTime = LocalTime.of(22, 0);
        //No Reservation should match
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(queryTime);
        FindByTimeCommand command = new FindByTimeCommand(predicate);

        expectedModel.updateFilteredReservationList(reservation ->
                reservation.getDate().toLocalDate().equals(LocalDate.now())
                        && predicate.test(reservation));

        CommandResult result = command.execute(model);

        assertEquals(0, model.getFilteredReservationList().size());
        assertEquals(MESSAGE_NO_RESERVATIONS_LISTED, result.getFeedbackToUser());
    }

    @Test
    public void execute_matchingTimeToday_multipleReservationsFound() {
        LocalTime queryTime = LocalTime.of(12, 30);
        //Should match DANIEL and FIONA
        TimeMatchesPredicate predicate = new TimeMatchesPredicate(queryTime);
        FindByTimeCommand command = new FindByTimeCommand(predicate);

        expectedModel.updateFilteredReservationList(reservation ->
                reservation.getDate().toLocalDate().equals(LocalDate.now())
                        && predicate.test(reservation));

        CommandResult result = command.execute(model);

        assertEquals(2, model.getFilteredReservationList().size());
        assertEquals(
                String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW, 2),
                result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        TimeMatchesPredicate predicate1 = new TimeMatchesPredicate(LocalTime.of(12, 0));
        TimeMatchesPredicate predicate2 = new TimeMatchesPredicate(LocalTime.of(13, 0));

        FindByTimeCommand command1 = new FindByTimeCommand(predicate1);
        FindByTimeCommand command2 = new FindByTimeCommand(predicate1);
        FindByTimeCommand command3 = new FindByTimeCommand(predicate2);

        assertEquals(command1, command2);
        assertEquals(command1, command1);
        assertEquals(command1.equals(null), false);
        assertEquals(command1.equals("not a command"), false);
        assertEquals(command1.equals(command3), false);
    }
}
