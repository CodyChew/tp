package seedu.address.model.investigationcase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCases.ALICE;
import static seedu.address.testutil.TypicalCases.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CaseBuilder;

public class CaseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Case investigationCase = new CaseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> investigationCase.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameCase(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCase(null));

        // different title -> returns false
        Case editedAlice = new CaseBuilder(ALICE).withTitle(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameCase(editedAlice));

        // same name, different attributes -> returns true
        editedAlice = new CaseBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCase(editedAlice));

        //TODO: Might want to test for permutations of different attributes if multiple fields in future.

    }

    @Test
    public void equals() {
        // same values -> returns true
        Case aliceCopy = new CaseBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Case editedAlice = new CaseBuilder(ALICE).withTitle(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CaseBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
