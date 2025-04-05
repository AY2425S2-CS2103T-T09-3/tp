package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.reservation.NameContainsKeywordsPredicate;
import seedu.address.model.reservation.StartDate;

/**
 * Finds and lists all persons whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindByNameCommand extends Command {

    public static final String COMMAND_WORD = "findn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names match any of "
            + "the specified names (case-insensitive) and displays reservations made by them for today or "
            + "tomorrow as a list with index numbers.\n"
            + "Parameters: NAME [MORE NAMES]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindByNameCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredReservationList(reservation -> {
            // Check name
            boolean matchesName = predicate.test(reservation);

            // Check if date is today or tomorrow
            boolean isTodayOrTomorrow = StartDate.isValidDateRange(reservation.getDate().value);
            return matchesName && isTodayOrTomorrow;
        });

        int count = model.getFilteredReservationList().size();
        String resultMessage;
        if (count == 0) {
            resultMessage = Messages.MESSAGE_NO_RESERVATIONS_LISTED;
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

        // instanceof handles nulls
        if (!(other instanceof FindByNameCommand)) {
            return false;
        }

        FindByNameCommand otherFindByNameCommand = (FindByNameCommand) other;
        return predicate.equals(otherFindByNameCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
