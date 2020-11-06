package seedu.pivot.logic.commands.casecommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_MAIN_PAGE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.pivot.model.Model.PREDICATE_SHOW_ARCHIVED_CASES;
import static seedu.pivot.model.Model.PREDICATE_SHOW_DEFAULT_CASES;

import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;

/**
 * Represents an Add command for adding Cases into PIVOT.
 */
public class AddCaseCommand extends AddCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_CASE
            + ": Adds a case to PIVOT.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + TYPE_CASE + " "
            + PREFIX_TITLE + "Bishan Theft "
            + PREFIX_STATUS + "closed";

    public static final String MESSAGE_ADD_CASE_SUCCESS = "New case added: %1$s";
    public static final String MESSAGE_DUPLICATE_CASE = "This case already exists in PIVOT";

    private static final Page pageType = Page.MAIN;
    private static final Logger logger = LogsCenter.getLogger(AddCaseCommand.class);

    private final Case investigationCase;

    /**
     * Creates an AddCaseCommand to add the specified {@code Case}.
     *
     * @param investigationCase investigation Case to be added to PIVOT.
     */
    public AddCaseCommand(Case investigationCase) {
        requireNonNull(investigationCase);
        this.investigationCase = investigationCase;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Adding case to PIVOT...");
        requireNonNull(model);

        assert(StateManager.atMainPage()) : ASSERT_MAIN_PAGE;

        if (model.hasCase(investigationCase)) {
            logger.warning("Failed to add case: Tried to add a case that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_CASE);
        }

        model.addCase(investigationCase);
        model.commitPivot(String.format(MESSAGE_ADD_CASE_SUCCESS, investigationCase), this);

        if (StateManager.atDefaultSection()) {
            model.updateFilteredCaseList(PREDICATE_SHOW_DEFAULT_CASES);
        }
        if (StateManager.atArchivedSection()) {
            model.updateFilteredCaseList(PREDICATE_SHOW_ARCHIVED_CASES);
        }

        return new CommandResult(String.format(MESSAGE_ADD_CASE_SUCCESS, investigationCase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCaseCommand // instanceof handles nulls
                && investigationCase.equals(((AddCaseCommand) other).investigationCase));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
