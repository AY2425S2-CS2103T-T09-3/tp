package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.address.model.Model;


/**
 * Lists all reservations by regulars
 */
public class ListRCommand extends Command {
    public static final String COMMAND_WORD = "listr";
    public static final String MESSAGE_SUCCESS = "Listed all reservations by regulars";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.filterReservationsByRegular(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}


