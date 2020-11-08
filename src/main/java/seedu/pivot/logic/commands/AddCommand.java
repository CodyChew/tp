package seedu.pivot.logic.commands;

import static seedu.pivot.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_REFERENCE;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.pivot.logic.parser.CliSyntax.PREFIX_TITLE;

/**
 * Represents an Add command for adding items of different types to PIVOT.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE_MAIN_PAGE = COMMAND_WORD
            + ": Adds an item of a specified type to PIVOT.\n"
            + "Format: '" + COMMAND_WORD + " TYPE PARAMETERS'\n\n"
            + "TYPE '" + TYPE_CASE + "'\n"
            + "Parameters: " + PREFIX_TITLE + "TITLE\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_CASE + " " + PREFIX_TITLE + " Ang Mo Kio Murders";

    public static final String MESSAGE_USAGE_CASE_PAGE = COMMAND_WORD
            + ": Adds an item of a specified type to opened case in PIVOT.\n"
            + "Format: '" + COMMAND_WORD + " TYPE PARAMETERS'\n\n"
            + "TYPE '" + TYPE_DESC + "'\n"
            + "Parameters: " + PREFIX_DESC + "DESC \n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DESC + " " + PREFIX_DESC + "7 caught for rioting\n\n"
            + "TYPE '" + TYPE_DOC + "'\n"
            + "Parameters: " + PREFIX_NAME + "NAME " + PREFIX_REFERENCE + "REFERENCE\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_DOC + " "
            + PREFIX_NAME + "Evidence " + PREFIX_REFERENCE + "text1.txt\n\n"
            + "TYPE '" + TYPE_SUSPECT + "','" + TYPE_VICTIM + "','" + TYPE_WITNESS + "'\n"
            + "Parameters: " + PREFIX_NAME + "NAME " + PREFIX_SEX + "SEX " + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] [" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " " + TYPE_SUSPECT + " " + PREFIX_NAME + "John " + PREFIX_SEX + "M "
            + PREFIX_PHONE + "912345678 " + PREFIX_EMAIL + "john@email.com " + PREFIX_ADDRESS + "Blk 123";
}
