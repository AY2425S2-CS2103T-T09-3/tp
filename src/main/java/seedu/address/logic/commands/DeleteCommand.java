package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Reservation;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the ID used in the displayed reservation list.\n"
            + "Parameters: ID ([6 figures of date (ie: ddMMyyyy) of TODAY or TOMORROW] "
            + "+ [last 4 digits of customer phone number (ie: xxxx)] "
            + "+ [time of reservation in HHMM format])\n"
            + "Example: " + COMMAND_WORD + " 0402202598761700";

    public static final String MESSAGE_DELETE_RESERVATION_SUCCESS = "Deleted Reservation: %1$s";

    private final Identification id;

    public DeleteCommand(Identification id) {
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Reservation reservationToDelete = Identification.getReservationUsingId(id, model);
        model.deleteReservation(reservationToDelete);
        //delete/decrement in persons list
        model.updatePersonsListAfterDelete(reservationToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_RESERVATION_SUCCESS,
                Messages.format(reservationToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return id.equals(otherDeleteCommand.id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identification", id)
                .toString();
    }
}
