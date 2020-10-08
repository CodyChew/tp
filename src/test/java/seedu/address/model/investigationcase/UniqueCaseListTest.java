package seedu.address.model.investigationcase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCases.ALICE;
import static seedu.address.testutil.TypicalCases.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.investigationcase.exceptions.CaseNotFoundException;
import seedu.address.model.investigationcase.exceptions.DuplicateCaseException;
import seedu.address.testutil.CaseBuilder;

public class UniqueCaseListTest {

    private final UniqueCaseList uniqueCaseList = new UniqueCaseList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCaseList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueCaseList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueCaseList.add(ALICE);
        assertTrue(uniqueCaseList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCaseList.add(ALICE);
        Case editedAlice = new CaseBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCaseList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCaseList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueCaseList.add(ALICE);
        assertThrows(DuplicateCaseException.class, () -> uniqueCaseList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCaseList.setCase(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCaseList.setCase(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(CaseNotFoundException.class, () -> uniqueCaseList.setCase(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueCaseList.add(ALICE);
        uniqueCaseList.setCase(ALICE, ALICE);
        UniqueCaseList expectedUniqueCaseList = new UniqueCaseList();
        expectedUniqueCaseList.add(ALICE);
        assertEquals(expectedUniqueCaseList, uniqueCaseList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueCaseList.add(ALICE);
        Case editedAlice = new CaseBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCaseList.setCase(ALICE, editedAlice);
        UniqueCaseList expectedUniqueCaseList = new UniqueCaseList();
        expectedUniqueCaseList.add(editedAlice);
        assertEquals(expectedUniqueCaseList, uniqueCaseList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueCaseList.add(ALICE);
        uniqueCaseList.setCase(ALICE, BOB);
        UniqueCaseList expectedUniqueCaseList = new UniqueCaseList();
        expectedUniqueCaseList.add(BOB);
        assertEquals(expectedUniqueCaseList, uniqueCaseList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCaseList.add(ALICE);
        uniqueCaseList.add(BOB);
        assertThrows(DuplicateCaseException.class, () -> uniqueCaseList.setCase(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCaseList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(CaseNotFoundException.class, () -> uniqueCaseList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCaseList.add(ALICE);
        uniqueCaseList.remove(ALICE);
        UniqueCaseList expectedUniqueCaseList = new UniqueCaseList();
        assertEquals(expectedUniqueCaseList, uniqueCaseList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCaseList.setCases((UniqueCaseList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueCaseList.add(ALICE);
        UniqueCaseList expectedUniqueCaseList = new UniqueCaseList();
        expectedUniqueCaseList.add(BOB);
        uniqueCaseList.setCases(expectedUniqueCaseList);
        assertEquals(expectedUniqueCaseList, uniqueCaseList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCaseList.setCases((List<Case>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueCaseList.add(ALICE);
        List<Case> caseList = Collections.singletonList(BOB);
        uniqueCaseList.setCases(caseList);
        UniqueCaseList expectedUniqueCaseList = new UniqueCaseList();
        expectedUniqueCaseList.add(BOB);
        assertEquals(expectedUniqueCaseList, uniqueCaseList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Case> listWithDuplicateCases = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateCaseException.class, () -> uniqueCaseList.setCases(listWithDuplicateCases));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCaseList.asUnmodifiableObservableList().remove(0));
    }
}
