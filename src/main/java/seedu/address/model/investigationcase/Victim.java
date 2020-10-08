package seedu.address.model.investigationcase;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class Victim {

    private final Name name;

    /**
     * Constructs a {@code Victim}.
     *
     * @param name A valid name.
     */
    public Victim(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Victim // instanceof handles nulls
                && this.getName().equals(((Victim) other).getName())); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }
}
