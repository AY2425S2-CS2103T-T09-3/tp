package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.StartDate;
import seedu.address.model.reservation.StartTime;


public class UnmarkCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE);
    private UnmarkCommandParser parser = new UnmarkCommandParser();
    //partition: invalid id form, valid id form

    @Test
    public void parser_validId_returnUnmarkCommand() {
        Identification id = new Identification(new StartDate("01/01/2025"),
                new Phone("12345678"), new StartTime("2359"));
        System.out.println("id" + id.toString());
        assertParseSuccess(parser, "0101202556782359", new UnmarkCommand(id));
    }

    @Test
    public void parser_invalidIdLength_throwParseException() {
        assertParseFailure(parser, "1242432", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parser_invalidIdWithInvalidDate_throwParseException() {
        assertParseFailure(parser, "1313202512342359", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parser_invalidIdWithInvalidTime_throwParseException() {
        assertParseFailure(parser, "0101202512342459", MESSAGE_INVALID_FORMAT);
    }
}
