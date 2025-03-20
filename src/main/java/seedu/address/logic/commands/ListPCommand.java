package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.address.model.Model;


/**
 * Lists all previous reservations
 */
public class ListPCommand extends Command {
    public static final String COMMAND_WORD = "listp";
    public static final String MESSAGE_SUCCESS = "Listed all previous reservations";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.filterPreviousReservations(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
