package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.reservation.NameContainsKeywordsPredicate;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.EditReservationDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_DATE_AMY = "01/03/2025";
    public static final String VALID_DATE_BOB = "02/03/2025";
    public static final String VALID_TIME_AMY = "1800";
    public static final String VALID_TIME_BOB = "2000";
    public static final String VALID_DURATION_AMY = "2";
    public static final String VALID_DURATION_BOB = "3";
    public static final String VALID_PAX_AMY = "2";
    public static final String VALID_PAX_BOB = "4";
    public static final String VALID_TABLE_AMY = "A1";
    public static final String VALID_TABLE_BOB = "B1";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_ID_AMY = "0001";
    public static final String VALID_ID_BOB = "0010";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
    public static final String TIME_DESC_AMY = " " + PREFIX_TIME + VALID_TIME_AMY;
    public static final String TIME_DESC_BOB = " " + PREFIX_TIME + VALID_TIME_BOB;
    public static final String DURATION_DESC_AMY = " " + PREFIX_DURATION + VALID_DURATION_AMY;
    public static final String DURATION_DESC_BOB = " " + PREFIX_DURATION + VALID_DURATION_BOB;
    public static final String PAX_DESC_AMY = " " + PREFIX_PAX + VALID_PAX_AMY;
    public static final String PAX_DESC_BOB = " " + PREFIX_PAX + VALID_PAX_BOB;
    public static final String TABLE_DESC_AMY = " " + PREFIX_TABLE + VALID_TABLE_AMY;
    public static final String TABLE_DESC_BOB = " " + PREFIX_TABLE + VALID_TABLE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ID_DESC_AMY = " " + PREFIX_ID + VALID_ID_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_ID + VALID_ID_BOB;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditReservationDescriptor DESC_AMY;
    public static final EditCommand.EditReservationDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditReservationDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withDate(VALID_DATE_AMY).withTime(VALID_TIME_AMY)
                .withDuration(VALID_DURATION_AMY).withPax(VALID_PAX_AMY).withTable(VALID_TABLE_AMY)
                .withTags(VALID_TAG_FRIEND).withId(VALID_ID_AMY).build();
        DESC_BOB = new EditReservationDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withDate(VALID_DATE_BOB).withTime(VALID_TIME_BOB)
                .withDuration(VALID_DURATION_BOB).withPax(VALID_PAX_BOB).withTable(VALID_TABLE_BOB)
                .withTags(VALID_TAG_FRIEND).withId(VALID_ID_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Reservation> expectedFilteredList = new ArrayList<>(actualModel.getFilteredReservationList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredReservationList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredReservationList().size());

        Reservation reservation = model.getFilteredReservationList().get(targetIndex.getZeroBased());
        final String[] splitName = reservation.getName().fullName.split("\\s+");
        model.updateFilteredReservationList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredReservationList().size());
    }

}
