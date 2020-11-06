package seedu.pivot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.pivot.commons.core.index.Index;

/**
 * Represents an Open command for opening items of different types to PIVOT based on its Index.
 */
public abstract class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the item of a specified type, identified by the index number"
            + " used in the displayed list.\n"
            + "Format: '" + COMMAND_WORD + " TYPE'\n\n"
            + "TYPE '" + TYPE_CASE + "'\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_CASE + " 1\n\n"
            + "TYPE '" + TYPE_DOC + "'\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DOC + " 1\n\n";

    protected final Index targetIndex;

    protected OpenCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenCommand // instanceof handles nulls
                && targetIndex.equals(((OpenCommand) other).targetIndex)); // state check
    }

}
