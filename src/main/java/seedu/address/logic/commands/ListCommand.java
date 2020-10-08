package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CASES;

import seedu.address.model.Model;

/**
 * Lists all cases in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all items of a specified type.\n"
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + " case";

    public static final String MESSAGE_SUCCESS = "Listed all cases";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCaseList(PREDICATE_SHOW_ALL_CASES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ListCommand; // instanceof handles nulls
    }
}
