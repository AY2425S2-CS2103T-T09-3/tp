package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reservation.Reservation;

/**
 * Panel containing the list of persons.
 */
public class ReservationListPanel extends UiPart<Region> {
    private static final String FXML = "ReservationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReservationListPanel.class);

    @FXML
    private ListView<Reservation> reservationListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ReservationListPanel(ObservableList<Reservation> reservationList) {
        super(FXML);
        SortedList<Reservation> sortedList = new SortedList<>(reservationList);
        //comparator to sort by time
        sortedList.setComparator(Comparator.comparing(r -> r.getTime().value));
        reservationListView.setItems(sortedList);
        reservationListView.setCellFactory(listView -> new ReservationListViewCell());

        // Add listener to detect changes to the reservation list and refresh the UI
        reservationList.addListener(new ListChangeListener<Reservation>() {
            @Override
            public void onChanged(Change<? extends Reservation> change) {
                // Call refreshListView when there are changes in the list
                refreshListView();
            }
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ReservationListViewCell extends ListCell<Reservation> {
        @Override
        protected void updateItem(Reservation reservation, boolean empty) {
            super.updateItem(reservation, empty);

            if (empty || reservation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReservationCard(reservation, getIndex() + 1).getRoot());
            }
        }
    }

    private void refreshListView() {
        reservationListView.refresh();
    }
}
