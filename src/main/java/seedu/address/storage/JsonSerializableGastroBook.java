package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.GastroBook;
import seedu.address.model.ReadOnlyGastroBook;
import seedu.address.model.reservation.Reservation;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "gastrobook")
class JsonSerializableGastroBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedReservation> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableGastroBook(@JsonProperty("persons") List<JsonAdaptedReservation> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableGastroBook(ReadOnlyGastroBook source) {
        persons.addAll(source.getReservationList()
                .stream().map(JsonAdaptedReservation::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GastroBook toModelType() throws IllegalValueException {
        GastroBook gastroBook = new GastroBook();
        for (JsonAdaptedReservation jsonAdaptedReservation : persons) {
            Reservation reservation = jsonAdaptedReservation.toModelType();
            if (gastroBook.hasReservation(reservation)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            gastroBook.addReservation(reservation);
        }
        return gastroBook;
    }

}
