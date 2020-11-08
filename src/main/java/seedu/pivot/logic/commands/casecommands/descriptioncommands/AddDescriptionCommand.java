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
import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Description;

/**
 * Represents an Add command for adding Description into Cases in PIVOT.
 */
public class AddDescriptionCommand extends AddCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_DESC
            + ": Adds a description to opened case in PIVOT. "
            + "Parameters: "
            + PREFIX_DESC + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " "
            + TYPE_DESC + " "
            + PREFIX_DESC + "7 people arrested for rioting";

    public static final String MESSAGE_ADD_DESCRIPTION_SUCCESS = "New description added: %1$s";
    public static final String MESSAGE_DESCRIPTION_ALREADY_EXISTS =
            "This case already has a description! Please edit the description instead.";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(AddDescriptionCommand.class);

    private final Index index;
    private final Description description;

    /**
     * Creates an AddDescriptionCommand to add the specified {@code Description}.
     *
     * @param index Index of the Case in PIVOT.
     * @param description Description to be added.
     */
    public AddDescriptionCommand(Index index, Description description) {
        requireAllNonNull(index, description);
        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Adding description to current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(index.getZeroBased());
        Description stateCaseDescription = stateCase.getDescription();

        // check if the case already has a description
        if (stateCaseDescription.hasDescription()) {
            logger.warning("Failed to add description: Tried to add a description to a case "
                    + "which already has a description in  PIVOT");
            throw new CommandException(MESSAGE_DESCRIPTION_ALREADY_EXISTS);
        }

        // create new updated case
        Case updatedCase = new Case(stateCase.getTitle(), this.description, stateCase.getStatus(),
                stateCase.getDocuments(), stateCase.getSuspects(), stateCase.getVictims(), stateCase.getWitnesses(),
                stateCase.getTags(), stateCase.getArchiveStatus());
        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_ADD_DESCRIPTION_SUCCESS, this.description), this);

        return new CommandResult(String.format(MESSAGE_ADD_DESCRIPTION_SUCCESS, this.description));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDescriptionCommand // instanceof handles nulls
                && index.equals(((AddDescriptionCommand) other).index)
                && description.equals(((AddDescriptionCommand) other).description));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
