package seedu.address.model.investigationcase;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Suspect in PIVOT
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Suspect {
    // Identity fields
    private final Name name;

    /**
     * Every field must be present and not null.
     */
    public Suspect(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns true if both suspects have the same identity and data fields.
     * This defines a stronger notion of equality between two suspects.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Suspect)) {
            return false;
        }

        Suspect otherSuspect = (Suspect) other;
        return otherSuspect.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
