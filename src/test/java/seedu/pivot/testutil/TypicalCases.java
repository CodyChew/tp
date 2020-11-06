package seedu.pivot.testutil;

import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.pivot.logic.commands.testutil.CommandTestUtil.VALID_TITLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pivot.model.Pivot;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalCases {

    // CasePersons
    public static final Suspect PETER = new CasePersonBuilder().withName("Peter").withSex("M").withPhone("912345678")
            .withEmail("peter@gmail.com").withAddress("Blk 123").buildSuspect();
    public static final Victim TOM = new CasePersonBuilder().withName("Tom").withSex("M").withPhone("912345678")
            .withEmail("tom@gmail.com").withAddress("Blk 123").buildVictim();
    public static final Witness JANICE = new CasePersonBuilder().withName("Janice").withSex("F")
            .withPhone("912345678").buildWitness();

    // Cases
    // The cases here are linked to the test file: typicalPersonsPivot.json
    // If the Case is modified here, it has to be modified there too.
    public static final Case ALICE_PAULINE_ASSAULT = new CaseBuilder().withTitle("Alice Pauline Assault")
            .withDocument("name", "validButShouldNotExist.txt")
            .withStatus("COLD")
            .addVictims(TOM)
            .addWitnesses(JANICE)
            .addSuspects(PETER)
            .withTags("friends")
            .build();
    public static final Case BENSON_MEIER_ROBBERY = new CaseBuilder().withTitle("Benson Meier Robbery")
            .withStatus("CLOSED")
            .withDocument("name", "test1.txt")
            .withDescription("Was hit with a very big banana!")
            .addVictims(TOM)
            .addWitnesses(JANICE)
            .withTags("owesMoney", "friends")
            .build();

    public static final Case CARL_KURZ_FIRE = new CaseBuilder().withTitle("Carl Kurz Fire")
            .build();
    public static final Case DANIEL_MEIER_SHOPLIFTING = new CaseBuilder().withTitle("Daniel Meier Shoplifting")
            .withTags("friends").build();
    public static final Case ELLE_MEYER_SHOOTING = new CaseBuilder().withTitle("Elle Meyer Shooting")
            .build();
    public static final Case FIONA_KUNZ_KIDNAPPING = new CaseBuilder().withTitle("Fiona Kunz Kidnapping")
            .build();
    public static final Case GEORGE_BEST_VANDALISM = new CaseBuilder().withTitle("George Best Vandalism")
            .build();

    // Manually added
    public static final Case HOON_MEIER_ARSON = new CaseBuilder().withTitle("Hoon Meier Arson")
            .build();
    public static final Case IDA_MUELLER_STABBING = new CaseBuilder().withTitle("Ida Mueller Stabbing")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Case AMY_BEE_DISAPPEARANCE = new CaseBuilder().withTitle(VALID_TITLE_AMY)
            .withStatus(VALID_STATUS_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Case BOB_CHOO_SALON_THEFT = new CaseBuilder().withTitle(VALID_TITLE_BOB)
            .withStatus(VALID_STATUS_BOB)
            .build();

    // Manually added - Cases that are archived
    public static final Case JUNK_YARD_MURDER = new CaseBuilder().withTitle("Junk Yard Murder")
            .withStatus("ACTIVE")
            .withArchiveStatus(ArchiveStatus.ARCHIVED).build();
    public static final Case KLOOK_SCAM = new CaseBuilder().withTitle("Klook Scam")
            .withStatus("ACTIVE")
            .withArchiveStatus(ArchiveStatus.ARCHIVED).build();
    public static final Case LOUIS_HOMICIDE = new CaseBuilder().withTitle("Louis Homicide")
            .withStatus("ACTIVE")
            .withArchiveStatus(ArchiveStatus.ARCHIVED).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCases() {} // prevents instantiation

    /**
     * Returns an {@code Pivot} with all the typical cases.
     */
    public static Pivot getTypicalPivot() {
        Pivot pivot = new Pivot();
        for (Case investigationCase : getTypicalCases()) {
            pivot.addCase(investigationCase);
        }
        return pivot;
    }

    public static List<Case> getTypicalCases() {
        return new ArrayList<>(Arrays.asList(ALICE_PAULINE_ASSAULT, BENSON_MEIER_ROBBERY,
                CARL_KURZ_FIRE, DANIEL_MEIER_SHOPLIFTING, ELLE_MEYER_SHOOTING,
                FIONA_KUNZ_KIDNAPPING, GEORGE_BEST_VANDALISM, JUNK_YARD_MURDER, KLOOK_SCAM,
                LOUIS_HOMICIDE));
    }
}
