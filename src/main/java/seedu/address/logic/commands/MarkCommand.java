package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Reservation;

/**
 * Marks a reservation as paid based on the given identification.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the reservation as paid "
            + "at the identification used in the reservation listing.\n"
            + "Parameters: ID ( [6 figures of date (ie : ddMMyyyy)) of TODAY or TOMORROW] "
            + "+ [last 4 digits of customer phone number (ie:xxxx)]).\n"
            + "Example: " + COMMAND_WORD + " 180320251234 ";

    public static final String MESSAGE_MARK_RESERVATION_SUCCESS = "Successfully marks the reservation";
    public final Identification id;

    /**
     * Constructs a {@code MarkCommand} with the given identification.
     *
     * @param id The identification of the reservation to be marked as paid.
     */
    public MarkCommand(Identification id) {
        requireNonNull(id);
        this.id = id;
    }

    /**
     * Executes the command to mark a reservation as paid.
     *
     * @param model The model containing the reservation data.
     * @return A {@code CommandResult} indicating the success message.
     * @throws CommandException If the reservation cannot be found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Reservation reservationToMark = Identification.getReservationUsingId(id, model);
        Reservation markedReservation = reservationToMark.toPaid();
        model.setReservation(reservationToMark, markedReservation);
        return new CommandResult(String.format(MESSAGE_MARK_RESERVATION_SUCCESS,
                Messages.format(reservationToMark)));
    }
}
