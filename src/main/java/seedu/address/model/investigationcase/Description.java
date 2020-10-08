package seedu.address.model.investigationcase;

/**
 * Represents an Investigation Case's description in PIVOT.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description extends Alphanumeric {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces";
    private static final boolean CAN_BE_BLANK = true;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        super(description, CAN_BE_BLANK);
    }

    /**
     * Returns true if a given string is a valid description.
     * Can be blank.
     */
    public static boolean isValidDescription(String test) {
        return isValidAlphanum(test, CAN_BE_BLANK);
    }

}
