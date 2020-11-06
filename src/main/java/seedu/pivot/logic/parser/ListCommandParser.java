package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.UserMessages.MESSAGE_INCORRECT_CASE_PAGE;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INCORRECT_MAIN_PAGE;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.commands.Command.TYPE_ARCHIVE;
import static seedu.pivot.logic.commands.Command.TYPE_CASE;
import static seedu.pivot.logic.commands.Command.TYPE_DOC;
import static seedu.pivot.logic.commands.Command.TYPE_SUSPECT;
import static seedu.pivot.logic.commands.Command.TYPE_VICTIM;
import static seedu.pivot.logic.commands.Command.TYPE_WITNESS;
import static seedu.pivot.logic.parser.PivotParser.BASIC_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.pivot.logic.commands.ListCommand;
import seedu.pivot.logic.commands.ListTabCommand;
import seedu.pivot.logic.commands.archivecommands.ListArchiveCommand;
import seedu.pivot.logic.commands.casecommands.ListCaseCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.logic.state.StateManager;

public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());

        if (StateManager.atMainPage()) {
            return parseMainPage(matcher);
        }

        if (StateManager.atCasePage()) {
            return parseCasePage(matcher);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE_COMBINED));
    }

    private static ListCommand parseMainPage(Matcher matcher) throws ParseException {
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE_MAIN_PAGE));
        }

        final String commandWord = matcher.group("commandWord");

        switch (commandWord) {
        case TYPE_CASE:
            return new ListCaseCommand();

        case TYPE_ARCHIVE:
            return new ListArchiveCommand();

        case TYPE_DOC:
        case TYPE_SUSPECT:
        case TYPE_VICTIM:
        case TYPE_WITNESS:
            throw new ParseException(MESSAGE_INCORRECT_CASE_PAGE);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE_MAIN_PAGE));
        }
    }

    private static ListCommand parseCasePage(Matcher matcher) throws ParseException {
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE_CASE_PAGE));
        }

        final String commandWord = matcher.group("commandWord");

        switch (commandWord) {
        case TYPE_DOC:
        case TYPE_SUSPECT:
        case TYPE_VICTIM:
        case TYPE_WITNESS:
            return new ListTabCommand(commandWord);

        case TYPE_CASE:
        case TYPE_ARCHIVE:
            throw new ParseException(MESSAGE_INCORRECT_MAIN_PAGE);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE_CASE_PAGE));
        }
    }
}
