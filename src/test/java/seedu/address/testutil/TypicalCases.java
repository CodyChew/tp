package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.investigationcase.Case;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalCases {

    public static final Case ALICE = new CaseBuilder().withTitle("Alice Pauline")
            .withDocument("name", "test1.txt")
            .withStatus("COLD")
            .withVictims("Tom")
            .withWitnesses("Janice")
            .withTags("friends")
            .build();
    public static final Case BENSON = new CaseBuilder().withTitle("Benson Meier")
            .withStatus("CLOSED")
            .withDocument("name", "test1.txt")
            .withVictims("Tom")
            .withWitnesses("Mary")
            .withTags("owesMoney", "friends")
            .build();

    public static final Case CARL = new CaseBuilder().withTitle("Carl Kurz")
            .build();
    public static final Case DANIEL = new CaseBuilder().withTitle("Daniel Meier")
            .withTags("friends").build();
    public static final Case ELLE = new CaseBuilder().withTitle("Elle Meyer")
            .build();
    public static final Case FIONA = new CaseBuilder().withTitle("Fiona Kunz")
            .build();
    public static final Case GEORGE = new CaseBuilder().withTitle("George Best")
            .build();

    // Manually added
    public static final Case HOON = new CaseBuilder().withTitle("Hoon Meier")
            .build();
    public static final Case IDA = new CaseBuilder().withTitle("Ida Mueller")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Case AMY = new CaseBuilder().withTitle(VALID_NAME_AMY)
            .withStatus(VALID_STATUS_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Case BOB = new CaseBuilder().withTitle(VALID_NAME_BOB)
            .withStatus(VALID_STATUS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCases() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Case investigationCase : getTypicalPersons()) {
            ab.addCase(investigationCase);
        }
        return ab;
    }

    public static List<Case> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
