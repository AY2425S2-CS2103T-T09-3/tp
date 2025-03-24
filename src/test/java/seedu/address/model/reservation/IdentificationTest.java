package seedu.address.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class IdentificationTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void createIdentificationFromDateAndPhone_success() {
        // Use only today's or tomorrow's date
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // Test for both valid StartDate cases
        testIdentificationForDate(today.format(FORMATTER), "91234567");
        testIdentificationForDate(tomorrow.format(FORMATTER), "87654321");
    }

    private void testIdentificationForDate(String validDate, String phoneNumber) {
        // Given a valid StartDate and valid Phone
        StartDate date = new StartDate(validDate);
        Phone phone = new Phone(phoneNumber);

        // Expected ID should be date without slashes + last 4 digits of phone
        String expectedId = validDate.replace("/", "") + phone.getLastFourDigitsString();

        // Execute the logic that should be tested
        Identification id = new Identification(date, phone);

        // Assert that the generated ID is correct
        assertNotNull(id);
        assertEquals(expectedId, id.value);
    }
}
