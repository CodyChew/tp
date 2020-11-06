package seedu.pivot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;

import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;

/**
 * Represents a List command for listing Tab items of different types in PIVOT.
 */
public class ListTabCommand extends ListCommand {

    public static final String LIST_TAB_SUCCESS = "Tab successfully listed: %1$s";
    private static final Logger logger = LogsCenter.getLogger(ListTabCommand.class);

    private final String tabType;

    /**
     * Creates a ListTabCommand holding the type of Tab to be listed.
     * @param tabType
     */
    public ListTabCommand(String tabType) {
        requireNonNull(tabType);
        this.tabType = tabType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing List Tab Command...");
        requireNonNull(model);

        assert (StateManager.atCasePage()) : ASSERT_CASE_PAGE;

        StateManager.setTabState(tabType);

        return new CommandResult(String.format(LIST_TAB_SUCCESS, tabType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ListTabCommand
                && this.tabType.equals(((ListTabCommand) other).tabType); // instanceof handles nulls
    }
}
