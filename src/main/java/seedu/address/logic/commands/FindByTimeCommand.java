package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.reservation.TimeMatchesPredicate;

/**
 * Finds and lists all reservations scheduled for today that are ongoing at the specified time.
 * A reservation is ongoing if its start time is at or before the search time and its end time
 * (computed as start time + duration) is after the search time.
 * Reservations ending exactly at the search time are not shown.
 */
public class FindByTimeCommand extends Command {

    public static final String COMMAND_WORD = "findt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all reservations scheduled for today only that are ongoing at the specified time.\n"
            + "Parameters: TIME (in HHmm format)\n"
            + "Example: " + COMMAND_WORD + " 1400";

    private final TimeMatchesPredicate predicate;

    public FindByTimeCommand(TimeMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredReservationList(reservation -> {
            // check time
            boolean matchesTime = predicate.test(reservation);

            // check if date is today
            boolean isToday = reservation.getDate().toLocalDate().equals(LocalDate.now());
            return matchesTime && isToday;
        });

        int count = model.getFilteredReservationList().size();
        String resultMessage;
        if (count == 0) {
            resultMessage = Messages.MESSAGE_NO_RESERVATIONS_LISTED;;
        } else {
            resultMessage = String.format(Messages.MESSAGE_RESERVATIONS_LISTED_OVERVIEW, count);
        }
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindByTimeCommand)) {
            return false;
        }
        FindByTimeCommand otherFindByTimeCommand = (FindByTimeCommand) other;
        return predicate.equals(otherFindByTimeCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
