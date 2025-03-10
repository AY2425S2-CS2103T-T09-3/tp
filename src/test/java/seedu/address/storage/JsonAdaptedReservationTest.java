package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalReservations.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;


public class JsonAdaptedReservationTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedReservation person = new JsonAdaptedReservation(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {

    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {

    }
}
