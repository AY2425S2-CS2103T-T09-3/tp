package seedu.address.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reservation.Name;
import seedu.address.model.reservation.Person;
import seedu.address.model.reservation.Phone;

/**
 * Represents a list of persons who have made reservations.
 * Tracks booking frequency and regular customer status.
 */
public class PersonsList {
    public static final int REGULAR_CUSTOMER_THRESHOLD = 3;
    private static final Logger logger = LogsCenter.getLogger(PersonsList.class);
    private static final Path PERSONS_FILE_PATH = Paths.get("data", "personslist.json");
    private static ArrayList<Person> personsList;

    /**
     * Initializes a PersonsList.
     */
    public PersonsList() {
        // Create data directory if it doesn't exist
        try {
            Files.createDirectories(PERSONS_FILE_PATH.getParent());
        } catch (IOException e) {
            logger.warning("Could not create data directory: " + e.getMessage());
        }

        // Initialize with empty list
        this.personsList = new ArrayList<>();

        // Load from file if it exists
        loadListFromFile();
    }


    /**
     * Updates a person in the file if they exist, otherwise adds them.
     * This method preserves ALL existing entries in the file.
     */
    private void updatePhoneInFileAndKeepOthers(Person person) {
        try {
            // First check if file exists and has content
            File file = PERSONS_FILE_PATH.toFile();
            if (!file.exists() || file.length() == 0) {
                // Create new file with just this person
                ArrayList<Person> newList = new ArrayList<>();
                newList.add(person);

                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(file, newList);

                logger.info("Created new file with first person: " + person.getName().fullName);
                return;
            }

            // Read the existing file content
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(ArrayList.class, Person.class);

            ArrayList<Person> filePersons;
            try {
                filePersons = mapper.readValue(file, listType);
            } catch (Exception e) {
                logger.warning("Could not parse file, resetting: " + e.getMessage());
                ArrayList<Person> newList = new ArrayList<>();
                newList.add(person);
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(file, newList);
                return;
            }

            // Check what kind of operation this is
            boolean isUpdateForExisting = false;
            String targetPhone = person.getPhone().value;

            // Check if this phone number exists in the file already
            for (Person p : filePersons) {
                if (p.getPhone().value.equals(targetPhone)) {
                    isUpdateForExisting = true;
                    break;
                }
            }

            // make a copy of the existing list
            ArrayList<Person> updatedList = new ArrayList<>(filePersons);

            if (isUpdateForExisting) {
                // For existing phone numbers, add the updated entry
                updatedList.add(person);
                logger.info("Added updated entry for existing person: " + person.getName().fullName);
            } else {
                // For new phone numbers, add to the list
                updatedList.add(person);
                logger.info("Added new person to file: " + person.getName().fullName);
            }

            // Write the updated list
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, updatedList);

            logger.info("File now contains " + updatedList.size() + " entries (previous: " + filePersons.size() + ")");

        } catch (IOException e) {
            logger.severe("Failed to update file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns true if a person with the same phone number exists in the list.
     */
    public boolean hasPerson(Person person) {
        if (person == null) {
            throw new NullPointerException();
        }

        for (Person p : personsList) {
            if (p.getPhone().value.equals(person.getPhone().value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a person with the given phone number.
     */
    public static Person getPerson(Phone phone) {
        if (phone == null) {
            throw new NullPointerException();
        }

        for (Person p : personsList) {
            if (p.getPhone().value.equals(phone.value)) {
                return p;
            }
        }
        return null;
    }


    /**
     * Adds a person to the persons list and to the file.
     */
    public void addPerson(Person p) {
        personsList.add(p);
        updatePhoneInFileAndKeepOthers(p);
        logger.info("Added person: " + p.getName().fullName);
    }


    /**
     * Records a booking for a person, updating their counter and regular status if needed.
     */
    public Person recordBooking(Name name, Phone phone) {
        logger.info("Recording booking for " + name.fullName + " with phone " + phone.value);

        // Check if person already exists
        Person existingPerson = null;
        int existingIndex = -1;

        for (int i = 0; i < personsList.size(); i++) {
            if (personsList.get(i).getPhone().value.equals(phone.value)) {
                existingPerson = personsList.get(i);
                existingIndex = i;
                logger.info("Found existing person with phone " + phone.value
                        + ", current counter: " + existingPerson.getCounter());
                break;
            }
        }

        Person resultPerson;

        if (existingPerson != null) {
            // Update existing person
            int newCount = existingPerson.getCounter() + 1;
            boolean isNowRegular = newCount >= REGULAR_CUSTOMER_THRESHOLD;

            logger.info("Incrementing counter from " + existingPerson.getCounter() + " to " + newCount);

            // Create updated person
            resultPerson = new Person(name, phone);
            resultPerson.setCounter(newCount);
            resultPerson.setIsRegular(isNowRegular);

            // Replace in memory list
            personsList.set(existingIndex, resultPerson);
        } else {
            // Create new person
            resultPerson = new Person(name, phone);
            resultPerson.setCounter(1);
            resultPerson.setIsRegular(false);

            logger.info("Creating new person with phone " + phone.value);

            // Add to memory list
            personsList.add(resultPerson);
        }

        // save the entire list to file after updating memory
        savePersonsToFile();

        return resultPerson;
    }

    /**
     * Saves the current persons list to file.
     */
    public void savePersonsToFile() {
        try {
            logger.info("Saving persons list with " + personsList.size() + " entries");

            // Create parent directories if they don't exist
            Files.createDirectories(PERSONS_FILE_PATH.getParent());

            // Create the file if it doesn't exist
            File file = PERSONS_FILE_PATH.toFile();
            if (!file.exists()) {
                file.createNewFile();
                logger.info("Created new persons file");
            }

            // Write the current in-memory list to file
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, personsList);

            logger.info("Successfully saved persons list to file");
        } catch (IOException e) {
            logger.severe("Failed to save persons list: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * Loads the persons list from file.
     * For entries with the same phone number, picks the one with the highest counter.
     */
    void loadListFromFile() {
        try {
            File personsFile = PERSONS_FILE_PATH.toFile();
            if (personsFile.exists() && personsFile.length() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                CollectionType listType = mapper.getTypeFactory()
                        .constructCollectionType(ArrayList.class, Person.class);

                ArrayList<Person> loadedPersons = mapper.readValue(personsFile, listType);
                if (loadedPersons != null) {
                    // for debug
                    logger.info("Read " + loadedPersons.size() + " total entries from file");
                    for (Person p : loadedPersons) {
                        logger.info("  Entry: " + p.getName().fullName
                                + ", phone: " + p.getPhone().value
                                + ", counter: " + p.getCounter());
                    }

                    // For each phone number, keep the entry with the highest counter
                    java.util.Map<String, Person> phoneToPersonMap = new java.util.HashMap<>();

                    for (Person p : loadedPersons) {
                        String phoneValue = p.getPhone().value;

                        if (!phoneToPersonMap.containsKey(phoneValue)
                                || p.getCounter() > phoneToPersonMap.get(phoneValue).getCounter()) {
                            phoneToPersonMap.put(phoneValue, p);
                        }
                    }

                    // Convert the map values to list
                    this.personsList = new ArrayList<>(phoneToPersonMap.values());

                    logger.info("Loaded " + personsList.size()
                            + " unique persons from file, prioritizing highest counter values");
                    for (Person p : personsList) {
                        logger.info("  Loaded: " + p.getName().fullName
                                + ", phone: " + p.getPhone().value
                                + ", counter: " + p.getCounter());
                    }
                }
            } else {
                // Create empty file if it doesn't exist
                if (!personsFile.exists()) {
                    Files.createDirectories(personsFile.getParentFile().toPath());
                    personsFile.createNewFile();

                    // Initialize with empty array
                    FileWriter writer = new FileWriter(personsFile);
                    writer.write("[]");
                    writer.close();

                    logger.info("Created new empty persons file with empty array");
                }

                this.personsList = new ArrayList<>();
            }
        } catch (IOException e) {
            logger.warning("Error loading persons list: " + e.getMessage());
            e.printStackTrace();
            this.personsList = new ArrayList<>();
        }
    }

    /**
     * Removes a person from the list.
     */
    public void removePerson(Person key) {
        boolean removed = personsList.remove(key);

        if (removed) {
            // Save updated list to file
            savePersonsToFile();
            logger.info("Removed person: " + key.getName().fullName);
        }
    }

    /**
     * Returns a copy of the persons list.
     */
    public ArrayList<Person> getPersonsList() {
        return new ArrayList<>(personsList);
    }

    /**
     * Returns a list of all regular customers (persons with isRegular set to true).
     */
    public ArrayList<Person> getRegularCustomers() {
        ArrayList<Person> regulars = new ArrayList<>();
        for (Person person : personsList) {
            if (person.isRegular()) {
                regulars.add(person);
            }
        }
        return regulars;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonsList)) {
            return false;
        }

        PersonsList otherPersonsList = (PersonsList) other;
        return personsList.equals(otherPersonsList.personsList);
    }

    @Override
    public String toString() {
        int regularCount = 0;
        for (Person p : personsList) {
            if (p.isRegular()) {
                regularCount++;
            }
        }
        return personsList.size() + " persons, " + regularCount + " regulars";
    }
}
