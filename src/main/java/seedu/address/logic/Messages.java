package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.reservation.Reservation;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX = "The Reservation "
        + "index provided is invalid";
    public static final String MESSAGE_RESERVATIONS_LISTED_OVERVIEW = "%1$d reservations listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code reservation} for display to the user.
     */
    public static String format(Reservation reservation) {
        final StringBuilder builder = new StringBuilder();
        builder.append(reservation.getName())
                .append("; Phone: ")
                .append(reservation.getPhone())
                .append("; Date: ")
                .append(reservation.getDate())
                .append("; Time: ")
                .append(reservation.getTime())
                .append("; Duration: ")
                .append(reservation.getDuration())
                .append("; Pax: ")
                .append(reservation.getPax())
                .append("; Table: ")
                .append(reservation.getTable())
                .append("; Tags: ");
        reservation.getTags().forEach(builder::append);
        builder.append("; Remark: ")
                .append(reservation.getRemark())
                .append("; ID: ")
                .append(reservation.getId());
        return builder.toString();
    }

}
