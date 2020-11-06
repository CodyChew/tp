package seedu.pivot.model.investigationcase;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Investigation Case's description in PIVOT.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescriptionToAdd(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description cannot be blank";

    private final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if a given string can be added as a Valid Description.
     * Cannot be blank.
     */
    public static boolean isValidDescriptionToAdd(String test) {
        return !test.isEmpty();
    }

    /**
     * Returns true if Description object has a description.
     */
    public boolean hasDescription() {
        return !description.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && description.equals(other.toString())); // state check
    }

    @Override
    public String toString() {
        return this.description;
    }
}
