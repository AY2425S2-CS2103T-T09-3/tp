package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reservation.Duration;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartDate;
import seedu.address.model.reservation.StartTime;
import seedu.address.model.reservation.Table;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION,
                        PREFIX_PAX, PREFIX_TABLE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION,
                PREFIX_PAX, PREFIX_TABLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }


        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION,
                PREFIX_PAX, PREFIX_TABLE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        StartDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        StartTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Pax pax = ParserUtil.parsePax(argMultimap.getValue(PREFIX_PAX).get());
        Table table = ParserUtil.parseTable(argMultimap.getValue(PREFIX_TABLE).get());
        Remark remark = new Remark("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        // Make use of current date ddMMyyyy and last 4 digits of phone and current reservation count
        // to form a unique key id
        Identification id = new Identification(date.toWithoutSlashString()
                + phone.getLastFourDigitsString());

        Reservation reservation = new Reservation(name, phone, date, time, duration, pax, table, remark, tagList, id);
        return new AddCommand(reservation);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
