package seedu.pivot.logic.commands.witnesscommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.commands.testutil.ModelStub;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Name;
import seedu.pivot.model.investigationcase.caseperson.Address;
import seedu.pivot.model.investigationcase.caseperson.Email;
import seedu.pivot.model.investigationcase.caseperson.Phone;
import seedu.pivot.model.investigationcase.caseperson.Sex;
import seedu.pivot.model.investigationcase.caseperson.Witness;
import seedu.pivot.testutil.CaseBuilder;

public class DeleteWitnessCommandTest {
    private static final Name DEFAULT_NAME = new Name("Test Name");
    private static final Sex DEFAULT_SEX = Sex.createSex("m");
    private static final Phone DEFAULT_PHONE = new Phone("91234567");
    private static final Address DEFAULT_ADDRESS = new Address("Blk 123");
    private static final Email DEFAULT_EMAIL = new Email("abc@gmail.com");
    private static final Witness DEFAULT_WITNESS = new Witness(DEFAULT_NAME, DEFAULT_SEX,
            DEFAULT_PHONE, DEFAULT_EMAIL, DEFAULT_ADDRESS);
    private static final Index DEFAULT_CASE_INDEX = Index.fromZeroBased(0);
    private static final Index DEFAULT_WITNESS_INDEX = Index.fromZeroBased(0);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteWitnessCommand(null, null));
        assertThrows(NullPointerException.class, () -> new DeleteWitnessCommand(null, DEFAULT_WITNESS_INDEX));
        assertThrows(NullPointerException.class, () -> new DeleteWitnessCommand(DEFAULT_CASE_INDEX, null));
    }

    @Test
    public void equals() {
        Index alternateCaseIndex = Index.fromZeroBased(1000);
        Index alternateWitnessIndex = Index.fromZeroBased(1000);
        DeleteCommand command = new DeleteWitnessCommand(DEFAULT_CASE_INDEX, DEFAULT_WITNESS_INDEX);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        assertTrue(command.equals(new DeleteWitnessCommand(DEFAULT_CASE_INDEX, DEFAULT_WITNESS_INDEX)));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different person -> returns false
        assertFalse(command.equals(new DeleteWitnessCommand(alternateCaseIndex, alternateWitnessIndex)));
    }

    @Test
    public void execute_witnessDeletedByModel_success() throws CommandException {
        StateManager.setState(DEFAULT_CASE_INDEX);
        Case testCase = new CaseBuilder()
                .addWitnesses(DEFAULT_WITNESS).build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        DeleteCommand command = new DeleteWitnessCommand(DEFAULT_CASE_INDEX, DEFAULT_WITNESS_INDEX);
        assertEquals(String.format(DeleteWitnessCommand.MESSAGE_DELETE_WITNESS_SUCCESS, DEFAULT_WITNESS),
                command.execute(modelStub).getFeedbackToUser());
        StateManager.resetState();
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        StateManager.setState(DEFAULT_CASE_INDEX);
        Case testCase = new CaseBuilder().build();
        List<Case> caseList = new ArrayList<>();
        caseList.add(testCase);

        Index invalidIndex = Index.fromZeroBased(1000);
        ModelStub modelStub = new ModelStubWithCaseList(caseList);
        DeleteCommand command = new DeleteWitnessCommand(DEFAULT_CASE_INDEX, invalidIndex);
        assertThrows(CommandException.class,
                UserMessages.MESSAGE_INVALID_WITNESS_DISPLAYED_INDEX, () -> command.execute(modelStub));
        StateManager.resetState();
    }

    /**
     * A Model stub that holds a caseList.
     */
    private class ModelStubWithCaseList extends ModelStub {
        private final List<Case> caseList;

        private ModelStubWithCaseList(List<Case> caseList) {
            this.caseList = caseList;
        }

        @Override
        public ObservableList<Case> getFilteredCaseList() {
            return FXCollections.observableList(caseList);
        }

        @Override
        public void setCase(Case target, Case editedCase) {
            this.caseList.set(caseList.indexOf(target), editedCase);
        }

        @Override
        public void updateFilteredCaseList(Predicate<Case> predicate) {
            return;
        }

        @Override
        public void commitPivot(String commandMessage, Undoable command) {}
    }
}
