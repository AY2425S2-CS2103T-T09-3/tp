package seedu.address.logic.commands;


import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reservation.*;
import seedu.address.model.tag.Tag;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the reservation as paid "
            + "at the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Successfully marks the reservation";
    public final Identification id;
    public MarkCommand(Identification id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //check if identification exist in the list and  ask model to  mark the given reservation as paid.
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        Reservation reservationToMark = lastShownList.stream().filter(r -> r.getId().equals(id))
                .findFirst().orElseThrow(() -> new CommandException("Input reservation id does not exist."));

        //identification id exist:
        Name name = reservationToMark.getName();
        Phone phone = reservationToMark.getPhone();
        StartDate startDate = reservationToMark.getDate();
        StartTime startTime = reservationToMark.getTime();
        Duration duration = reservationToMark.getDuration();
        Pax pax = reservationToMark.getPax();
        Table table = reservationToMark.getTable();
        Remark remark = reservationToMark.getRemark();
        Set<Tag> tags = reservationToMark.getTags();
        Identification id = reservationToMark.getId();

        Reservation markedReservation = new Reservation(name, phone, startDate, startTime
                , duration, pax, table, remark, tags, id, true);

        model.setReservation(reservationToMark, markedReservation);
        //to be implemented
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
