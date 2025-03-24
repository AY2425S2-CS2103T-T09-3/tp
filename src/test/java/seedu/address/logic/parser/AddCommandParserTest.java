package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

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

public class AddCommandParserTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_validInput_success() throws Exception {
        LocalDate today = LocalDate.now();
        String validDate = today.format(FORMATTER); // Ensuring only today is used

        String userInput = " " + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "91234567 "
                + PREFIX_DATE + validDate + " "
                + PREFIX_TIME + "1200 "
                + PREFIX_DURATION + "1.5 "
                + PREFIX_PAX + "4 "
                + PREFIX_TABLE + "A10 ";

        AddCommand expectedCommand = new AddCommand(
                new Reservation(
                        new Name("John Doe"),
                        new Phone("91234567"),
                        new StartDate(validDate),
                        new StartTime("1200"),
                        new Duration("1.5"),
                        new Pax("4"),
                        new Table("A10"),
                        new Remark(""),
                        new HashSet<>(),
                        new Identification(new StartDate(validDate), new Phone("91234567")),
                        false)
        );

        AddCommand parsedCommand = parser.parse(userInput);
        assertEquals(expectedCommand, parsedCommand);
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        // Invalid date format (wrong separator)
        String userInput = " " + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "91234567 "
                + PREFIX_DATE + "2024-03-10 " // Incorrect format
                + PREFIX_TIME + "1200 "
                + PREFIX_DURATION + "1.5 "
                + PREFIX_PAX + "4 "
                + PREFIX_TABLE + "A10";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    /*
    @Test
    public void parse_pastDate_throwsParseException() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        String invalidDate = pastDate.format(FORMATTER); // Yesterday's date

        String userInput = " " + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "91234567 "
                + PREFIX_DATE + invalidDate + " "
                + PREFIX_TIME + "1200 "
                + PREFIX_DURATION + "1.5 "
                + PREFIX_PAX + "4 "
                + PREFIX_TABLE + "A10";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_futureBeyondTomorrow_throwsParseException() {
        LocalDate futureDate = LocalDate.now().plusDays(5);
        String invalidDate = futureDate.format(FORMATTER); // 5 days from today

        String userInput = " " + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "91234567 "
                + PREFIX_DATE + invalidDate + " "
                + PREFIX_TIME + "1200 "
                + PREFIX_DURATION + "1.5 "
                + PREFIX_PAX + "4 "
                + PREFIX_TABLE + "A10";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

     */

    @Test
    public void parse_invalidTimeFormat_throwsParseException() {
        // Invalid time format (exceeds 24-hour format)
        LocalDate today = LocalDate.now();
        String validDate = today.format(FORMATTER);

        String userInput = " " + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "91234567 "
                + PREFIX_DATE + validDate + " "
                + PREFIX_TIME + "2500 " // Invalid time
                + PREFIX_DURATION + "1.5 "
                + PREFIX_PAX + "4 "
                + PREFIX_TABLE + "A10";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate phone number
        LocalDate today = LocalDate.now();
        String validDate = today.format(FORMATTER);

        String userInput = " " + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "91234567 "
                + PREFIX_PHONE + "98765432 " // Duplicate phone
                + PREFIX_DATE + validDate + " "
                + PREFIX_TIME + "1200 "
                + PREFIX_DURATION + "1.5 "
                + PREFIX_PAX + "4 "
                + PREFIX_TABLE + "A10";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
