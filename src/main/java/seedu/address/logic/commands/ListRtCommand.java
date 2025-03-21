package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.address.model.Model;

/**
 * Lists all reservations scheduled for today.
 */
public class ListRtCommand extends Command {
    public static final String COMMAND_WORD = "listrt";
    public static final String MESSAGE_SUCCESS = "Listed all reservations for today";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.filterReservationsForToday(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
