package seedu.address.model.investigationcase.exceptions;

/**
 * Signals that the operation will result in duplicate Cases (Cases are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCaseException extends RuntimeException {
    public DuplicateCaseException() {
        super("Operation would result in duplicate cases");
    }
}
