package seedu.address.model.investigationcase;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Witness in the investigation case.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Witness {

    // Identity fields
    public final Name name;

    /**
     * Every field must be present and not null.
     */
    public Witness(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return name;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Witness)) {
            return false;
        }

        Witness otherWitness = (Witness) other;
        return otherWitness.getName().equals(getName());
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
