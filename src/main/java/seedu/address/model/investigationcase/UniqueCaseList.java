package seedu.address.model.investigationcase;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.investigationcase.exceptions.CaseNotFoundException;
import seedu.address.model.investigationcase.exceptions.DuplicateCaseException;

/**
 * A list of cases that enforces uniqueness between its elements and does not allow nulls.
 * A case is considered unique by comparing using {@code Case#isSameCase(Case)}. As such, adding and updating of
 * cases uses Case#isSameCase(Case) for equality so as to ensure that the case being added or updated is
 * unique in terms of identity in the UniqueCaseList. However, the removal of a case uses Case#equals(Object) so
 * as to ensure that the case with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Case#isSameCase(Case)
 */
public class UniqueCaseList implements Iterable<Case> {

    private final ObservableList<Case> internalList = FXCollections.observableArrayList();
    private final ObservableList<Case> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent case as the given argument.
     */
    public boolean contains(Case toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCase);
    }

    /**
     * Adds a case to the list.
     * The case must not already exist in the list.
     */
    public void add(Case toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCaseException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the case {@code target} in the list with {@code editedCase}.
     * {@code target} must exist in the list.
     * The case identity of {@code editedCase} must not be the same as another existing case in the list.
     */
    public void setCase(Case target, Case editedCase) {
        requireAllNonNull(target, editedCase);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CaseNotFoundException();
        }

        if (!target.isSameCase(editedCase) && contains(editedCase)) {
            throw new DuplicateCaseException();
        }

        internalList.set(index, editedCase);
    }

    /**
     * Removes the equivalent case from the list.
     * The case must exist in the list.
     */
    public void remove(Case toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CaseNotFoundException();
        }
    }

    public void setCases(UniqueCaseList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cases}.
     * {@code cases} must not contain duplicate cases.
     */
    public void setCases(List<Case> cases) {
        requireAllNonNull(cases);
        if (!casesAreUnique(cases)) {
            throw new DuplicateCaseException();
        }

        internalList.setAll(cases);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Case> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Case> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCaseList // instanceof handles nulls
                        && internalList.equals(((UniqueCaseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code cases} contains only unique cases.
     */
    private boolean casesAreUnique(List<Case> cases) {
        for (int i = 0; i < cases.size() - 1; i++) {
            for (int j = i + 1; j < cases.size(); j++) {
                if (cases.get(i).isSameCase(cases.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
