package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.investigationcase.Case;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Case> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Case> caseList) {
        super(FXML);
        personListView.setItems(caseList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Case> {
        @Override
        protected void updateItem(Case investigationCase, boolean empty) {
            super.updateItem(investigationCase, empty);

            if (empty || investigationCase == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(investigationCase, getIndex() + 1).getRoot());
            }
        }
    }

}
