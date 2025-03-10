package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class ReservationUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Reservation reservation) {
        return AddCommand.COMMAND_WORD + " " + getReservationDetails(reservation);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getReservationDetails(Reservation reservation) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + reservation.getName().fullName + " ");
        sb.append(PREFIX_PHONE + reservation.getPhone().value + " ");
        sb.append(PREFIX_DATE + reservation.getDate().value + " ");
        sb.append(PREFIX_TIME + reservation.getTime().value + " ");
        sb.append(PREFIX_DURATION + reservation.getDuration().value + " ");
        sb.append(PREFIX_PAX + reservation.getPax().value + " ");
        sb.append(PREFIX_TABLE + reservation.getTable().value + " ");
        reservation.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        sb.append(PREFIX_ID + reservation.getId().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditReservationDescriptorDetails(EditCommand.EditReservationDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.value).append(" "));
        descriptor.getTime().ifPresent(time -> sb.append(PREFIX_TIME).append(time.value).append(" "));
        descriptor.getDuration().ifPresent(duration -> sb.append(PREFIX_DURATION).append(duration.value).append(" "));
        descriptor.getPax().ifPresent(pax -> sb.append(PREFIX_PAX).append(pax.value).append(" "));
        descriptor.getTable().ifPresent(table -> sb.append(PREFIX_TABLE).append(table.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        descriptor.getId().ifPresent(id -> sb.append(PREFIX_ID).append(id.value).append(" "));
        return sb.toString();
    }
}
