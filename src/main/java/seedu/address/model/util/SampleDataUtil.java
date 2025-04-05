package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.GastroBook;
import seedu.address.model.ReadOnlyGastroBook;
import seedu.address.model.reservation.Duration;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartDate;
import seedu.address.model.reservation.StartTime;
import seedu.address.model.reservation.Table;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static Reservation[] getSampleReservations() {
        return new Reservation[] {
            new Reservation(
                        new Name("Vincent Oh"),
                        new Phone("87438807"),
                        new StartDate("01/04/2025"),
                        new StartTime("1830"),
                        new Duration("2"),
                        new Pax("4"),
                        new Table("A1"),
                        new Remark("Anniversary dinner"),
                        getTagSet("VIP", "Window"),
                        new Identification("1004202588071830"),
                        false
                ),
            new Reservation(
                        new Name("Damith the Legend"),
                        new Phone("99272758"),
                        new StartDate("01/04/2025"),
                        new StartTime("1900"),
                        new Duration("2"),
                        new Pax("2"),
                        new Table("B3"),
                        new Remark("No nuts - allergy"),
                        getTagSet("VIP"),
                        new Identification("0104202527581900"),
                        true
                ),
            new Reservation(
                        new Name("Ronaldo"),
                        new Phone("77777777"),
                        new StartDate("25/03/2025"),
                        new StartTime("1230"),
                        new Duration("2"),
                        new Pax("6"),
                        new Table("C2"),
                        new Remark("Birthday celebration"),
                        getTagSet("Birthday", "Large"),
                        new Identification("2503202577771215"),
                        false
                )
        };
    }

    public static ReadOnlyGastroBook getSampleAddressBook() {
        try {
            // Enable bypass for sample data
            StartDate.enableBypassForSampleData();

            GastroBook sampleAb = new GastroBook();
            for (Reservation sampleReservation : getSampleReservations()) {
                sampleAb.addReservation(sampleReservation);
            }
            return sampleAb;
        } finally {
            // Always disable bypass after creating sample data
            StartDate.disableBypassForSampleData();
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
