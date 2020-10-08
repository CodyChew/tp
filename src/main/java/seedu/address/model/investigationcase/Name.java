package seedu.address.model.investigationcase;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name extends Alphanumeric {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    private static final boolean CAN_BE_BLANK = false;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        super(name, CAN_BE_BLANK);
    }

    public static boolean isValidName(String name) {
        return isValidAlphanum(name, CAN_BE_BLANK);
    }


}
