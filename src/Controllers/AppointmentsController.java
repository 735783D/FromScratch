package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    @FXML
    private RadioButton AllTimesDisplay;

    @FXML
    private TableView<?> Appointments;

    @FXML
    private Label AppointmentsTitle;

    @FXML
    private TextField ApptSearch;

    @FXML
    private Button Cancel;

    @FXML
    private TableColumn<?, ?> ColumnAppointmentID;

    @FXML
    private TableColumn<?, ?> ColumnContact;

    @FXML
    private TableColumn<?, ?> ColumnCustomerID;

    @FXML
    private TableColumn<?, ?> ColumnDescription;

    @FXML
    private TableColumn<?, ?> ColumnEnd;

    @FXML
    private TableColumn<?, ?> ColumnLocation;

    @FXML
    private TableColumn<?, ?> ColumnStart;

    @FXML
    private TableColumn<?, ?> ColumnTitle;

    @FXML
    private TableColumn<?, ?> ColumnType;

    @FXML
    private TableColumn<?, ?> ColumnUserID;

    @FXML
    private Button CreateAppointment;

    @FXML
    private Button DeleteAppointment;

    @FXML
    private Label Displays;

    @FXML
    private RadioButton MonthDisplay;

    @FXML
    private Button Search;

    @FXML
    private Button UpdateAppointment;

    @FXML
    private RadioButton WeekDisplay;

    public void BackToMain(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Menu!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    public void CreateAppointmentMenu(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/CreateAppointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Menu!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    public void UpdateAppointmentMenu(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/Views/UpdateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Menu!!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
