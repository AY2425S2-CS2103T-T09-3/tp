package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reservation.Reservation;


/**
 * A UI component that displays information of a {@code Person}.
 */
public class ReservationCard extends UiPart<Region> {

    private static final String FXML = "ReservationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reservation reservation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label duration;
    @FXML
    private Label pax;
    @FXML
    private Label table;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private Label reservationId;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ReservationCard(Reservation reservation, int displayedIndex) {
        super(FXML);
        System.out.println(reservation.getDate().toString());
        this.reservation = reservation;
        id.setText(displayedIndex + ". ");
        name.setText(reservation.getName().fullName);
        phone.setText(reservation.getPhone().value);
        date.setText(reservation.getDate().value);
        time.setText(reservation.getTime().value);
        duration.setText(reservation.getDuration().value);
        pax.setText(reservation.getPax().value);
        table.setText(reservation.getTable().value);
        remark.setText(reservation.getRemark().value);
        reservation.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        reservationId.setText(reservation.getId().value);
        System.out.println(reservation.getDate().toString());
    }
}
