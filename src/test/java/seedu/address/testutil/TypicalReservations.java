package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.reservation.Reservation;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalReservations {

    public static final Reservation ALICE = new ReservationBuilder().withName("Alice Pauline")
            .withPhone("94351253").withRemark("She likes aardvarks.")
            .withTags("friends").build();
    public static final Reservation BENSON = new ReservationBuilder().withName("Benson Meier")
            .withRemark("He can't take beer!")
            .withTags("owesMoney", "friends").build();
    public static final Reservation CARL = new ReservationBuilder().withName("Carl Kurz").withPhone("95352563").build();
    public static final Reservation DANIEL = new ReservationBuilder().withName("Daniel Meier").withPhone("87652533")
            .withTags("friends").build();
    public static final Reservation ELLE = new ReservationBuilder().withName("Elle Meyer").withPhone("9482224")
            .build();
    public static final Reservation FIONA = new ReservationBuilder().withName("Fiona Kunz").withPhone("9482427")
            .build();
    public static final Reservation GEORGE = new ReservationBuilder().withName("George Best").withPhone("9482442")
            .build();

    // Manually added
    public static final Reservation HOON = new ReservationBuilder().withName("Hoon Meier").withPhone("8482424")
            .build();
    public static final Reservation IDA = new ReservationBuilder().withName("Ida Mueller").withPhone("8482131")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Reservation AMY = new ReservationBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Reservation BOB = new ReservationBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalReservations() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Reservation reservation : getTypicalPersons()) {
            ab.addPerson(reservation);
        }
        return ab;
    }

    public static List<Reservation> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
