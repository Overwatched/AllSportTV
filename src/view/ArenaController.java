package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Arena;

/**
 * Created by ludwi on 2016-12-11.
 */
public class ArenaController {
    @FXML
    private TableView<Arena> arenaTable;
    @FXML
    private TableColumn<Arena, String> arenaColumn;

    @FXML
    private Label namn;
    @FXML
    private Label stad;
    @FXML
    private Label kapacitet;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        arenaColumn.setCellValueFactory(cellData -> cellData.getValue().namnProperty());
        showArenaDetails(null);

        // Listen for selection changes and show the person details when changed.
        arenaTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showArenaDetails(newValue));
    }

    private void showArenaDetails(Arena arena) {
        if (arena != null) {
            namn.setText(arena.getNamn());
            stad.setText(arena.getStad());
            kapacitet.setText(Integer.toString(arena.getKapacitet()));
        } else {
            namn.setText("");
            stad.setText("");
            kapacitet.setText("");
        }
    }

    @FXML
    private void handleNewArena() throws Exception{
        Arena tempPerson = new Arena();
        boolean okClicked = mainApp.showArenaBuild(tempPerson);
        if (okClicked) {
            mainApp.setSqlArena(tempPerson);
            mainApp.getArenaData().add(tempPerson);
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        arenaTable.setItems(mainApp.getArenaData());
    }


}
