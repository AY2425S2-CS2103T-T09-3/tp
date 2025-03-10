package seedu.address.model.reservation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReservations.ALICE;
import static seedu.address.testutil.TypicalReservations.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ReservationBuilder;

public class ReservationTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Reservation reservation = new ReservationBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> reservation.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameReservation(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameReservation(null));

        // same name, all other attributes different -> returns true

        // different name, all other attributes same -> returns false

        // name differs in case, all other attributes same -> returns false
        Reservation editedBob = new ReservationBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameReservation(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new ReservationBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameReservation(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Reservation aliceCopy = new ReservationBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Reservation editedAlice = new ReservationBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ReservationBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ReservationBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        /*
        String expected = Reservation.class.getCanonicalName() + "{name=" + ALICE.getName()
        + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
         */
    }
}
