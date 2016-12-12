package view;

import integration.sql;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Arena;

/**
 * Created by Aquador on 2016-12-11.
 */
public class ByggArenaController {
    @FXML
    private TextField namnField;
    @FXML
    private TextField stadField;
    @FXML
    private TextField kapacitetField;


    private Stage dialogStage;
    private Arena arena = new Arena();
    private boolean okClicked = false;
    sql database;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk(){
        if(isInputValid()){
            arena.setNamn(namnField.getText());
            arena.setStad(stadField.getText());
            arena.setKapacitet(Integer.parseInt(kapacitetField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    public String[] getArena(){
        String[] list = {namnField.getText(),stadField.getText(), kapacitetField.getText()} ;
        return list;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (namnField.getText() == null || namnField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (stadField.getText() == null || stadField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }

        if (kapacitetField.getText() == null || kapacitetField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(kapacitetField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public void setArena(Arena arena) {
        this.arena = arena;

        namnField.setText(arena.getNamn());
        stadField.setText(arena.getStad());
        kapacitetField.setText(Integer.toString(arena.getKapacitet()));
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
