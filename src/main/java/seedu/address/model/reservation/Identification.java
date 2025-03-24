package seedu.address.model.reservation;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an identification number in the reservation system.
 * Ensures that the ID consists only of integers.
 */
public class Identification {
    public static final String MESSAGE_CONSTRAINTS =
            "Identification should only contain integers in the form of Date of reservation (ie: ddMMyyyy) "
                    + "+ last 4 digits of the reservation phone number (ie: 1234)."
                    + "The date should only be of today or tomorrow.";

    public static final String VALIDATION_REGEX =
            "^(?:(?:31(0[13578]|1[02]))|" // Matches 31st for months: Jan, Mar, May, Jul, Aug, Oct, Dec
                    + "(?:30(0[13-9]|1[0-2]))|" // Matches 30th for all months except Feb (02)
                    + "(?:29(02)(?:(?:1[6-9]|[2-9]\\d)" // Matches 29th Feb ONLY if year is a leap year
                    + "(?:0[48]|[2468][048]|[13579][26])" // Leap year check for YYYY divisible by 4
                    + "|(?:16|[2468][048]|[3579][26])00))|" // Extra leap year check for century years
                    + "(?:0[1-9]|1\\d|2[0-8])" // Matches 01-28 for all valid months
                    + "(0[1-9]|1[0-2]))" // Matches valid months 01-12
                    + "([1-9]\\d{3})" // Matches a valid year (1000-9999)
                    + "(\\d{4})$"; // Matches the last 4 digits (any number from 0000-9999)


    public final String value;

    /**
     * Constructs an {@code Identification} using a start date and phone number.
     *
     * @param date  A valid start date.
     * @param phone A valid phone number.
     */
    public Identification(StartDate date, Phone phone) {
        requireNonNull(date);
        requireNonNull(phone);
        // Date and phone format have already been validated
        value = date.toWithoutSlashString()
                + phone.getLastFourDigitsString();
    }


    /**
     * Constructs an {@code Identification} with the given ID string.
     *
     * @param id The identification string. Cannot be null.
     */
    public Identification(String id) {
        requireNonNull(id);
        value = id;
    }


    /**
     * Returns true if a given string is a valid ID.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    //This method will be called by the Commands and assumes that isValidId has been used to check
    //Validity of ID
    public static Reservation getReservationUsingId(Identification id, Model model) throws CommandException {
        List<Reservation> overallList = model.getOverallReservationList();

        return overallList.stream().filter(r -> r.getId().equals(id))
                .findFirst().orElseThrow(() -> new CommandException("Input reservation id does not exist."));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Identification)) {
            return false;
        }

        Identification otherId = (Identification) other;
        return value.equals(otherId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
