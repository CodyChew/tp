package seedu.pivot.logic.commands.documentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_CASE_PAGE;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.UserMessages;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.commons.util.FileUtil;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.OpenCommand;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.Model;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Reference;

/**
 * Represents an Open command for opening Documents in PIVOT based on its Index and Reference.
 */
public class OpenDocumentCommand extends OpenCommand {

    public static final String MESSAGE_OPEN_DOCUMENT_SUCCESS = "Opened Document: %1$s";

    private static final Logger logger = LogsCenter.getLogger(OpenDocumentCommand.class);

    public OpenDocumentCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Opening specified document...");

        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        //check for valid index
        assert(StateManager.atCasePage()) : ASSERT_CASE_PAGE;
        Index caseIndex = StateManager.getState();

        //get current case in state
        Case stateCase = lastShownList.get(caseIndex.getZeroBased());
        List<Document> existingDocuments = stateCase.getDocuments();

        //check valid document index
        if (targetIndex.getZeroBased() >= existingDocuments.size()) {
            logger.info("Invalid index: " + targetIndex.getOneBased());
            throw new CommandException(UserMessages.MESSAGE_INVALID_DOCUMENT_DISPLAYED_INDEX);
        }

        //open the document
        Document docToOpen = existingDocuments.get(targetIndex.getZeroBased());
        Reference reference = docToOpen.getReference();
        if (!reference.isExists()) {
            logger.warning("Reference does not exist");
            throw new CommandException(String.format(
                    UserMessages.MESSAGE_REFERENCE_DOES_NOT_EXIST, docToOpen.getReference()));
        }

        try {
            logger.info("Document successfully opened");
            FileUtil.openFile(reference.getPath());
        } catch (IOException e) {
            logger.warning("Error opening document");
            throw new CommandException(UserMessages.MESSAGE_ERROR_OPENING_FILE);
        }

        return new CommandResult(String.format(MESSAGE_OPEN_DOCUMENT_SUCCESS, docToOpen));
    }
}
