package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reservation identified "
            + "by the index number used in the displayed reservation list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters:  ID ( [6 figures of date (ie : ddMMyyyy)) of TODAY or TOMORROW] "
            + "+ [last 4 digits of customer phone number (ie:xxxx)])"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_PAX + "PAX] "
            + "[" + PREFIX_TABLE + "TABLE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_RESERVATION_SUCCESS = "Edited reservation: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESERVATION = "This reservation already exists in the address book.";

    private final Identification id;
    private final EditReservationDescriptor editReservationDescriptor;

    /**
     * @param id                        of the reservation  in the filtered reservation list to edit
     * @param editReservationDescriptor details to edit the reservation with
     */
    public EditCommand(Identification id, EditReservationDescriptor editReservationDescriptor) {
        requireNonNull(id);
        requireNonNull(editReservationDescriptor);

        this.id = id;
        this.editReservationDescriptor = new EditReservationDescriptor(editReservationDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Reservation reservationToEdit = Identification.getReservationUsingId(id, model);
        Reservation editedReservation = createEditedReservation(reservationToEdit, editReservationDescriptor);

        if (!reservationToEdit.isSameReservation(editedReservation) && model.hasReservation(editedReservation)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESERVATION);
        }

        model.setReservation(reservationToEdit, editedReservation);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_RESERVATION_SUCCESS, Messages.format(editedReservation)));
    }

    /**
     * Creates and returns a {@code Reservation} with the details of {@code ReservationToEdit}
     * edited with {@code editReservationDescriptor}.
     */
    private static Reservation createEditedReservation(Reservation reservationToEdit,
                                                       EditReservationDescriptor editReservationDescriptor) {
        assert reservationToEdit != null;

        Name updatedName = editReservationDescriptor.getName().orElse(reservationToEdit.getName());
        Phone updatedPhone = editReservationDescriptor.getPhone().orElse(reservationToEdit.getPhone());
        StartDate updatedDate = editReservationDescriptor.getDate().orElse(reservationToEdit.getDate());
        StartTime updatedTime = editReservationDescriptor.getTime().orElse(reservationToEdit.getTime());
        Duration updatedDuration = editReservationDescriptor.getDuration().orElse(reservationToEdit.getDuration());
        Pax updatedPax = editReservationDescriptor.getPax().orElse(reservationToEdit.getPax());
        Table updatedTable = editReservationDescriptor.getTable().orElse(reservationToEdit.getTable());
        Remark updatedRemark = reservationToEdit.getRemark();
        Set<Tag> updatedTags = editReservationDescriptor.getTags().orElse(reservationToEdit.getTags());
        Identification id = new Identification(updatedDate, updatedPhone);
        Boolean isPaid = reservationToEdit.getIsPaid();

        return new Reservation(updatedName, updatedPhone, updatedDate, updatedTime, updatedDuration, updatedPax,
                updatedTable, updatedRemark, updatedTags, id, isPaid);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return id.equals(otherEditCommand.id)
                && editReservationDescriptor.equals(otherEditCommand.editReservationDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identification", id)
                .add("editPersonDescriptor", editReservationDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the reservation with. Each non-empty field value will replace the
     * corresponding field value of the reservation.
     */
    public static class EditReservationDescriptor {
        private Name name;
        private Phone phone;
        private StartDate date;
        private StartTime time;
        private Duration duration;
        private Pax pax;
        private Table table;
        private Set<Tag> tags;
        private Identification id;

        public EditReservationDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditReservationDescriptor(EditReservationDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setDuration(toCopy.duration);
            setPax(toCopy.pax);
            setTable(toCopy.table);
            setTags(toCopy.tags);
            setId(toCopy.id);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, date, time, duration, pax, table, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setDate(StartDate date) {
            this.date = date;
        }

        public Optional<StartDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(StartTime time) {
            this.time = time;
        }

        public Optional<StartTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        public void setPax(Pax pax) {
            this.pax = pax;
        }

        public Optional<Pax> getPax() {
            return Optional.ofNullable(pax);
        }

        public void setTable(Table table) {
            this.table = table;
        }

        public Optional<Table> getTable() {
            return Optional.ofNullable(table);
        }

        public void setId(Identification id) {
            this.id = id;
        }

        public Optional<Identification> getId() {
            return Optional.ofNullable(id);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReservationDescriptor)) {
                return false;
            }

            EditReservationDescriptor otherEditReservationDescriptor = (EditReservationDescriptor) other;
            return Objects.equals(name, otherEditReservationDescriptor.name)
                    && Objects.equals(phone, otherEditReservationDescriptor.phone)
                    && Objects.equals(date, otherEditReservationDescriptor.date)
                    && Objects.equals(time, otherEditReservationDescriptor.time)
                    && Objects.equals(duration, otherEditReservationDescriptor.duration)
                    && Objects.equals(pax, otherEditReservationDescriptor.pax)
                    && Objects.equals(table, otherEditReservationDescriptor.table)
                    && Objects.equals(tags, otherEditReservationDescriptor.tags);
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
}
