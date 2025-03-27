package seedu.address.model;

import java.util.ArrayList;

import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;
import seedu.address.model.reservation.Reservation;

/**
 * Manages operations related to PersonsList that involve coordination with Reservations.
 */
public class PersonsListManager {
    private final PersonsList personsList;

    public PersonsListManager(PersonsList personsList) {
        this.personsList = personsList;
    }

    /**
     * Updates person details in PersonsList when a reservation is edited.
     * @param oldReservation the reservation before editing
     * @param newReservation the reservation after editing
     */
    public void updatePersonsListAfterEdit(Reservation oldReservation, Reservation newReservation) {
        // Check if phone or name have been updated
        boolean phoneChanged = !oldReservation.getPhone().equals(newReservation.getPhone());
        boolean nameChanged = !oldReservation.getName().equals(newReservation.getName());

        // Only proceed if sth changed
        if (phoneChanged || nameChanged) {
            // Load current list from file
            personsList.loadListFromFile();

            // Get the current in-memory list after reloading
            ArrayList<Person> currentPersons = new ArrayList<>(personsList.getPersonsList());

            if (phoneChanged) {
                // Find and update the old phone entry if it exists
                Phone oldPhone = oldReservation.getPhone();
                Person oldPerson = null;

                for (Person person : currentPersons) {
                    //match by phone number
                    if (person.getPhone().value.equals(oldPhone.value)) {
                        oldPerson = person;
                        break;
                    }
                }

                if (oldPerson != null) {
                    // Create new person with new phone but same details eg counter/status
                    Person newPerson = new Person(newReservation.getName(), newReservation.getPhone());
                    newPerson.setCounter(oldPerson.getCounter());
                    newPerson.setIsRegular(oldPerson.isRegular());

                    // Remove old person from list
                    personsList.removePerson(oldPerson);

                    // Add new person to list
                    personsList.addPerson(newPerson);
                } else {
                    // Just add a new person if old one wasn't found
                    // shouldnt happen
                    Person newPerson = new Person(newReservation.getName(), newReservation.getPhone());
                    newPerson.setCounter(1);
                    newPerson.setIsRegular(false);
                    personsList.addPerson(newPerson);
                }
            } else if (nameChanged) {
                // Only name changed - update the existing record
                Phone currentPhone = newReservation.getPhone();
                Person existingPerson = null;

                for (Person person : currentPersons) {
                    if (person.getPhone().value.equals(currentPhone.value)) {
                        existingPerson = person;
                        break;
                    }
                }

                if (existingPerson != null) {
                    // Remove old entry
                    personsList.removePerson(existingPerson);

                    // Create updated entry with new name
                    Person updatedPerson = new Person(newReservation.getName(), currentPhone);
                    updatedPerson.setCounter(existingPerson.getCounter());
                    updatedPerson.setIsRegular(existingPerson.isRegular());

                    // Add updated entry
                    personsList.addPerson(updatedPerson);
                } else {
                    // Just add a new record if no existing one found
                    Person newPerson = new Person(newReservation.getName(), currentPhone);
                    newPerson.setCounter(1);
                    newPerson.setIsRegular(false);
                    personsList.addPerson(newPerson);
                }
            }

            //SAVE FILEE
            personsList.savePersonsToFile();
        }
    }
}
