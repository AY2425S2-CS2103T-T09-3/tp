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
    private boolean isPaid;

    /**
     * Every field must be present and not null.
     */
    public Reservation(Name name, Phone phone, StartDate date, StartTime time, Duration duration, Pax pax, Table table,
                       Remark remark, Set<Tag> tags, Identification id, boolean isPaid) {
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
        this.isPaid = isPaid;
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

    public boolean getIsPaid() {
        return isPaid;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Marks this reservation as paid by creating a new {@code Reservation} instance
     * with the paid status set to {@code true}.
     *
     * @return A new {@code Reservation} instance with the same details but marked as paid.
     */
    public Reservation toPaid() {
        return new Reservation(this.name, this.phone, this.date, this.time,
                this.duration, this.pax, this.table, this.remark, this.tags,
                this.id, true);
    }


    /**
     * Unmarks this reservation as not paid by creating a new {@code Reservation} instance
     * with the paid status set to {@code false}.
     *
     * @return A new {@code Reservation} instance with the same details but unmarked as not paid.
     */
    public Reservation toUnpaid() {
        return new Reservation(this.name, this.phone, this.date, this.time,
                this.duration, this.pax, this.table, this.remark, this.tags,
                this.id, false);
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
                && this.equals(otherReservation);
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
        return phone.getLastFourDigitsString().equals(otherReservation.phone.getLastFourDigitsString())
                && date.equals(otherReservation.date)
                && time.equals(otherReservation.time);
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
                .add("remark", remark)
                .add("payment", isPaid)
                .add("id", id)
                .toString();
    }
}
