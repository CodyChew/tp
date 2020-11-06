package seedu.pivot.logic.commands.victimcommands;

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
import seedu.pivot.model.investigationcase.caseperson.Victim;

/**
 * Represents a Delete command for deleting Victims from a Case in PIVOT based on its Index.
 */
public class DeleteVictimCommand extends DeleteCommand implements Undoable {

    public static final String MESSAGE_DELETE_VICTIM_SUCCESS = "Deleted victim: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(DeleteVictimCommand.class);

    private final Index caseIndex;
    private final Index victimIndex;

    /**
     * Creates a DeleteVictimCommand to delete the victim at specified index, in the case at specified index.
     * @param caseIndex The index of the case to delete the victim.
     * @param victimIndex The index of the victim to be deleted.
     */
    public DeleteVictimCommand(Index caseIndex, Index victimIndex) {
        requireAllNonNull(caseIndex, victimIndex);
        this.caseIndex = caseIndex;
        this.victimIndex = victimIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Deleting victim from current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(caseIndex.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(caseIndex.getZeroBased());
        List<Victim> updatedVictims = stateCase.getVictims();

        // invalid victim index
        if (victimIndex.getZeroBased() >= updatedVictims.size()) {
            logger.info("Invalid index: " + victimIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_VICTIM_DISPLAYED_INDEX);
        }

        Victim victimToDelete = updatedVictims.get(victimIndex.getZeroBased());
        updatedVictims.remove(victimToDelete);

        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), stateCase.getDocuments(), stateCase.getSuspects(),
                updatedVictims, stateCase.getWitnesses(), stateCase.getTags(), stateCase.getArchiveStatus());

        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_DELETE_VICTIM_SUCCESS, victimToDelete), this);
        model.updateFilteredCaseList(PREDICATE_SHOW_DEFAULT_CASES);

        return new CommandResult(String.format(MESSAGE_DELETE_VICTIM_SUCCESS, victimToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteVictimCommand // instanceof handles nulls
                && caseIndex.equals(((DeleteVictimCommand) other).caseIndex)
                && victimIndex.equals(((DeleteVictimCommand) other).victimIndex)); // state check
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
