package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREFIX_WITH_INVALID_STATUS;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREFIX_WITH_INVALID_TITLE_AMY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREFIX_WITH_STATUS_AMY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREFIX_WITH_STATUS_BOB;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREFIX_WITH_TITLE_AMY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.PREFIX_WITH_TITLE_BOB;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.pivot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pivot.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pivot.testutil.TypicalCases.AMY_BEE_DISAPPEARANCE;
import static seedu.pivot.testutil.TypicalCases.BOB_CHOO_SALON_THEFT;

import org.junit.jupiter.api.Test;

import seedu.pivot.logic.commands.casecommands.AddCaseCommand;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Status;
import seedu.pivot.model.investigationcase.Title;
import seedu.pivot.testutil.CaseBuilder;


public class AddCaseCommandParserTest {
    private AddCaseCommandParser parser = new AddCaseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Case expectedCase = new CaseBuilder(BOB_CHOO_SALON_THEFT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_WITH_TITLE_BOB
                + PREFIX_WITH_STATUS_BOB,
                new AddCaseCommand(expectedCase));

        // multiple names - last name accepted
        assertParseSuccess(parser, PREFIX_WITH_TITLE_AMY + PREFIX_WITH_TITLE_BOB
                + PREFIX_WITH_STATUS_BOB,
                new AddCaseCommand(expectedCase));

        // multiple statuses - last status accepted
        assertParseSuccess(parser, PREFIX_WITH_TITLE_BOB
                + PREFIX_WITH_STATUS_AMY + PREFIX_WITH_STATUS_BOB,
                new AddCaseCommand(expectedCase));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Case expectedCase = new CaseBuilder(AMY_BEE_DISAPPEARANCE).withTags().build();
        assertParseSuccess(parser, PREFIX_WITH_TITLE_AMY, new AddCaseCommand(expectedCase));

        // no status
        expectedCase = new CaseBuilder(expectedCase).withStatus("active").build();
        assertParseSuccess(parser, PREFIX_WITH_TITLE_AMY, new AddCaseCommand(expectedCase));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaseCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TITLE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TITLE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, PREFIX_WITH_INVALID_TITLE_AMY,
                Title.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, PREFIX_WITH_TITLE_BOB + PREFIX_WITH_INVALID_STATUS,
                Status.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        // Is relevant for "add case t:TITLE s:STATUS" or any number of invalid fields
        // Using 2 invalid Fields Title and Status
        assertParseFailure(parser, PREFIX_WITH_INVALID_TITLE_AMY + PREFIX_WITH_INVALID_STATUS,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_WITH_TITLE_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaseCommand.MESSAGE_USAGE));
    }
}
