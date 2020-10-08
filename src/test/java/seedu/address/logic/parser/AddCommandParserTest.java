package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.Command.TYPE_CASE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCases.AMY;
import static seedu.address.testutil.TypicalCases.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.investigationcase.Case;
import seedu.address.model.investigationcase.Status;
import seedu.address.model.investigationcase.Title;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CaseBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Case expectedCase = new CaseBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TYPE_CASE + NAME_DESC_BOB
                + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedCase));

        // multiple names - last name accepted
        assertParseSuccess(parser, TYPE_CASE + NAME_DESC_AMY + NAME_DESC_BOB
                + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedCase));

        // multiple statuses - last status accepted
        assertParseSuccess(parser, TYPE_CASE + NAME_DESC_BOB
                + STATUS_DESC_AMY + STATUS_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedCase));

        // multiple tags - all accepted
        Case expectedCaseMultipleTags = new CaseBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();

        assertParseSuccess(parser, TYPE_CASE + NAME_DESC_BOB
                + STATUS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedCaseMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Case expectedCase = new CaseBuilder(AMY).withTags().build();
        assertParseSuccess(parser, TYPE_CASE + NAME_DESC_AMY, new AddCommand(expectedCase));

        // no status
        expectedCase = new CaseBuilder(expectedCase).withStatus("active").build();
        assertParseSuccess(parser, TYPE_CASE + NAME_DESC_AMY, new AddCommand(expectedCase));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, TYPE_CASE + VALID_NAME_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, TYPE_CASE + VALID_NAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser, TYPE_CASE + INVALID_NAME_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Title.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, TYPE_CASE + NAME_DESC_BOB
                + INVALID_STATUS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Status.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TYPE_CASE + NAME_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        // TODO: for "add case t:TITLE", this test case may not be so relevant bc only one value
        // but might be relevant for "add case t:TITLE d:DESCRIPTION" <-- can KIV for future use?
        assertParseFailure(parser, TYPE_CASE + INVALID_NAME_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TYPE_CASE + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // missing specific command word
        assertParseFailure(parser, NAME_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
