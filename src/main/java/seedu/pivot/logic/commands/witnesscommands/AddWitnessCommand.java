package seedu.pivot.logic.commands.witnesscommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_WITNESS;
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
import seedu.pivot.model.investigationcase.caseperson.Witness;

/**
 * Represents an Add command for adding Witnesses into Cases in PIVOT.
 */
public class AddWitnessCommand extends AddCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_WITNESS
            + ": Adds a witness to the opened case in PIVOT.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SEX + "SEX "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_WITNESS + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_SEX + "M "
            + PREFIX_PHONE + "912345678 "
            + PREFIX_EMAIL + "john@email.com "
            + PREFIX_ADDRESS + "Blk 123";

    public static final String MESSAGE_ADD_WITNESS_SUCCESS = "New witness added: %1$s";
    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(AddWitnessCommand.class);

    private final Index index;
    private final Witness witness;

    /**
     * Creates an AddWitnessCommand to add the specified {@code Witness}
     *
     * @param witness The witness to be added.
     */
    public AddWitnessCommand(Index index, Witness witness) {
        requireAllNonNull(index, witness);
        this.index = index;
        this.witness = witness;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Adding witness to current case...");
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        Case stateCase = lastShownList.get(index.getZeroBased());
        List<Witness> updatedWitnesses = stateCase.getWitnesses();

        if (updatedWitnesses.stream().anyMatch(witness::isSamePerson)) {
            logger.warning("Failed to add witness: Tried to add a witness that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_WITNESS);
        }

        updatedWitnesses.add(witness);

        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), stateCase.getDocuments(), stateCase.getSuspects(),
                stateCase.getVictims(), updatedWitnesses, stateCase.getTags(), stateCase.getArchiveStatus());

        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_ADD_WITNESS_SUCCESS, witness), this);

        return new CommandResult(String.format(MESSAGE_ADD_WITNESS_SUCCESS, witness));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWitnessCommand // instanceof handles nulls
                && witness.equals(((AddWitnessCommand) other).witness)
                && index.equals(((AddWitnessCommand) other).index));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
