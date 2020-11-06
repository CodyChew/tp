package seedu.pivot.logic.commands.casecommands.descriptioncommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.EditCommand;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Description;

/**
 * Represents an Edit command for editing a Description in a Case in PIVOT.
 */
public class EditDescriptionCommand extends EditCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_DESC
            + ": Edits the description of the opened case.\n"
            + "Parameters: "
            + PREFIX_DESC + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DESC + " "
            + PREFIX_DESC + "This is an example description!";

    public static final String MESSAGE_EDIT_DESCRIPTION_SUCCESS = "Description updated: %1$s";
    public static final String MESSAGE_DUPLICATE_DESCRIPTION = "This description already exists for the case!";
    public static final String MESSAGE_NO_DESCRIPTION_TO_EDIT = "Cannot edit description of "
            + "case, because this case does not have a description";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(EditDescriptionCommand.class);

    private final Index index;
    private final Description description;

    /**
     * Creates an EditDescriptionCommand to update the specified { @code Description } for a { @code Case }.
     *
     * @param index Index of the Case in PIVOT.
     * @param description Description to be updated.
     */
    public EditDescriptionCommand(Index index, Description description) {
        requireAllNonNull(index, description);
        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Updating description to current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(index.getZeroBased());
        Description stateCaseDescription = stateCase.getDescription();

        // check for existing description
        if (!stateCaseDescription.hasDescription()) {
            logger.warning("Failed to edit description: Tried to edit description in a "
                    + "case with no description in PIVOT");
            throw new CommandException(MESSAGE_NO_DESCRIPTION_TO_EDIT);
        }

        // check for same description
        if (stateCaseDescription.equals(this.description)) {
            logger.warning("Failed to edit description: Tried to edit to a description that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_DESCRIPTION);
        }

        // create new updated case
        Case updatedCase = new Case(stateCase.getTitle(), this.description, stateCase.getStatus(),
                stateCase.getDocuments(), stateCase.getSuspects(), stateCase.getVictims(), stateCase.getWitnesses(),
                stateCase.getTags(), stateCase.getArchiveStatus());
        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_EDIT_DESCRIPTION_SUCCESS, this.description), this);

        return new CommandResult(String.format(MESSAGE_EDIT_DESCRIPTION_SUCCESS, description));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditDescriptionCommand // instanceof handles nulls
                && index.equals(((EditDescriptionCommand) other).index)
                && description.equals(((EditDescriptionCommand) other).description));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
