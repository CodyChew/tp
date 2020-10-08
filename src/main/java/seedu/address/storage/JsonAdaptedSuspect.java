package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.investigationcase.Name;
import seedu.address.model.investigationcase.Suspect;

/**
 * Jackson-friendly version of {@link Suspect}.
 */
public class JsonAdaptedSuspect {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Suspect's %s field is missing!";

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedSuspect} with the given suspect details.
     */
    @JsonCreator
    public JsonAdaptedSuspect(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Suspect} into this class for Jackson use.
     */
    public JsonAdaptedSuspect(Suspect source) {
        this.name = source.getName().alphaNum;
    }

    /**
     * Converts this Jackson-friendly adapted suspect object into the model's {@code Suspect} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted suspect.
     */
    public Suspect toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        return new Suspect(modelName);
    }
}
