package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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

    public static Reservation[] getSamplePersons() {
        return new Reservation[] {
            new Reservation(new Name("Alex Yeoh"), new Phone("87438807"), new StartDate("01/03/2025"),
                    new StartTime("1800"), new Duration("2"), new Pax("2"), new Table("A1"), EMPTY_REMARK,
                getTagSet("friends"), new Identification("0001"))
                /*
            new Reservation(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), EMPTY_REMARK,
                getTagSet("colleagues", "friends")),
            new Reservation(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_REMARK,
                getTagSet("neighbours")),
            new Reservation(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), EMPTY_REMARK,
                getTagSet("family")),
            new Reservation(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), EMPTY_REMARK,
                getTagSet("classmates")),
            new Reservation(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), EMPTY_REMARK,
                getTagSet("colleagues"))*/
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Reservation sampleReservation : getSamplePersons()) {
            sampleAb.addPerson(sampleReservation);
        }
        return sampleAb;
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
