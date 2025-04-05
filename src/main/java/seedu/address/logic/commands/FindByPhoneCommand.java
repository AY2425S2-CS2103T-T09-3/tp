package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.reservation.PhoneContainsKeywordsPredicate;
import seedu.address.model.reservation.StartDate;

/**
 * Finds and lists all reservations whose phone numbers match any of the argument keywords.
 */
public class FindByPhoneCommand extends Command {

    public static final String COMMAND_WORD = "findp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reservations whose phone numbers match "
            + "any of the specified phone numbers and displays reservations made by them for today or "
            + "tomorrow as a list with index numbers.\n"
            + "Parameters: PHONE NUMBER [MORE PHONE NUMBERS]...\n"
            + "Example: " + COMMAND_WORD + " 98765432 81234567";

    private final PhoneContainsKeywordsPredicate predicate;

    public FindByPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredReservationList(reservation -> {
            // Check phone number
            boolean matchesPhone = predicate.test(reservation);

            // Check if date is today or tomorrow
            boolean isTodayOrTomorrow = StartDate.isValidDateRange(reservation.getDate().value);
            return matchesPhone && isTodayOrTomorrow;
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
        if (!(other instanceof FindByPhoneCommand)) {
            return false;
        }
        FindByPhoneCommand otherFindByPhoneCommand = (FindByPhoneCommand) other;
        return predicate.equals(otherFindByPhoneCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
