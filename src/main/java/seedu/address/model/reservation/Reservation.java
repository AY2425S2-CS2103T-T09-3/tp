package seedu.address.model.reservation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reservation {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final StartDate date;
    private final StartTime time;
    private final Duration duration;
    private final Pax pax;
    private final Table table;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();
    private final Identification id;

    /**
     * Every field must be present and not null.
     */
    public Reservation(Name name, Phone phone, StartDate date, StartTime time,
                       Duration duration, Pax pax, Table table, Remark remark, Set<Tag> tags, Identification id) {
        requireAllNonNull(name, phone, date, time, duration, pax, table);
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.pax = pax;
        this.table = table;
        this.remark = remark;
        this.tags.addAll(tags);
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public StartDate getDate() {
        return date;
    }

    public StartTime getTime() {
        return time;
    }

    public Duration getDuration() {
        return duration;
    }

    public Pax getPax() {
        return pax;
    }

    public Table getTable() {
        return table;
    }

    public Remark getRemark() {
        return remark;
    }

    public Identification getId() {
        return id;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameReservation(Reservation otherReservation) {
        if (otherReservation == this) {
            return true;
        }

        return otherReservation != null
                && otherReservation.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Reservation)) {
            return false;
        }

        Reservation otherReservation = (Reservation) other;
        return name.equals(otherReservation.name)
                && phone.equals(otherReservation.phone)
                && date.equals(otherReservation.date)
                && time.equals(otherReservation.time)
                && duration.equals(otherReservation.duration)
                && pax.equals(otherReservation.pax)
                && table.equals(otherReservation.table)
                && tags.equals(otherReservation.tags)
                && id.equals(otherReservation.id);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, date, time, duration, pax, table, tags, id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("date", date)
                .add("time", time)
                .add("duration", duration)
                .add("pax", pax)
                .add("table", table)
                .add("tags", tags)
                .add("id", id)
                .toString();
    }
}
