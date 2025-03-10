package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditReservationDescriptor;
import seedu.address.model.reservation.Duration;
import seedu.address.model.reservation.Identification;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Pax;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Reservation;
import seedu.address.model.reservation.StartDate;
import seedu.address.model.reservation.StartTime;
import seedu.address.model.reservation.Table;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditReservationDescriptorBuilder {

    private EditReservationDescriptor descriptor;

    public EditReservationDescriptorBuilder() {
        descriptor = new EditReservationDescriptor();
    }

    public EditReservationDescriptorBuilder(EditReservationDescriptor descriptor) {
        this.descriptor = new EditReservationDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditReservationDescriptorBuilder(Reservation reservation) {
        descriptor = new EditReservationDescriptor();
        descriptor.setName(reservation.getName());
        descriptor.setPhone(reservation.getPhone());
        descriptor.setDate(reservation.getDate());
        descriptor.setTime(reservation.getTime());
        descriptor.setDuration(reservation.getDuration());
        descriptor.setPax(reservation.getPax());
        descriptor.setTable(reservation.getTable());
        descriptor.setTags(reservation.getTags());
        descriptor.setId(reservation.getId());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withDate(String date) {
        descriptor.setDate(new StartDate(date));
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withTime(String time) {
        descriptor.setTime(new StartTime(time));
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withDuration(String duration) {
        descriptor.setDuration(new Duration(duration));
        return this;
    }

    /**
     * Sets the {@code Pax} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withPax(String pax) {
        descriptor.setPax(new Pax(pax));
        return this;
    }

    /**
     * Sets the {@code Table} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withTable(String table) {
        descriptor.setTable(new Table(table));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditReservationDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Identification} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withId(String id) {
        descriptor.setId(new Identification(id));
        return this;
    }

    public EditCommand.EditReservationDescriptor build() {
        return descriptor;
    }
}
