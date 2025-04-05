package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkCommand;
public class MarkCommandParserTest {

    //partition: invalid id form, valid id form
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);

    private MarkCommandParser parser = new MarkCommandParser();


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
