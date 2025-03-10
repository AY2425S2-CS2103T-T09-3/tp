
package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Reservation}.
 */
class JsonAdaptedReservation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reservation's %s field is missing!";

    private final String name;
    private final String phone;
    private final String date;
    private final String time;
    private final String duration;
    private final String pax;
    private final String table;
    private final String remark;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String id;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedReservation(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                  @JsonProperty("date") String date, @JsonProperty("time") String time,
                                  @JsonProperty("duration") String duration, @JsonProperty("pax") String pax,
                                  @JsonProperty("table") String table, @JsonProperty("remark") String remark,
                                  @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("id") String id) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.pax = pax;
        this.table = table;
        this.remark = remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.id = id;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedReservation(Reservation source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        date = source.getDate().value;
        time = source.getTime().value;
        duration = source.getDuration().value;
        pax = source.getPax().value;
        table = source.getTable().value;
        remark = source.getRemark().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        id = source.getId().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Reservation toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDate.class.getSimpleName()));
        }
        if (!StartDate.isValidDate(date)) {
            throw new IllegalValueException(StartDate.MESSAGE_CONSTRAINTS);
        }
        final StartDate modelDate = new StartDate(date);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartTime.class.getSimpleName()));
        }
        if (!StartTime.isValidTime(time)) {
            throw new IllegalValueException(StartTime.MESSAGE_CONSTRAINTS);
        }
        final StartTime modelTime = new StartTime(time);

        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        if (!Duration.isValidDuration(duration)) {
            throw new IllegalValueException(Duration.MESSAGE_CONSTRAINTS);
        }
        final Duration modelDuration = new Duration(duration);

        if (pax == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Pax.class.getSimpleName()));
        }
        if (!Pax.isValidPax(pax)) {
            throw new IllegalValueException(Pax.MESSAGE_CONSTRAINTS);
        }
        final Pax modelPax = new Pax(pax);

        if (table == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Table.class.getSimpleName()));
        }
        if (!Table.isValidTable(table)) {
            throw new IllegalValueException(Table.MESSAGE_CONSTRAINTS);
        }
        final Table modelTable = new Table(table);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Identification.class.getSimpleName()));
        }

        if (!Identification.isValidId(id)) {
            throw new IllegalValueException(Identification.MESSAGE_CONSTRAINTS);
        }
        final Identification modelId = new Identification(id);

        return new Reservation(modelName, modelPhone, modelDate, modelTime, modelDuration, modelPax, modelTable,
                modelRemark, modelTags, modelId);
    }
}
