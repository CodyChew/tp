package seedu.pivot.logic.commands.victimcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_VICTIM;
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
import seedu.pivot.model.investigationcase.caseperson.Victim;

/**
 * Represents an Add command for adding Victims into Cases in PIVOT.
 */
public class AddVictimCommand extends AddCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_VICTIM
            + ": Adds a victim to the opened case in PIVOT.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SEX + "SEX "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_VICTIM + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_SEX + "M "
            + PREFIX_PHONE + "912345678 "
            + PREFIX_EMAIL + "john@email.com "
            + PREFIX_ADDRESS + "Blk 123";

    public static final String MESSAGE_ADD_VICTIM_SUCCESS = "New victim added: %1$s";
    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(AddVictimCommand.class);

    private final Index index;
    private final Victim victim;

    /**
     * Creates an AddVictimCommand to add the specified {@code Victim}
     *
     * @param victim The victim to be added.
     */
    public AddVictimCommand(Index index, Victim victim) {
        requireAllNonNull(index, victim);
        this.index = index;
        this.victim = victim;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Adding victim to current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(index.getZeroBased());
        List<Victim> updatedVictims = stateCase.getVictims();

        if (updatedVictims.stream().anyMatch(victim::isSamePerson)) {
            logger.warning("Failed to add victim: Tried to add a victim that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_VICTIM);
        }

        updatedVictims.add(victim);

        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), stateCase.getDocuments(), stateCase.getSuspects(),
                updatedVictims, stateCase.getWitnesses(), stateCase.getTags(), stateCase.getArchiveStatus());

        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_ADD_VICTIM_SUCCESS, victim), this);

        return new CommandResult(String.format(MESSAGE_ADD_VICTIM_SUCCESS, victim));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddVictimCommand // instanceof handles nulls
                && victim.equals(((AddVictimCommand) other).victim)
                && index.equals(((AddVictimCommand) other).index));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
