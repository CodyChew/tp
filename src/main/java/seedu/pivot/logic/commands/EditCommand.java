package seedu.pivot.logic.commands;

import static seedu.pivot.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_REFERENCE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;

/**
 * Represents an Edit command for editing items of different types to PIVOT.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the specified type "
            + "in the current case.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Format: '" + COMMAND_WORD + " TYPE PARAMETERS'\n\n"
            + "TYPE 'title'\n"
            + "Parameters: [" + PREFIX_TITLE + "TITLE]\n"
            + "Example: " + COMMAND_WORD + " title " + PREFIX_TITLE + "Triple Kovan Murders\n\n"
            + "TYPE '" + TYPE_DESC + "'\n"
            + "Parameters: " + PREFIX_DESC + "DESC \n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DESC + " " + PREFIX_DESC + "8 caught for rioting\n\n"
            + "TYPE 'status'\n"
            + "Parameters: [" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " status s:closed\n\n"
            + "TYPE '" + TYPE_DOC + "'\n"
            + "Parameters: INDEX [" + PREFIX_NAME + "NAME] [" + PREFIX_REFERENCE + "REFERENCE]\n"
            + "Example: " + COMMAND_WORD + " doc 1 n:meeting notes\n\n"
            + "TYPE '" + TYPE_SUSPECT + "','" + TYPE_VICTIM + "','" + TYPE_WITNESS + "'\n"
            + "Parameters: INDEX [" + PREFIX_NAME + "NAME] [" + PREFIX_SEX + "SEX] [" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] [" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_SUSPECT + " 1"
            + PREFIX_EMAIL + "newEmail@mail.com " + PREFIX_ADDRESS + "new road crescent\n\n";
}
