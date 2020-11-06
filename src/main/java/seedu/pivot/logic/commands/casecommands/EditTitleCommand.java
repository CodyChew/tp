package seedu.pivot.logic.commands.casecommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_TITLE;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;

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
import seedu.pivot.model.investigationcase.Title;

/**
 * Represents an Edit command for editing Title of a Case in PIVOT.
 */
public class EditTitleCommand extends EditCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_TITLE
            + ": Edits the title of the opened case.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_TITLE + " "
            + PREFIX_TITLE + "Changed title";

    public static final String MESSAGE_EDIT_TITLE_SUCCESS = "Title updated: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(EditTitleCommand.class);

    private final Index index;
    private final Title title;

    /**
     * Creates an EditTitleCommand to update the specified { @code Title } for a { @code Case }.
     *
     * @param index Index of the Case in PIVOT.
     * @param title Title to be updated.
     */
    public EditTitleCommand(Index index, Title title) {
        requireAllNonNull(index, title);
        this.index = index;
        this.title = title;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Updating title to current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(index.getZeroBased());

        // create new updated case
        Case updatedCase = new Case(title, stateCase.getDescription(), stateCase.getStatus(),
                stateCase.getDocuments(), stateCase.getSuspects(), stateCase.getVictims(), stateCase.getWitnesses(),
                stateCase.getTags(), stateCase.getArchiveStatus());

        if (model.hasCase(updatedCase)) {
            logger.warning("Failed to add case: Tried to edit a title that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_TITLE);
        }

        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_EDIT_TITLE_SUCCESS, title), this);

        return new CommandResult(String.format(MESSAGE_EDIT_TITLE_SUCCESS, title));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditTitleCommand // instanceof handles nulls
                && index.equals(((EditTitleCommand) other).index)
                && title.equals(((EditTitleCommand) other).title));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
