package seedu.address.model.investigationcase;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Document's reference in an investigation case.
 * Guarantees: immutable; is valid as declared in {@link #isValidReference(String)}
 */
public class Reference {

    public static final String MESSAGE_CONSTRAINTS =
            "File should be placed in the ./data/reference folder. Please only enter the file name.";

    private static final String DEFAULT_FILEPATH = "./references/";
    private final Path path;
    private final String fileName;


    /**
     * Constructs a {@code Reference}.
     *
     * @param fileName A valid file name in the default folder.
     */
    public Reference(String fileName) {
        requireNonNull(fileName);
        checkArgument(isValidReference(fileName), MESSAGE_CONSTRAINTS);
        this.path = Paths.get(DEFAULT_FILEPATH + fileName);
        this.fileName = fileName;
    }

    /**
     * Returns true if a given file path exists.
     */
    public static boolean isValidReference(String fileName) {
        Path filePath = Paths.get(DEFAULT_FILEPATH + fileName);
        return Files.exists(filePath);
    }


    @Override
    public String toString() {
        return this.path.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reference // instanceof handles nulls
                && path.equals(((Reference) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    public String getFileName() {
        return this.fileName;
    }
}
