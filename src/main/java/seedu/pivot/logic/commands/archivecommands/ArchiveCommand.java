package seedu.pivot.logic.commands.archivecommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_DEFAULT_SECTION;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_MAIN_PAGE;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.Command;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;

/**
 * Represents an Archive command for archiving Cases in PIVOT based on its Index.
 */
public class ArchiveCommand extends Command implements Undoable {
    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the case identified by the index number"
            + " used in the displayed list.\n"
            + "Format: '" + COMMAND_WORD + " case PARAMETERS'\n\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " case 1";

    public static final String MESSAGE_ARCHIVE_CASE_SUCCESS = "Case archived: %1$s";

    private static final Page pageType = Page.MAIN;
    private static final Logger logger = LogsCenter.getLogger(ArchiveCommand.class);

    private Index targetIndex;

    /**
     * Creates an Archive to add the specified {@code Case} specified at {@code Index}.
     *
     * @param targetIndex Index of Case to be archived in PIVOT.
     */
    public ArchiveCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Archiving case ...");

        assert(StateManager.atDefaultSection()) : ASSERT_DEFAULT_SECTION;
        assert(StateManager.atMainPage()) : ASSERT_MAIN_PAGE;

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        // check case provided is valid index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case caseToArchive = lastShownList.get(targetIndex.getZeroBased());
        assert(caseToArchive.getArchiveStatus().equals(ArchiveStatus.DEFAULT)) : ASSERT_DEFAULT_SECTION;

        Case updatedCase = new Case(caseToArchive.getTitle(), caseToArchive.getDescription(), caseToArchive.getStatus(),
                caseToArchive.getDocuments(), caseToArchive.getSuspects(), caseToArchive.getVictims(),
                caseToArchive.getWitnesses(), caseToArchive.getTags(), ArchiveStatus.ARCHIVED);

        model.deleteCase(caseToArchive);
        model.addCase(updatedCase);

        model.updateFilteredCaseList(Model.PREDICATE_SHOW_DEFAULT_CASES);
        model.commitPivot(String.format(MESSAGE_ARCHIVE_CASE_SUCCESS, updatedCase), this);

        return new CommandResult(String.format(MESSAGE_ARCHIVE_CASE_SUCCESS, updatedCase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveCommand // instanceof handles nulls
                && targetIndex.equals(((ArchiveCommand) other).targetIndex)); // state check
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
