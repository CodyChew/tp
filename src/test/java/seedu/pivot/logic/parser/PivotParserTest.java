package seedu.pivot.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.pivot.logic.commands.Command.TYPE_CASE;
import static seedu.pivot.testutil.Assert.assertThrows;
import static seedu.pivot.testutil.TypicalIndexes.FIRST_INDEX;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.pivot.logic.commands.AddCommand;
import seedu.pivot.logic.commands.DeleteCommand;
import seedu.pivot.logic.commands.ExitCommand;
import seedu.pivot.logic.commands.FindCommand;
import seedu.pivot.logic.commands.HelpCommand;
import seedu.pivot.logic.commands.ListCommand;
import seedu.pivot.logic.commands.casecommands.AddCaseCommand;
import seedu.pivot.logic.commands.casecommands.DeleteCaseCommand;
import seedu.pivot.logic.commands.casecommands.ListCaseCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.DetailsContainsKeywordsPredicate;
import seedu.pivot.testutil.CaseBuilder;
import seedu.pivot.testutil.CaseUtil;

public class PivotParserTest {

    private final PivotParser parser = new PivotParser();

    @Test
    public void parseCommand_addCase() throws Exception {
        Case investigationCase = new CaseBuilder().build();
        AddCommand command = (AddCaseCommand) parser.parseCommand(CaseUtil.getAddCommand(investigationCase));
        assertEquals(new AddCaseCommand(investigationCase), command);
    }

    //    @Test
    //    public void parseCommand_clear() throws Exception {
    //        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    //        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    //    }

    @Test
    public void parseCommand_delete_case() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TYPE_CASE + " "
                        + FIRST_INDEX.getOneBased());
        assertEquals(new DeleteCaseCommand(FIRST_INDEX), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new DetailsContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list_case() throws Exception {
        //TODO: check for state first?
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + TYPE_CASE)
                instanceof ListCaseCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " " + TYPE_CASE + " 3")
                instanceof ListCaseCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
