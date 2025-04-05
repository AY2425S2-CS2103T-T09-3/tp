package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.address.logic.Messages;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or edits the remark of the reservation "
            + "identified by the ID used in the displayed reservation list. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: ID ([6 figures of date (ie: ddMMyyyy) of TODAY or TOMORROW] "
            + "+ [last 4 digits of customer phone number (ie: xxxx)] "
            + "+ [time of reservation in HHMM format]) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 0402202598761700 "
            + PREFIX_REMARK + "Likes milk and cheese";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to reservation: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from reservation: %1$s";

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
        return String.format(message, Messages.format(reservationToEdit));
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
