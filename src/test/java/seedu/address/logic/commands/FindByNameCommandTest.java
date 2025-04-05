package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_NO_RESERVATIONS_LISTED;
import static seedu.address.logic.Messages.MESSAGE_RESERVATIONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalReservations.getTypicalGastroBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reservation.NameContainsKeywordsPredicate;
import seedu.address.model.reservation.StartDate;

public class FindByNameCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGastroBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalGastroBook(), new UserPrefs());
    }

    @Test
    public void execute_singleKeyword_matchesOneOrMore() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        FindByNameCommand command = new FindByNameCommand(predicate);

        expectedModel.updateFilteredReservationList(reservation ->
                predicate.test(reservation)
                        && StartDate.isValidDateRange(reservation.getDate().value));

        CommandResult result = command.execute(model);

        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());
        assertEquals(String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW,
                model.getFilteredReservationList().size()), result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleKeywords_caseInsensitive() {
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Arrays.asList("benson", "daniel"));

        FindByNameCommand command = new FindByNameCommand(predicate);

        expectedModel.updateFilteredReservationList(reservation ->
                predicate.test(reservation)
                        && StartDate.isValidDateRange(reservation.getDate().value));

        CommandResult result = command.execute(model);

        assertEquals(expectedModel.getFilteredReservationList(), model.getFilteredReservationList());
        assertEquals(String.format(MESSAGE_RESERVATIONS_LISTED_OVERVIEW,
                model.getFilteredReservationList().size()), result.getFeedbackToUser());
    }

    @Test
    public void execute_noMatchingNames_returnsEmptyList() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Zelda"));
        FindByNameCommand command = new FindByNameCommand(predicate);

        expectedModel.updateFilteredReservationList(reservation ->
                predicate.test(reservation)
                        && StartDate.isValidDateRange(reservation.getDate().value));

        CommandResult result = command.execute(model);

        assertEquals(0, model.getFilteredReservationList().size());
        assertEquals(MESSAGE_NO_RESERVATIONS_LISTED, result.getFeedbackToUser());
    }

    @Test
    public void execute_partialNameKeyword_doesNotMatch() {
        // "Ali" should NOT match "Alice"
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Ali"));
        FindByNameCommand command = new FindByNameCommand(predicate);

        expectedModel.updateFilteredReservationList(reservation ->
                predicate.test(reservation)
                        && StartDate.isValidDateRange(reservation.getDate().value));

        CommandResult result = command.execute(model);

        assertEquals(0, model.getFilteredReservationList().size());
        assertEquals(MESSAGE_NO_RESERVATIONS_LISTED, result.getFeedbackToUser());
    }


    @Test
    public void equals() {
        NameContainsKeywordsPredicate predicate1 =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        NameContainsKeywordsPredicate predicate2 =
                new NameContainsKeywordsPredicate(Collections.singletonList("Bob"));

        FindByNameCommand command1 = new FindByNameCommand(predicate1);
        FindByNameCommand command2 = new FindByNameCommand(predicate1);
        FindByNameCommand command3 = new FindByNameCommand(predicate2);

        assertEquals(command1, command1); // same object
        assertEquals(command1, command2); // same predicate
        assertEquals(command1.equals(null), false);
        assertEquals(command1.equals("not a command"), false);
        assertEquals(command1.equals(command3), false); // different predicate
    }
}
