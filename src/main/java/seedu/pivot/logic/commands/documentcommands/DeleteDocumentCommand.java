package seedu.pivot.logic.commands.documentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_VALID_INDEX;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.pivot.model.Model.PREDICATE_SHOW_DEFAULT_CASES;

import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Document;

/**
 * Represents a Delete command for deleting Documents from a Case in PIVOT based on its Index.
 */
public class DeleteDocumentCommand extends DeleteCommand implements Undoable {

    public static final String MESSAGE_DELETE_DOCUMENT_SUCCESS = "Deleted document: %1$s";

    private static final Page pageType = Page.CASE;
    private static final Logger logger = LogsCenter.getLogger(DeleteDocumentCommand.class);

    private final Index caseIndex;
    private final Index documentIndex;

    /**
     * Creates a DeleteDocumentCommand to delete a {@code Document} at index @code documentIndex}
     * from the case at index {@code caseIndex}.
     *
     * @param caseIndex index of a case in the list.
     * @param documentIndex document index in the document list.
     */
    public DeleteDocumentCommand(Index caseIndex, Index documentIndex) {
        requireAllNonNull(caseIndex, documentIndex);
        this.caseIndex = caseIndex;
        this.documentIndex = documentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Deleting document from current case...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        assert(caseIndex.getZeroBased() < lastShownList.size()) : ASSERT_VALID_INDEX;

        //get case from state
        Case stateCase = lastShownList.get(caseIndex.getZeroBased());
        List<Document> updatedDocuments = stateCase.getDocuments();

        //document index validation in model
        if (documentIndex.getZeroBased() >= updatedDocuments.size()) {
            logger.info("Invalid index: " + documentIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_DOCUMENT_DISPLAYED_INDEX);
        }

        //remove document
        Document documentToDelete = updatedDocuments.get(documentIndex.getZeroBased());
        updatedDocuments.remove(documentToDelete);

        Case updatedCase = new Case(stateCase.getTitle(), stateCase.getDescription(),
                stateCase.getStatus(), updatedDocuments, stateCase.getSuspects(),
                stateCase.getVictims(), stateCase.getWitnesses(), stateCase.getTags(), stateCase.getArchiveStatus());

        //update model
        model.setCase(stateCase, updatedCase);
        model.commitPivot(String.format(MESSAGE_DELETE_DOCUMENT_SUCCESS, documentToDelete), this);
        model.updateFilteredCaseList(PREDICATE_SHOW_DEFAULT_CASES);

        return new CommandResult(String.format(MESSAGE_DELETE_DOCUMENT_SUCCESS, documentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDocumentCommand // instanceof handles nulls
                && caseIndex.equals(((DeleteDocumentCommand) other).caseIndex)
                && documentIndex.equals(((DeleteDocumentCommand) other).documentIndex)); // state check
    }

    @Override
    public Page getPage() {
        return pageType;
    }
}
