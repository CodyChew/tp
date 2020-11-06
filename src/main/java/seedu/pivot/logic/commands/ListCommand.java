package seedu.pivot.logic.commands;

/**
 * Represents a List command for listing items of different types in PIVOT.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE_MAIN_PAGE = COMMAND_WORD
            + ": Lists all items of a specified type.\n"
            + "Format: '" + COMMAND_WORD + " TYPE'\n\n"
            + "TYPE '" + TYPE_CASE + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_CASE + "\n\n"
            + "TYPE '" + TYPE_ARCHIVE + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_ARCHIVE;

    public static final String MESSAGE_USAGE_CASE_PAGE = COMMAND_WORD
            + ": Lists all items of a specified type.\n"
            + "Format: '" + COMMAND_WORD + " TYPE'\n\n"
            + "TYPE '" + TYPE_DOC + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DOC + "\n\n"
            + "TYPE '" + TYPE_SUSPECT + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_SUSPECT + "\n\n"
            + "TYPE '" + TYPE_VICTIM + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_VICTIM + "\n\n"
            + "TYPE '" + TYPE_WITNESS + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_WITNESS;

    public static final String MESSAGE_USAGE_COMBINED = COMMAND_WORD
            + ": Lists all items of a specified type at Main Page.\n"
            + "Format: '" + COMMAND_WORD + " TYPE'\n\n"
            + "TYPE '" + TYPE_CASE + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_CASE + "\n\n"
            + "TYPE '" + TYPE_ARCHIVE + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_ARCHIVE + "\n\n"
            + COMMAND_WORD + ": Lists all items of a specified type at Case Page.\n"
            + "Format: '" + COMMAND_WORD + " TYPE'\n\n"
            + "TYPE '" + TYPE_DOC + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DOC + "\n\n"
            + "TYPE '" + TYPE_SUSPECT + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_SUSPECT + "\n\n"
            + "TYPE '" + TYPE_VICTIM + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_VICTIM + "\n\n"
            + "TYPE '" + TYPE_WITNESS + "'\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_WITNESS;

}
