package seedu.pivot.logic.commands.witnesscommands;

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
import seedu.pivot.model.investigationcase.caseperson.Witness;

/**
 * Represents a Delete command for deleting Witnesses from a Case in PIVOT based on its Index.
 */
public class DeleteWitnessCommand extends DeleteCommand implements Undoable {

    public static final String MESSAGE_DELETE_WITNESS_SUCCESS = "Deleted witness: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(DeleteWitnessCommand.class);

    private final Index caseIndex;
    private final Index witnessIndex;

    /**
     * Creates a DeleteWitnessCommand to delete the witness from the case at specified index.
     * @param caseIndex The index of the case to delete the witness.
     * @param witnessIndex The index of the witness to be deleted.
     */
    public DeleteWitnessCommand(Index caseIndex, Index witnessIndex) {
        requireAllNonNull(caseIndex, witnessIndex);
        this.caseIndex = caseIndex;
        this.witnessIndex = witnessIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Deleting witness from current case...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(caseIndex.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(caseIndex.getZeroBased());
        List<Witness> updatedWitnesses = stateCase.getWitnesses();

        // invalid witness index
        if (witnessIndex.getZeroBased() >= updatedWitnesses.size()) {
            logger.info("Invalid index: " + witnessIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_WITNESS_DISPLAYED_INDEX);
        }

        Witness witnessToDelete = updatedWitnesses.get(witnessIndex.getZeroBased());
        updatedWitnesses.remove(witnessToDelete);

        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), stateCase.getDocuments(), stateCase.getSuspects(),
                stateCase.getVictims(), updatedWitnesses, stateCase.getTags(), stateCase.getArchiveStatus());

        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_DELETE_WITNESS_SUCCESS, witnessToDelete), this);
        model.updateFilteredCaseList(PREDICATE_SHOW_DEFAULT_CASES);

        return new CommandResult(String.format(MESSAGE_DELETE_WITNESS_SUCCESS, witnessToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteWitnessCommand // instanceof handles nulls
                && caseIndex.equals(((DeleteWitnessCommand) other).caseIndex)
                && witnessIndex.equals(((DeleteWitnessCommand) other).witnessIndex)); // state check
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
