package seedu.pivot.logic.commands;

/**
 * Represents a command that can be undone by the user.
 */
public interface Undoable {
    Page getPage();
}
