package seedu.pivot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.model.Model.PREDICATE_SHOW_ARCHIVED_CASES;

import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;

/**
 * Lists all archived cases in PIVOT.
 */
public class ListArchiveCommand extends ListCommand {

    public static final String MESSAGE_LIST_CASE_SUCCESS = "Listed all archived cases";
    private static final Logger logger = LogsCenter.getLogger(ListArchiveCommand.class);

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing list case command");
        requireNonNull(model);

        assert(StateManager.atMainPage()) : "Program should be at main page";

        model.updateFilteredCaseList(PREDICATE_SHOW_ARCHIVED_CASES);
        return new CommandResult(MESSAGE_LIST_CASE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ListArchiveCommand; // instanceof handles nulls
    }

}
