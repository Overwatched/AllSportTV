package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Turnering;

/**
 * Created by ludwi on 2016-12-11.
 */
public class TurneringController {
    @FXML
    private TableView<Turnering> turTable;
    @FXML
    private TableColumn<Turnering, String> turColumn;

    @FXML
    private Label namn;
    @FXML
    private Label fran;
    @FXML
    private Label till;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        turColumn.setCellValueFactory(cellData -> cellData.getValue().namnProperty());
        showTurneringDetails(null);

        // Listen for selection changes and show the person details when changed.
        turTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTurneringDetails(newValue));
    }

    private void showTurneringDetails(Turnering tur) {
        if (tur != null) {
            namn.setText(tur.getNamn());
            fran.setText(tur.getFran());
            till.setText(tur.getTill());
        } else {
            namn.setText("");
            fran.setText("");
            till.setText("");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        turTable.setItems(mainApp.getTurneringData());
    }
}
