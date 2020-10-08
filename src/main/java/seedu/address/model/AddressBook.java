package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.investigationcase.Case;
import seedu.address.model.investigationcase.UniqueCaseList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCase comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCaseList cases;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        cases = new UniqueCaseList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Cases in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the case list with {@code cases}.
     * {@code cases} must not contain duplicate cases.
     */
    public void setCases(List<Case> cases) {
        this.cases.setCases(cases);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setCases(newData.getCaseList());
    }

    //// case-level operations

    /**
     * Returns true if a case with the same identity as {@code case} exists in the address book.
     */
    public boolean hasCase(Case investigationCase) {
        requireNonNull(investigationCase);
        return cases.contains(investigationCase);
    }

    /**
     * Adds a case to the address book.
     * The case must not already exist in the address book.
     */
    public void addCase(Case p) {
        cases.add(p);
    }

    /**
     * Replaces the given case {@code target} in the list with {@code editedCase}.
     * {@code target} must exist in the address book.
     * The case identity of {@code editedCase} must not be the same as another existing case in the address book.
     */
    public void setCase(Case target, Case editedCase) {
        requireNonNull(editedCase);

        cases.setCase(target, editedCase);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCase(Case key) {
        cases.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return cases.asUnmodifiableObservableList().size() + " cases";
        // TODO: refine later
    }

    @Override
    public ObservableList<Case> getCaseList() {
        return cases.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && cases.equals(((AddressBook) other).cases));
    }

    @Override
    public int hashCode() {
        return cases.hashCode();
    }
}
