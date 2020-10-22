package seedu.pivot.logic.commands;

import static seedu.pivot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pivot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pivot.testutil.TypicalCases.getTypicalPivot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pivot.logic.commands.casecommands.AddCaseCommand;
import seedu.pivot.model.Model;
import seedu.pivot.model.ModelManager;
import seedu.pivot.model.UserPrefs;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.testutil.CaseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCaseCommand}.
 */
public class AddCaseCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPivot(), new UserPrefs());
    }

    @Test
    public void execute_newCase_success() {
        Case validCase = new CaseBuilder().build();

        Model expectedModel = new ModelManager(model.getPivot(), new UserPrefs());
        expectedModel.addCase(validCase);

        assertCommandSuccess(new AddCaseCommand(validCase), model,
                String.format(AddCaseCommand.MESSAGE_ADD_CASE_SUCCESS, validCase), expectedModel);
    }

    @Test
    public void execute_duplicateCase_throwsCommandException() {
        Case caseInList = model.getPivot().getCaseList().get(0);
        assertCommandFailure(new AddCaseCommand(caseInList), model, AddCaseCommand.MESSAGE_DUPLICATE_CASE);
    }

}
