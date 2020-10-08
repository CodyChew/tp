package seedu.address.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditCaseDescriptor;
import seedu.address.model.investigationcase.Case;
import seedu.address.model.investigationcase.Name;
import seedu.address.model.investigationcase.Status;
import seedu.address.model.investigationcase.Suspect;
import seedu.address.model.investigationcase.Title;
import seedu.address.model.investigationcase.Victim;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditCaseDescriptorBuilder {

    private EditCaseDescriptor descriptor;

    public EditCaseDescriptorBuilder() {
        descriptor = new EditCaseDescriptor();
    }

    public EditCaseDescriptorBuilder(EditCaseDescriptor descriptor) {
        this.descriptor = new EditCaseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditCaseDescriptorBuilder(Case investigationCase) {
        descriptor = new EditCaseDescriptor();
        descriptor.setTitle(investigationCase.getTitle());
        descriptor.setStatus(investigationCase.getStatus());
        descriptor.setSuspects(investigationCase.getSuspects());
        descriptor.setVictims(investigationCase.getVictims());
        descriptor.setTags(investigationCase.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditCaseDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditCaseDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(Status.createStatus(status));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditCaseDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code suspects} into a {@code List<Suspect>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditCaseDescriptorBuilder withSuspects(String... suspect) {
        List<Suspect> suspects = Stream.of(suspect).map(string -> new Suspect(new Name(string)))
                .collect(Collectors.toList());
        descriptor.setSuspects(suspects);
        return this;
    }

    /**
     * Parses the {@code victims} into a {@code List<Victim>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditCaseDescriptorBuilder withVictims(String... victims) {
        List<Victim> victim = Stream.of(victims).map(string -> new Victim(new Name(string)))
                .collect(Collectors.toList());
        descriptor.setVictims(victim);
        return this;
    }

    public EditCaseDescriptor build() {
        return descriptor;
    }
}
