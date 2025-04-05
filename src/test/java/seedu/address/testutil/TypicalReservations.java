package seedu.address.testutil;

/*
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.GastroBook;
import seedu.address.model.reservation.Duration;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Remark;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartDate;
import seedu.address.model.reservation.StartTime;
import seedu.address.model.reservation.Table;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalReservations {
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    private static LocalDate today = LocalDate.now();
    private static LocalDate tomorrow = today.plusDays(1);

    //    public static final Reservation ALICE = new ReservationBuilder().withName("Alice Pauline")
    //            .withPhone("94351253").withRemark("She likes aardvarks.")
    //            .withTags("friends").build();
    //    public static final Reservation BENSON = new ReservationBuilder().withName("Benson Meier")
    //            .withRemark("He can't take beer!")
    //            .withTags("owesMoney", "friends").build();
    //    public static final Reservation CARL = new ReservationBuilder().withName("Carl Kurz")
    //    .withPhone("95352563").build();
    //    public static final Reservation DANIEL = new ReservationBuilder().withName("Daniel Meier")
    //    .withPhone("87652533")
    //            .withTags("friends").build();
    //    public static final Reservation ELLE = new ReservationBuilder().withName("Elle Meyer")
    //    .withPhone("9482224")
    //            .build();
    //    public static final Reservation FIONA = new ReservationBuilder().withName("Fiona Kunz")
    //    .withPhone("9482427")
    //            .build();
    //    public static final Reservation GEORGE = new ReservationBuilder().withName("George Best")
    //    .withPhone("9482442")
    //            .build();
    //
    //    // Manually added
    //    public static final Reservation HOON = new ReservationBuilder().withName("Hoon Meier").withPhone("8482424")
    //            .build();
    //    public static final Reservation IDA = new ReservationBuilder().withName("Ida Mueller").withPhone("8482131")
    //            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    // TODO work on the ReservationBuilder() constructor
    //    public static final Reservation AMY = new ReservationBuilder()
    //    .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
    //            .withTags(VALID_TAG_FRIEND).build1();
    //    public static final Reservation BOB = new ReservationBuilder()
    //    .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withTime(VALID_TIME_BOB)
    //            .build2();

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static String todayDate = today.format(formatter);
    private static String tomorrowDate = tomorrow.format(formatter);

    public static final Reservation ALICE = new ReservationBuilder(new Name("Alice Pauline"), new Phone("94351253"),
            new StartDate(todayDate), new StartTime("1800"), new Duration("2"), new Pax("2"),
            new Table("A1"), new Remark("She likes aardvarks."), new HashSet<>()).build();
    public static final Reservation BENSON = new ReservationBuilder(new Name("Benson Meier"), new Phone("93321423"),
            new StartDate(todayDate), new StartTime("1400"), new Duration("3"), new Pax("2"),
            new Table("A2"), new Remark("He can't take beer!"), new HashSet<>()).build();

    //the payment status of this reservation is set to true.
    public static final Reservation CARL = new ReservationBuilder(new Name("Carl Kurz"), new Phone("95352563"),
            new StartDate(tomorrowDate), new StartTime("1200"), new Duration("3"), new Pax("3"),
            new Table("A2"), new Remark("She can't take beer!"), new HashSet<>()).withPaymentStatus(true).build();

    public static final Reservation DANIEL = new ReservationBuilder(new Name("Daniel Meier"), new Phone("87652533"),
            new StartDate(todayDate), new StartTime("1200"), new Duration("1"), new Pax("3"),
            new Table("A2"), new Remark("She can't take beer!"), new HashSet<>()).build();

    public static final Reservation ELLE = new ReservationBuilder(new Name("Elle Meyer"), new Phone("9482224"),
            new StartDate(tomorrowDate), new StartTime("1200"), new Duration("1"), new Pax("3"),
            new Table("A2"), new Remark("She can't take beer!"), new HashSet<>()).build();

    public static final Reservation FIONA = new ReservationBuilder(new Name("Fiona Kunz"), new Phone("9482427"),
            new StartDate(todayDate), new StartTime("1200"), new Duration("1"), new Pax("3"),
            new Table("A2"), new Remark("She can't take beer!"), new HashSet<>())
            .withPaymentStatus(true).build();

    public static final Reservation GEORGE = new ReservationBuilder(new Name("George Best"), new Phone("9482442"),
            new StartDate(tomorrowDate), new StartTime("1200"), new Duration("1"), new Pax("3"),
            new Table("A2"), new Remark("She can't take beer!"), new HashSet<>()).build();

    public static final Reservation HOON = new ReservationBuilder(new Name("Hoon Meier"), new Phone("8482424"),
            new StartDate(tomorrowDate), new StartTime("1200"), new Duration("1"), new Pax("3"),
            new Table("A2"), new Remark("She can't take beer!"), new HashSet<>()).build();

    /*

    public static final Reservation BENSON = new ReservationBuilder( ).withName("Benson Meier")
            .withRemark("He can't take beer!")
            .withTags("owesMoney", "friends").build();
    public static final Reservation CARL = new ReservationBuilder( ).withName("Carl Kurz").withPhone("95352563")
    .build();
    public static final Reservation DANIEL = new ReservationBuilder( ).withName("Daniel Meier").withPhone("87652533")
            .withTags("friends").build();
    public static final Reservation ELLE = new ReservationBuilder(, , , , , , , , , ).withName("Elle Meyer")
    .withPhone("9482224")
            .build();
    public static final Reservation FIONA = new ReservationBuilder(, , , , , , , , , ).withName("Fiona Kunz")
    .withPhone("9482427")
            .build();
    public static final Reservation GEORGE = new ReservationBuilder(, , , , , , , , , ).withName("George Best")
    .withPhone("9482442")
            .build();

    // Manually added
    public static final Reservation HOON = new ReservationBuilder(, , , , , , , , , ).withName("Hoon Meier")
    .withPhone("8482424")
            .build();
    public static final Reservation IDA = new ReservationBuilder(, , , , , , , , , ).withName("Ida Mueller")
    .withPhone("8482131")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Reservation AMY = new ReservationBuilder(, , , , , , , , , ).withName(VALID_NAME_AMY)
    .withPhone(VALID_PHONE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Reservation BOB = new ReservationBuilder(, , , , , , , , , ).withName(VALID_NAME_BOB)
    .withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    */
    // public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalReservations() {} // prevents instantiation

    /**
     * Returns a {@code GastroBook} with all the typical persons.
     */
    public static GastroBook getTypicalGastroBook() {
        GastroBook ab = new GastroBook();
        for (Reservation reservation : getTypicalReservations()) {
            ab.addReservation(reservation);
        }
        return ab;
    }

    public static List<Reservation> getTypicalReservations() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON));
    }
}
