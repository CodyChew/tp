package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.investigationcase.Case;
import seedu.address.model.investigationcase.Description;
import seedu.address.model.investigationcase.Document;
import seedu.address.model.investigationcase.Name;
import seedu.address.model.investigationcase.Reference;
import seedu.address.model.investigationcase.Status;
import seedu.address.model.investigationcase.Suspect;
import seedu.address.model.investigationcase.Title;
import seedu.address.model.investigationcase.Victim;
import seedu.address.model.investigationcase.Witness;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class CaseBuilder {

    public static final String DEFAULT_TITLE = "Alice Pauline";
    public static final String DEFAULT_DESCRIPTION = "";
    public static final String DEFAULT_STATUS = "active";

    private Title title;
    private Description description;
    private Status status;
    private List<Document> documents;
    private List<Suspect> suspects;
    private List<Victim> victims;
    private Set<Tag> tags;
    private List<Witness> witnesses;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public CaseBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        status = Status.createStatus(DEFAULT_STATUS);
        documents = new ArrayList<>();
        suspects = new ArrayList<>();
        victims = new ArrayList<>();
        witnesses = new ArrayList<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public CaseBuilder(Case caseToCopy) {
        title = caseToCopy.getTitle();
        description = caseToCopy.getDescription();
        status = caseToCopy.getStatus();
        documents = new ArrayList<>(caseToCopy.getDocuments());
        suspects = caseToCopy.getSuspects();
        victims = caseToCopy.getVictims();
        witnesses = new ArrayList<>(caseToCopy.getWitnesses());
        tags = new HashSet<>(caseToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CaseBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Person} that we are building.
     */
    public CaseBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CaseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CaseBuilder withWitnesses(String ... witnesses) {
        this.witnesses = SampleDataUtil.getWitnessList(witnesses);
        return this;
    }

    /**
     * Sets the {@code Document} of the {@code Person} that we are building.
     */
    public CaseBuilder withDocument(String name, String ref) {
        this.documents = new ArrayList<>();
        this.documents.add(new Document(new Name(name), new Reference(ref)));
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public CaseBuilder withStatus(String status) {
        this.status = Status.createStatus(status);
        return this;
    }

    //TODO: Not used anywhere in code, use the witness example and use there
    /**
     * Parses the {@code suspects} into a {@code List<Suspect>} and set it to the {@code Person} that we are building.
     */
    public CaseBuilder withSuspects(String ... suspects) {
        this.suspects = SampleDataUtil.getSuspectList(suspects);
        return this;
    }

    /**
     * Parses the {@code victims} into a {@code List<Victim>} and set it to the {@code Person} that we are building.
     */
    public CaseBuilder withVictims(String ... victims) {
        this.victims = SampleDataUtil.getVictimList(victims);
        return this;
    }

    /**
     * Generates a {@code Person} object with existing fields.
     * @return Person object
     */
    public Case build() {
        return new Case(title, description, status, documents, suspects, victims, witnesses, tags);
    }


}
