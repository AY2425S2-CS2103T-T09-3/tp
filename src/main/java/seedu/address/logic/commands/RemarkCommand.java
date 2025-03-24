package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: Reservation ID (date of reservation in DDMMYYYY + last 4 digits of phone number) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 01012025 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";

    private final Identification id;
    private final Remark remark;

    /**
     * @param id of the reservation in the filtered reservation list to edit the remark
     * @param remark of the reservation to be updated to
     */
    public RemarkCommand(Identification id, Remark remark) {
        requireAllNonNull(id, remark);

        this.id = id;
        this.remark = remark;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {

        Reservation reservationToEdit = Identification.getReservationUsingId(id, model);
        Reservation editedReservation = new Reservation(reservationToEdit.getName(), reservationToEdit.getPhone(),
                reservationToEdit.getDate(), reservationToEdit.getTime(), reservationToEdit.getDuration(),
                reservationToEdit.getPax(), reservationToEdit.getTable(), remark, reservationToEdit.getTags(),
                reservationToEdit.getId(), reservationToEdit.getIsPaid());

        model.setReservation(reservationToEdit, editedReservation);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);

        return new CommandResult(generateSuccessMessage(editedReservation));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code reservationToEdit}.
     */
    private String generateSuccessMessage(Reservation reservationToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, reservationToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return id.equals(e.id)
                && remark.equals(e.remark);
    }
}
