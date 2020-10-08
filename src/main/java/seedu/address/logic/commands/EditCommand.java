package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CASES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.investigationcase.Case;
import seedu.address.model.investigationcase.Description;
import seedu.address.model.investigationcase.Document;
import seedu.address.model.investigationcase.Status;
import seedu.address.model.investigationcase.Suspect;
import seedu.address.model.investigationcase.Title;
import seedu.address.model.investigationcase.Victim;
import seedu.address.model.investigationcase.Witness;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing case in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the case identified "
            + "by the index number used in the displayed case list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_CASE_SUCCESS = "Edited Case: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CASE = "This case already exists in the address book.";

    private final Index index;
    private final EditCaseDescriptor editCaseDescriptor;

    /**
     * @param index of the case in the filtered case list to edit
     * @param editCaseDescriptor details to edit the case with
     */
    public EditCommand(Index index, EditCaseDescriptor editCaseDescriptor) {
        requireNonNull(index);
        requireNonNull(editCaseDescriptor);

        this.index = index;
        this.editCaseDescriptor = new EditCaseDescriptor(editCaseDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Case> lastShownList = model.getFilteredCaseList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CASE_DISPLAYED_INDEX);
        }

        Case caseToEdit = lastShownList.get(index.getZeroBased());
        Case editedCase = createEditedCase(caseToEdit, editCaseDescriptor);

        if (!caseToEdit.isSameCase(editedCase) && model.hasCase(editedCase)) {
            throw new CommandException(MESSAGE_DUPLICATE_CASE);
        }

        model.setCase(caseToEdit, editedCase);
        model.updateFilteredCaseList(PREDICATE_SHOW_ALL_CASES);
        return new CommandResult(String.format(MESSAGE_EDIT_CASE_SUCCESS, editedCase));
    }

    /**
     * Creates and returns a {@code Case} with the details of {@code caseToEdit}
     * edited with {@code editCaseDescriptor}.
     */
    private static Case createEditedCase(Case caseToEdit, EditCaseDescriptor editCaseDescriptor) {
        assert caseToEdit != null;

        Title updatedTitle = editCaseDescriptor.getTitle().orElse(caseToEdit.getTitle());
        Description updatedDescription = new Description("");
        Status updatedStatus = editCaseDescriptor.getStatus().orElse(caseToEdit.getStatus());
        List<Document> updatedDocuments = editCaseDescriptor.getDocuments().orElse(caseToEdit.getDocuments());
        List<Suspect> updatedSuspects = editCaseDescriptor.getSuspects().orElse(caseToEdit.getSuspects());
        List<Victim> updatedVictims = editCaseDescriptor.getVictims().orElse(caseToEdit.getVictims());
        Set<Tag> updatedTags = editCaseDescriptor.getTags().orElse(caseToEdit.getTags());
        List<Witness> updatedWitnesses =
                editCaseDescriptor.getWitnesses().orElse(caseToEdit.getWitnesses());
        return new Case(updatedTitle, updatedDescription, updatedStatus, updatedDocuments,
                updatedSuspects, updatedVictims, updatedWitnesses, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editCaseDescriptor.equals(e.editCaseDescriptor);
    }

    /**
     * Stores the details to edit the case with. Each non-empty field value will replace the
     * corresponding field value of the case.
     */
    public static class EditCaseDescriptor {
        private Title title;
        private Description description;
        private Status status;
        private List<Document> documents;
        private List<Suspect> suspects;
        private List<Victim> victims;
        private Set<Tag> tags;
        private ArrayList<Witness> witnesses;

        public EditCaseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCaseDescriptor(EditCaseDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setStatus(toCopy.status);
            setDocuments(toCopy.documents);
            setSuspects(toCopy.suspects);
            setVictims(toCopy.victims);
            setWitnesses(toCopy.witnesses);
            setTags(toCopy.tags);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, description,
                    status, suspects, victims, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setDocuments(List<Document> documents) {
            this.documents = documents;
        }

        public Optional<List<Document>> getDocuments() {
            return (documents != null) ? Optional.of(documents) : Optional.empty();
        }

        /**
         * Sets {@code suspects} to this object's {@code suspects}.
         * A defensive copy of {@code suspects} is used internally.
         */
        public void setSuspects(List<Suspect> suspects) {
            this.suspects = (suspects != null) ? new ArrayList<>() : null;
        }

        /**
         * Returns {@code Optional#empty()} if {@code suspects} is null.
         */
        public Optional<List<Suspect>> getSuspects() {
            return (suspects != null) ? Optional.of(suspects) : Optional.empty();
        }

        /**
         * Sets {@code victims} to this object's {@code victims}.
         * A defensive copy of {@code victims} is used internally.
         */
        public void setVictims(List<Victim> victims) {
            this.victims = (victims != null) ? new ArrayList<>() : null;
        }

        /**
         * Returns {@code Optional#empty()} if {@code victims} is null.
         */
        public Optional<List<Victim>> getVictims() {
            return (victims != null) ? Optional.of(victims) : Optional.empty();
        }


        /**
         * Sets {@code witnesses} to this object's {@code witnesses}.
         * A defensive copy of {@code witnesses} is used internally.
         */
        public void setWitnesses(ArrayList<Witness> witnesses) {
            this.witnesses = (witnesses != null) ? new ArrayList<>(witnesses) : null;
        }

        /**
         * Returns {@code Optional#empty()} if {@code witnesses} is null.
         */
        public Optional<List<Witness>> getWitnesses() {
            return (witnesses != null) ? Optional.of(witnesses) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCaseDescriptor)) {
                return false;
            }

            // state check
            EditCaseDescriptor e = (EditCaseDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getStatus().equals(e.getStatus())
                    && getDocuments().equals(e.getDocuments())
                    && getTags().equals(e.getTags());
        }

    }
}
