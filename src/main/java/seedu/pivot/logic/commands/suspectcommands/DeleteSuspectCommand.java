package seedu.pivot.logic.commands.suspectcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;
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
import seedu.pivot.model.investigationcase.caseperson.Suspect;

/**
 * Represents a Delete command for deleting Suspects from a Case in PIVOT based on its Index.
 */
public class DeleteSuspectCommand extends DeleteCommand implements Undoable {

    public static final String MESSAGE_DELETE_SUSPECT_SUCCESS = "Deleted Suspect: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(DeleteSuspectCommand.class);

    private final Index caseIndex;
    private final Index suspectIndex;

    /**
     * Creates a DeleteSuspectCommand to delete the suspect at specified index, in the case at specified index.
     * @param caseIndex The index of the case to delete the suspect.
     * @param suspectIndex The index of the suspect to be deleted.
     */
    public DeleteSuspectCommand(Index caseIndex, Index suspectIndex) {
        requireAllNonNull(caseIndex, suspectIndex);
        this.caseIndex = caseIndex;
        this.suspectIndex = suspectIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Deleting suspect from current case...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(caseIndex.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case openCase = lastShownList.get(caseIndex.getZeroBased());
        List<Suspect> updatedSuspects = openCase.getSuspects();

        if (suspectIndex.getZeroBased() >= updatedSuspects.size()) {
            logger.info("Invalid index: " + suspectIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_SUSPECT_DISPLAYED_INDEX);
        }

        Suspect suspectToDelete = updatedSuspects.get(suspectIndex.getZeroBased());
        updatedSuspects.remove(suspectIndex.getZeroBased());
        Case updatedCase = new Case(openCase.getTitle(), openCase.getDescription(), openCase.getStatus(),
                openCase.getDocuments(), updatedSuspects, openCase.getVictims(), openCase.getWitnesses(),
                openCase.getTags(), openCase.getArchiveStatus());

        model.setCase(openCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_DELETE_SUSPECT_SUCCESS, suspectToDelete), this);
        model.updateFilteredCaseList(PREDICATE_SHOW_DEFAULT_CASES);

        return new CommandResult(String.format(MESSAGE_DELETE_SUSPECT_SUCCESS, suspectToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSuspectCommand // instanceof handles nulls
                && caseIndex.equals(((DeleteSuspectCommand) other).caseIndex)
                && suspectIndex.equals(((DeleteSuspectCommand) other).suspectIndex)); // state check
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
