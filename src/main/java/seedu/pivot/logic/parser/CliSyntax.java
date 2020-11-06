package seedu.pivot.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {

    /* Prefix definitions for Case */
    public static final Prefix PREFIX_TITLE = new Prefix("t:");
    public static final Prefix PREFIX_REFERENCE = new Prefix("r:");
    public static final Prefix PREFIX_STATUS = new Prefix("s:");
    public static final Prefix PREFIX_DESC = new Prefix("d:");

    /* Prefix definitions for CasePerson */
    public static final Prefix PREFIX_NAME = new Prefix("n:");
    public static final Prefix PREFIX_SEX = new Prefix("sex:");
    public static final Prefix PREFIX_PHONE = new Prefix("p:");
    public static final Prefix PREFIX_EMAIL = new Prefix("e:");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a:");

}
