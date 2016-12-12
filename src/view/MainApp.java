package view;

import integration.sql;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Arena;
import model.Turnering;

import java.io.IOException;
import java.sql.ResultSet;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Arena> arenaData = FXCollections.observableArrayList();
    private ObservableList<Turnering> turneringData = FXCollections.observableArrayList();
    sql database = new sql();
    public MainApp() throws Exception{
        database.connect();

        selectArenas(database.selectAllArenas());
        selectTournaments(database.selectAllTournaments());

    }

    public void selectArenas(ResultSet rs) throws Exception{
        while(rs.next()){
            arenaData.add(new Arena(rs.getString("Namn"), rs.getString("Stad"),rs.getInt("Kapacitet")));
        }
    }

    public void selectTournaments(ResultSet rs) throws Exception{
        while(rs.next()){
            turneringData.add(new Turnering(rs.getString("Namn"), rs.getString("Fran"), rs.getString("Till")));
        }
    }
    public ObservableList<Arena> getArenaData() {
        return arenaData;
    }
    public ObservableList<Turnering> getTurneringData(){
        return turneringData;
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AllSportTV");
        this.primaryStage.setResizable(false);

        rootLayout();
        showArena();
        showTurnering();
    }

    private void rootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showArena(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ArenaOverview.fxml"));
            BorderPane arenaOver = (BorderPane) loader.load();

            rootLayout.setTop(arenaOver);

            ArenaController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showTurnering(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("TurneringOverview.fxml"));
            BorderPane turneringBottom = (BorderPane) loader.load();

            rootLayout.setBottom(turneringBottom);

            TurneringController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showArenaBuild(Arena arena) throws Exception {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ByggArena.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bygg arena");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ByggArenaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setArena(arena);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setSqlArena(Arena arena)throws Exception{
        database.createArena(arena.getNamn(), arena.getStad(), arena.getKapacitet());

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}