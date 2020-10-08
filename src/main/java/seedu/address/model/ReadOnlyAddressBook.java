package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.investigationcase.Case;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the cases list.
     * This list will not contain any duplicate cases.
     */
    ObservableList<Case> getCaseList();

}
