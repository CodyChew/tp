package seedu.pivot.logic.parser;

import static seedu.pivot.commons.core.DeveloperMessages.ASSERT_MAIN_PAGE;
import static seedu.pivot.commons.core.UserMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pivot.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.pivot.logic.commands.casecommands.AddCaseCommand;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.Description;
import seedu.pivot.model.investigationcase.Document;
import seedu.pivot.model.investigationcase.Status;
import seedu.pivot.model.investigationcase.Title;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;
import seedu.pivot.model.tag.Tag;

public class AddCaseCommandParser implements Parser<AddCaseCommand> {
    @Override
    public AddCaseCommand parse(String args) throws ParseException {
        assert(StateManager.atMainPage()) : ASSERT_MAIN_PAGE;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCaseCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Description description = new Description("");
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).orElse("active"));
        List<Document> documents = new ArrayList<>();
        List<Suspect> suspects = new ArrayList<>();
        List<Victim> victims = new ArrayList<>();
        List<Witness> witnesses = new ArrayList<>();
        Set<Tag> tagList = new HashSet<>();

        Case investigationCase = null;
        if (StateManager.atArchivedSection()) {
            investigationCase = new Case(title, description, status, documents,
                    suspects, victims, witnesses, tagList, ArchiveStatus.ARCHIVED);
        }

        if (StateManager.atDefaultSection()) {
            investigationCase = new Case(title, description, status, documents,
                    suspects, victims, witnesses, tagList, ArchiveStatus.DEFAULT);
        }

        return new AddCaseCommand(investigationCase);
    }
}
