package seedu.pivot.logic.commands.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.pivot.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.commands.Command;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.model.Model;
import seedu.pivot.model.Pivot;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.DetailsContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // for case
    public static final String VALID_TITLE_AMY = "Amy Bee Disappearance";
    public static final String VALID_TITLE_BOB = "Bob Choo Salon Theft";
    public static final String VALID_STATUS_AMY = "active";
    public static final String VALID_STATUS_BOB = "closed";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String PREFIX_WITH_TITLE_AMY = " " + PREFIX_TITLE + VALID_TITLE_AMY;
    public static final String PREFIX_WITH_TITLE_BOB = " " + PREFIX_TITLE + VALID_TITLE_BOB;

    public static final String PREFIX_WITH_STATUS_AMY = " " + PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String PREFIX_WITH_STATUS_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;

    // '&' not allowed in title
    public static final String PREFIX_WITH_INVALID_TITLE_AMY = " " + PREFIX_TITLE + "James&";
    public static final String PREFIX_WITH_INVALID_STATUS = " " + PREFIX_STATUS + "status";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // for caseperson
    public static final String VALID_CASEPERSON_NAME_AMY = "Amy";
    public static final String VALID_CASEPERSON_NAME_BOB = "Bob";
    public static final String VALID_CASEPERSON_SEX_AMY = "F";
    public static final String VALID_CASEPERSON_SEX_BOB = "M";
    public static final String VALID_CASEPERSON_PHONE = "91234567";
    public static final String VALID_CASEPERSON_EMAIL = "peterjack@example.com";
    public static final String VALID_CASEPERSON_ADDRESS = "Bishan Blk 123";

    public static final String INVALID_CASEPERSON_NAME = "James&";

    // inputs
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_CASEPERSON_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_CASEPERSON_NAME_BOB;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + INVALID_CASEPERSON_NAME;
    public static final String SEX_DESC_BOB = " " + PREFIX_SEX + VALID_CASEPERSON_SEX_BOB;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_CASEPERSON_PHONE;

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the PIVOT, filtered Case list and selected Case in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Pivot expectedPivot = new Pivot(actualModel.getPivot());
        List<Case> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCaseList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedPivot, actualModel.getPivot());
        assertEquals(expectedFilteredList, actualModel.getFilteredCaseList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the Case at the given {@code targetIndex} in the
     * {@code model}'s PIVOT.
     */
    public static void showCaseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCaseList().size());

        Case investigationCase = model.getFilteredCaseList().get(targetIndex.getZeroBased());
        final String[] splitName = investigationCase.getTitle().getAlphaNum().split("\\s+");
        model.updateFilteredCaseList(new DetailsContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCaseList().size());
    }

}
