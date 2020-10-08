package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditCaseDescriptor;
import seedu.address.model.investigationcase.Case;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class CaseUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Case investigationCase) {
        return AddCommand.COMMAND_WORD + " " + AddCommand.TYPE_CASE + " " + getPersonDetails(investigationCase);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Case investigationCase) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + investigationCase.getTitle().alphaNum + " ");
        sb.append(PREFIX_STATUS + investigationCase.getStatus().name() + " ");
        investigationCase.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCaseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.alphaNum).append(" "));
        descriptor.getStatus().ifPresent(status -> sb.append(PREFIX_STATUS).append(status.name()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
