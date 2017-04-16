# Address Book
### Purpose
The purpose of this program is to allow the creation, exporting, updating and viewing of address books.

### Features
* Allows to store information and people
* Ability to add/remove records, along with modifying
* Capable of exporting and opening previous databases
* Fully error-trapped user interactions
* Structured program architecture/design

### Design
The program has been designed with use of OOP principles. The `DataCheck` class verifies all data, and acts as a utility class. The `PersonRecord` class is an object representing each person, and is encapsulated throughout the program. `DatabaseApp` is used to handle File IO interactions (saving/opening databases with error-traps), along with setting up the JFrame. Lastly, the `AddressBook` class deals with the PersonRecords, and allows to go forward, backwards, and add and remove records. Feel free to contact me if you are interested in the UML for this project.

### How to Run
To run this program, simply execute the `DatabaseApp.java` file.

### Documentation
This program comes with full JavaDoc documentation, located in the `doc/` folder.