package seedu.pivot.logic.commands.casecommands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.OpenCommand;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;

/**
 * Represents an Open command for opening Cases in PIVOT based on its Index.
 */
public class OpenCaseCommand extends OpenCommand {

    public static final String MESSAGE_OPEN_CASE_SUCCESS = "Opened Case: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the case at specified index in the current list\n"
            + "TYPE 'case'\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " case 1\n\n";

    private static final Logger logger = LogsCenter.getLogger(OpenCaseCommand.class);

    /**
     * Creates an OpenCaseCommand to open the case at specified {@code targetIndex}.
     *
     * @param targetIndex Index of the case in PIVOT.
     */
    public OpenCaseCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Opening specified case...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.info("Invalid index: " + targetIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case caseToOpen = lastShownList.get(targetIndex.getZeroBased());

        StateManager.setState(targetIndex);

        return new CommandResult(String.format(MESSAGE_OPEN_CASE_SUCCESS, caseToOpen));
    }
}
