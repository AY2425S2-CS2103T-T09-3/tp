package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.TypicalReservations.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.reservation.Reservation;
import seedu.address.testutil.ReservationBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reservation expectedReservation = new ReservationBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble


        // multiple tags - all accepted
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {

        // multiple names

        // multiple phones


        // multiple fields repeated


        // invalid value followed by valid value

        // invalid name

        // invalid phone

        // valid value followed by invalid value

        // invalid name

        // invalid phone

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix

        // missing phone prefix

        // all prefixes missing
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name

        // invalid phone

        // invalid tag

        // two invalid values, only first invalid value reported

        // non-empty preamble

    }
}
