package seedu.pivot.logic.commands.documentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_DUPLICATE_DOCUMENT;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_REFERENCE;

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
import seedu.pivot.model.investigationcase.Document;

/**
 * Represents an Add command for adding Documents into Cases in PIVOT.
 */
public class AddDocumentCommand extends AddCommand implements Undoable {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + TYPE_DOC
            + ": Adds a document to the opened case.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_REFERENCE + "REFERENCE\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DOC + " "
            + PREFIX_NAME + "Location file "
            + PREFIX_REFERENCE + "test1.txt";

    public static final String MESSAGE_ADD_DOCUMENT_SUCCESS = "New document added: %1$s";
    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(AddDocumentCommand.class);

    private final Index index;
    private final Document doc;

    /**
     * Creates an AddDocumentCommand to add a {@code Document} to a case with {@code Index}.
     *
     * @param index index of a case in the list.
     * @param doc document to be added.
     */
    public AddDocumentCommand(Index index, Document doc) {
        requireAllNonNull(index, doc);
        this.index = index;
        this.doc = doc;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Adding document to current case...");
        requireNonNull(model);

        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(index.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        //get current case in state
        Case stateCase = lastShownList.get(index.getZeroBased());
        List<Document> updatedDocuments = stateCase.getDocuments();

        //check for duplicate
        if (updatedDocuments.contains(this.doc)) {
            logger.warning("Failed to add document: Tried to add a document that exists in PIVOT");
            throw new CommandException(MESSAGE_DUPLICATE_DOCUMENT);
        }

        //add document to existing list
        updatedDocuments.add(this.doc);

        //create new updated case
        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(), stateCase.getStatus(),
                updatedDocuments, stateCase.getSuspects(), stateCase.getVictims(), stateCase.getWitnesses(),
                stateCase.getTags(), stateCase.getArchiveStatus());

        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_ADD_DOCUMENT_SUCCESS, this.doc), this);

        return new CommandResult(String.format(MESSAGE_ADD_DOCUMENT_SUCCESS, this.doc));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDocumentCommand // instanceof handles nulls
                && doc.equals(((AddDocumentCommand) other).doc)
                && index.equals(((AddDocumentCommand) other).index));
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
