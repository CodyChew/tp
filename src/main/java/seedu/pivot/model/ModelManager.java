package seedu.pivot.model;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.pivot.commons.core.GuiSettings;
import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.logic.commands.Undoable;
import seedu.pivot.logic.state.StateManager;
import seedu.pivot.model.investigationcase.Case;

/**
 * Represents the in-memory model of the PIVOT data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Pivot pivot;
    private final VersionedPivot versionedPivot;
    private final UserPrefs userPrefs;
    private final FilteredList<Case> filteredCases;

    /**
     * Initializes a ModelManager with the given PIVOT and userPrefs.
     */
    public ModelManager(ReadOnlyPivot pivot, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(pivot, userPrefs);

        logger.fine("Initializing with PIVOT: " + pivot + " and user prefs " + userPrefs);

        this.pivot = new Pivot(pivot);
        this.versionedPivot = new VersionedPivot(pivot);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCases = new FilteredList<>(this.pivot.getCaseList());
    }

    public ModelManager() {
        this(new Pivot(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPivotFilePath() {
        return userPrefs.getPivotFilePath();
    }

    @Override
    public void setPivotFilePath(Path pivotFilePath) {
        requireNonNull(pivotFilePath);
        userPrefs.setPivotFilePath(pivotFilePath);
    }

    //=========== PIVOT ================================================================================

    @Override
    public void setPivot(ReadOnlyPivot pivot) {
        this.pivot.resetData(pivot);
        StateManager.refresh();
    }

    @Override
    public ReadOnlyPivot getPivot() {
        return pivot;
    }

    @Override
    public boolean hasCase(Case investigationCase) {
        requireNonNull(investigationCase);
        return pivot.hasCase(investigationCase);
    }

    @Override
    public void deleteCase(Case target) {
        pivot.removeCase(target);
    }

    @Override
    public void addCase(Case investigationCase) {
        pivot.addCase(investigationCase);
    }

    @Override
    public void setCase(Case target, Case editedCase) {
        requireAllNonNull(target, editedCase);
        pivot.setCase(target, editedCase);
        StateManager.refresh();
    }

    //=========== Versioned Pivot ===========================================================================
    @Override
    public void commitPivot(String commandMessage, Undoable command) {
        requireAllNonNull(commandMessage, command);
        this.versionedPivot.purgeStates();

        ObservableList<Case> cases = this.pivot.getCaseList();
        List<Case> newCases = new ArrayList<>();
        for (int i = 0; i < cases.size(); i++) {
            newCases.add(new Case(cases.get(i)));
        }
        Pivot newPivot = new Pivot();
        newPivot.setCases(newCases);

        this.versionedPivot.commit(newPivot, commandMessage, command);
    }

    @Override
    public boolean canRedoPivot() {
        return this.versionedPivot.canRedo();
    }

    @Override
    public void redoPivot() {
        ReadOnlyPivot pivot = this.versionedPivot.redo();
        this.setPivot(pivot);
    }

    @Override
    public boolean canUndoPivot() {
        return this.versionedPivot.canUndo();
    }

    @Override
    public void undoPivot() {
        ReadOnlyPivot pivot = this.versionedPivot.undo();
        this.setPivot(pivot);
    }

    @Override
    public String getCommandMessage() {
        return this.versionedPivot.getCommandMessageResult();
    }

    @Override
    public boolean isMainPageCommand() {
        return this.versionedPivot.isMainPageCommand();
    }

    //=========== Filtered Case List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Case} backed by the internal list of
     * {@code versionedPivot}
     */
    @Override
    public ObservableList<Case> getFilteredCaseList() {
        return filteredCases;
    }

    @Override
    public void updateFilteredCaseList(Predicate<Case> predicate) {
        requireNonNull(predicate);
        filteredCases.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return pivot.equals(other.pivot)
                && versionedPivot.equals(other.versionedPivot)
                && userPrefs.equals(other.userPrefs)
                && filteredCases.equals(other.filteredCases);
    }

}
