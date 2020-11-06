package seedu.pivot.logic.commands;

import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String TYPE_TITLE = "title";
    public static final String TYPE_STATUS = "status";
    public static final String TYPE_CASE = "case";
    public static final String TYPE_DESC = "desc";
    public static final String TYPE_DOC = "doc";
    public static final String TYPE_SUSPECT = "suspect";
    public static final String TYPE_WITNESS = "witness";
    public static final String TYPE_VICTIM = "victim";
    public static final String TYPE_ARCHIVE = "archive";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;



}
