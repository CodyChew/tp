package seedu.pivot.logic.commands.archivecommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_ARCHIVED_SECTION;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.Command;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;

/**
 * Represents an Unarchive command for unarchiving Cases in PIVOT based on its Index.
 */
public class UnarchiveCommand extends Command implements Undoable {
    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the case identified by the index number"
            + " used in the displayed list.\n"
            + "Format: '" + COMMAND_WORD + " case INDEX'\n\n"
            + "TYPE 'case'\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " case 1";

    public static final String MESSAGE_UNARCHIVE_CASE_SUCCESS = "Case unarchived: %1$s";

    private static final Page pageType = Page.MAIN;
    private static final Logger logger = LogsCenter.getLogger(UnarchiveCommand.class);

    private Index targetIndex;

    /**
     * Creates an UnarchiveCommand to add the specified {@code Case} specified at {@code Index}.
     *
     * @param targetIndex Index of Case to be unarchived in PIVOT.
     */
    public UnarchiveCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Unarchiving case ...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        // check case provided is valid index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(UserMessages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case caseToUnarchive = lastShownList.get(targetIndex.getZeroBased());
        assert(caseToUnarchive.getArchiveStatus().equals(ArchiveStatus.ARCHIVED)) : ASSERT_ARCHIVED_SECTION;

        Case updatedCase = new Case(caseToUnarchive.getTitle(), caseToUnarchive.getDescription(),
                caseToUnarchive.getStatus(), caseToUnarchive.getDocuments(), caseToUnarchive.getSuspects(),
                caseToUnarchive.getVictims(), caseToUnarchive.getWitnesses(), caseToUnarchive.getTags(),
                ArchiveStatus.DEFAULT);

        model.deleteCase(caseToUnarchive);
        model.addCase(updatedCase);

        model.updateFilteredCaseList(Model.PREDICATE_SHOW_ARCHIVED_CASES);
        model.commitPivot(String.format(MESSAGE_UNARCHIVE_CASE_SUCCESS, updatedCase), this);

        return new CommandResult(String.format(MESSAGE_UNARCHIVE_CASE_SUCCESS, updatedCase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnarchiveCommand // instanceof handles nulls
                && targetIndex.equals(((UnarchiveCommand) other).targetIndex)); // state check
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}

