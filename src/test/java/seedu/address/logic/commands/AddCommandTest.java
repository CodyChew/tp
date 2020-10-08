package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.investigationcase.Case;
import seedu.address.testutil.CaseBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullCase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_caseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Case validCase = new CaseBuilder().build();

        CommandResult commandResult = new AddCommand(validCase).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCase), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCase), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicateCase_throwsCommandException() {
        Case validCase = new CaseBuilder().build();
        AddCommand addCommand = new AddCommand(validCase);
        ModelStub modelStub = new ModelStubWithPerson(validCase);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CASE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Case alice = new CaseBuilder().withTitle("Alice").build();
        Case bob = new CaseBuilder().withTitle("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCase(Case investigationCase) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCase(Case investigationCase) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCase(Case target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCase(Case target, Case editedCase) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Case> getFilteredCaseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCaseList(Predicate<Case> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Case investigationCase;

        ModelStubWithPerson(Case investigationCase) {
            requireNonNull(investigationCase);
            this.investigationCase = investigationCase;
        }

        @Override
        public boolean hasCase(Case investigationCase) {
            requireNonNull(investigationCase);
            return this.investigationCase.isSameCase(investigationCase);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Case> personsAdded = new ArrayList<>();

        @Override
        public boolean hasCase(Case investigationCase) {
            requireNonNull(investigationCase);
            return personsAdded.stream().anyMatch(investigationCase::isSameCase);
        }

        @Override
        public void addCase(Case investigationCase) {
            requireNonNull(investigationCase);
            personsAdded.add(investigationCase);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
