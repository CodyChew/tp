package seedu.pivot.logic.commands;

/**
 * Represents a Delete command for deleting items of different types to PIVOT based on its Index.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE_MAIN_PAGE = COMMAND_WORD
            + ": Deletes the item of a specified type identified by the index number"
            + " used in the displayed list.\n"
            + "Format: '" + COMMAND_WORD + " TYPE PARAMETERS'\n\n"
            + "TYPE '" + TYPE_CASE + "'\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_CASE + " 1";

    public static final String MESSAGE_USAGE_CASE_PAGE = COMMAND_WORD
            + ": Deletes the specified type of item identified by the index number"
            + " used in the displayed list in current case.\n"
            + "Format: '" + COMMAND_WORD + " TYPE PARAMETERS'\n\n"
            + "TYPE '" + TYPE_DESC + "'\n"
            + "Parameters: DESC\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DESC + "\n\n"
            + "TYPE '" + TYPE_DOC + "','" + TYPE_SUSPECT + "','" + TYPE_VICTIM + "','" + TYPE_WITNESS + "'\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_SUSPECT + " 1";

}
