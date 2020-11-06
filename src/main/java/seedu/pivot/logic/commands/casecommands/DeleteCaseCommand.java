package seedu.pivot.logic.commands.casecommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_MAIN_PAGE;
import static seedu.pivot.model.Model.PREDICATE_SHOW_ARCHIVED_CASES;
import static seedu.pivot.model.Model.PREDICATE_SHOW_DEFAULT_CASES;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;

/**
 * Represents a Delete command for deleting a Case from PIVOT based on its Index.
 */
public class DeleteCaseCommand extends DeleteCommand implements Undoable {

    public static final String MESSAGE_DELETE_CASE_SUCCESS = "Deleted Case: %1$s";

    private static final Page pageType = Page.MAIN;
    private static final Logger logger = LogsCenter.getLogger(DeleteCaseCommand.class);

    private final Index targetIndex;

    /**
     * Creates an DeleteCaseCommand to delete the {@code Case} specified at {@code Index}.
     *
     * @param targetIndex Index of Case to be deleted from PIVOT.
     */
    public DeleteCaseCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Deleting case from PIVOT...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atMainPage()) : ASSERT_MAIN_PAGE;

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.info("Invalid index: " + targetIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case caseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCase(caseToDelete);
        model.commitPivot(String.format(MESSAGE_DELETE_CASE_SUCCESS, caseToDelete), this);

        if (StateManager.atDefaultSection()) {
            model.updateFilteredCaseList(PREDICATE_SHOW_DEFAULT_CASES);
        }
        if (StateManager.atArchivedSection()) {
            model.updateFilteredCaseList(PREDICATE_SHOW_ARCHIVED_CASES);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_CASE_SUCCESS, caseToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCaseCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCaseCommand) other).targetIndex)); // state check
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
