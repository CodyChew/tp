package seedu.pivot.logic.commands.suspectcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_SUSPECT;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_SEX;

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
import seedu.pivot.model.investigationcase.caseperson.Suspect;

/**
 * Represents an Add command for adding Suspects into Cases in PIVOT.
 */
public class AddSuspectCommand extends AddCommand implements Undoable {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_SUSPECT
            + ": Adds a suspect to the opened case in PIVOT.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SEX + "SEX "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_SUSPECT + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_SEX + "M "
            + PREFIX_PHONE + "912345678 "
            + PREFIX_EMAIL + "john@email.com "
            + PREFIX_ADDRESS + "Blk 123";

    public static final String MESSAGE_ADD_SUSPECT_SUCCESS = "New suspect added: %1$s";
    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(AddSuspectCommand.class);

    private final Index index;
    private final Suspect suspect;

    /**
     * Creates an AddSuspectCommand to add the specified {@code Suspect}
     *
     * @param suspect
     */
    public AddSuspectCommand(Index index, Suspect suspect) {
        requireAllNonNull(index, suspect);
        this.index = index;
        this.suspect = suspect;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Adding suspect to current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case openCase = lastShownList.get(index.getZeroBased());
        List<Suspect> updatedSuspects = openCase.getSuspects();

        if (updatedSuspects.stream().anyMatch(suspect::isSamePerson)) {
            logger.warning("Failed to add suspect: Tried to add a suspect that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_SUSPECT);
        }

        updatedSuspects.add(suspect);
        Case updatedCase = new Case(openCase.getTitle(), openCase.getDescription(), openCase.getStatus(),
                openCase.getDocuments(), updatedSuspects, openCase.getVictims(), openCase.getWitnesses(),
                openCase.getTags(), openCase.getArchiveStatus());

        model.setCase(openCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_ADD_SUSPECT_SUCCESS, suspect), this);

        return new CommandResult(String.format(MESSAGE_ADD_SUSPECT_SUCCESS, suspect));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSuspectCommand // instanceof handles nulls
                && suspect.equals(((AddSuspectCommand) other).suspect)
                && index.equals(((AddSuspectCommand) other).index));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
