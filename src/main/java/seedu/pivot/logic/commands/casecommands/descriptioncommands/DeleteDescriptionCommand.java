package seedu.pivot.logic.commands.casecommands.descriptioncommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Description;

/**
 * Represents a Delete command for deleting Description from a Case in PIVOT based on its Index.
 */
public class DeleteDescriptionCommand extends DeleteCommand implements Undoable {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_DESC
            + ": Deletes the description of the opened case.\n"
            + "Parameters: "
            + PREFIX_DESC + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DESC;

    public static final String MESSAGE_DELETE_DESCRIPTION_SUCCESS = "Description deleted: %1$s";
    public static final String MESSAGE_NO_DESCRIPTION_TO_DELETE = "Cannot delete description of "
            + "case, because this case does not have a description";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(DeleteDescriptionCommand.class);

    private final Index index;

    /**
     * Creates an DeleteDescriptionCommand to update the specified { @code Description } for a { @code Case }.
     *
     * @param index Index of the Case in PIVOT.
     */
    public DeleteDescriptionCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Deleting description of current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(index.getZeroBased());
        Description stateCaseDescription = stateCase.getDescription();

        // check for no description
        if (!stateCaseDescription.hasDescription()) {
            logger.warning("Failed to delete description: Tried to delete description for a "
                    + "case with no description in PIVOT");
            throw new CommandException(MESSAGE_NO_DESCRIPTION_TO_DELETE);
        }

        Description deletedDescription = new Description("");

        // create new updated case
        Case updatedCase = new Case(stateCase.getTitle(), deletedDescription, stateCase.getStatus(),
                stateCase.getDocuments(), stateCase.getSuspects(), stateCase.getVictims(), stateCase.getWitnesses(),
                stateCase.getTags(), stateCase.getArchiveStatus());
        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_DELETE_DESCRIPTION_SUCCESS, stateCaseDescription), this);

        return new CommandResult(String.format(MESSAGE_DELETE_DESCRIPTION_SUCCESS, stateCaseDescription));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDescriptionCommand // instanceof handles nulls
                && index.equals(((DeleteDescriptionCommand) other).index));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
