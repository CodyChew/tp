package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static List<Witness> sampleWitnesses = new ArrayList<>(
            List.of(new Witness(new Name("Mary")), new Witness(new Name("Janice"))));
    private static List<Document> sampleDocuments =
            List.of(new Document(new Name("name"), new Reference("test1.txt")));
    private static List<Victim> sampleVictims = getVictimList("Tom", "John");
    private static Description sampleDescription = new Description("");

    public static Case[] getSampleCases() {
        return new Case[] {
            new Case(new Title("Alex Yeoh"), sampleDescription,
                    Status.createStatus("active"), sampleDocuments,
                    new ArrayList<>(), sampleVictims,
                    sampleWitnesses, getTagSet("friends")),
            new Case(new Title("Bernice Yu"), sampleDescription,
                    Status.createStatus("closed"), sampleDocuments,
                    new ArrayList<>(), sampleVictims, sampleWitnesses, getTagSet("colleagues", "friends")),
            new Case(new Title("Charlotte Oliveiro"), sampleDescription,
                    Status.createStatus("cold"), sampleDocuments,
                    new ArrayList<>(), sampleVictims, sampleWitnesses, getTagSet("neighbours")),
            new Case(new Title("David Li"), sampleDescription,
                    Status.createStatus("active"), sampleDocuments,
                    new ArrayList<>(), sampleVictims, sampleWitnesses, getTagSet("family")),
            new Case(new Title("Irfan Ibrahim"), sampleDescription,
                    Status.createStatus("active"), sampleDocuments,
                    new ArrayList<>(), sampleVictims, sampleWitnesses, getTagSet("classmates")),
            new Case(new Title("Roy Balakrishnan"), sampleDescription,
                    Status.createStatus("active"), sampleDocuments,
                    new ArrayList<>(), sampleVictims, sampleWitnesses, getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Case sampleCase : getSampleCases()) {
            sampleAb.addCase(sampleCase);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a Witness list containing the list of strings given.
     */
    public static List<Witness> getWitnessList(String... strings) {
        return Arrays.stream(strings)
                .map(string -> new Witness(new Name(string)))
                .collect(Collectors.toList());
    }

    /**
     * Returns a suspect list containing the list of strings given.
     */
    public static List<Suspect> getSuspectList(String[] suspects) {
        return Arrays.stream(suspects)
                .map(string -> new Suspect(new Name(string)))
                .collect(Collectors.toList());
    }

    /**
     * Returns a victim list containing the list of strings given.
     */
    public static List<Victim> getVictimList(String... strings) {
        return Arrays.stream(strings)
                .map(string -> new Victim(new Name(string)))
                .collect(Collectors.toList());
    }

}
